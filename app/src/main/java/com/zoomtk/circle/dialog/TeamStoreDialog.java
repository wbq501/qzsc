package com.zoomtk.circle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.AdressBean;
import com.zoomtk.circle.bean.TeamStoreBean;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wbq501 on 2017/11/23.
 */

public class TeamStoreDialog extends Dialog{

    List<TeamStoreBean> lists = new ArrayList<>();
    private int num;
    private String token;
    ListView lv;
    ScrollView sv;
    LinearLayout ll;

    private int sv_size = 0;

    public TeamStoreDialog(@NonNull Context context,int num,String token) {
        super(context);
        this.num = num;
        this.token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int)(WindowManager.LayoutParams.MATCH_PARENT * 0.9);
        lp.height = (int)(WindowManager.LayoutParams.MATCH_PARENT * 0.9);
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
        setContentView(R.layout.team_store);
        init();
    }

    private void init() {
        ImageView close = (ImageView) findViewById(R.id.close);
        TextView save = (TextView) findViewById(R.id.save);
        lv = (ListView) findViewById(R.id.lv);
        sv = (ScrollView) findViewById(R.id.sv);
        ll = (LinearLayout) findViewById(R.id.ll);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sv_size <= 0){

                }else {
                    Bitmap bitmap = shotScrollView(sv);
                    saveBitmap(bitmap);
                }
                dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getData();
    }

    private void getData(){
        Map<String,String> parms = new HashMap<>();
        parms.put("reg_num",num+"");
        parms.put("token",getContext().getSharedPreferences("passwordFile", Context.MODE_PRIVATE).getString("token",""));
        HttpUtils.batchRegisterChild(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    Gson gson = new Gson();
                    String result = gson.toJson(msg.getResult());
                    ArrayList<TeamStoreBean> lists = gson.fromJson(result,new TypeToken<List<TeamStoreBean>>(){}.getType());
                    for (int i = 0; i < lists.size(); i++){
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.team_store_item, null);
                        ImageView iv_head = (ImageView) view.findViewById(R.id.iv_head);
                        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
                        TextView tv_psd = (TextView) view.findViewById(R.id.tv_psd);
                        TeamStoreBean teamStoreBean = lists.get(i);
                        tv_name.setText(teamStoreBean.getMember_truename() == null ? "":teamStoreBean.getMember_truename());
                        tv_num.setText(teamStoreBean.getMember_name());
                        tv_psd.setText("密码："+teamStoreBean.getMember_passwd());
                        ImgLoading.LoadCircle(getContext(),teamStoreBean.getMember_avatar(),iv_head);
                        ll.addView(view);
                    }
                }else {
                    BaseToast.YToastS(getContext(),msg.getResultInfo());
                    dismiss();
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(getContext(),errormsg);
                dismiss();
            }
        });
    }

    private void saveBitmap(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "down");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            BaseToast.YToastS(getContext(),"保存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getPath())));
    }

    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap shotListView(ListView listview) {

        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();

        for (int i = 0; i < itemscount; i++) {

            View childView = adapter.getView(i, null, listview);
            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }

        Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);
        Paint paint = new Paint();
        int iHeight = 0;

        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
            bmp = null;
        }
        return bigbitmap;
    }

    /**
     *  截图listview
     * **/
    public static Bitmap getListViewBitmap(ListView listView,String picpath) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < listView.getChildCount(); i++) {
            h += listView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        listView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return bitmap;
    }
}
