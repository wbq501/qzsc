package com.zoomtk.circle.dialog;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseToast;

/**
 * Created by wbq501 on 2017/11/20.
 */

public class ShareDialog extends PopupWindow {

    private View view;

    private ImageButton ll_weixin,ll_weixinp,ll_qq
            ,ll_qzone,ll_weibo,ll_copy,ll_refresh,man_img,ll_type,car_img;

    private Button btn_cancel;

    private String title,mContext,url,imgurl;

    private Context context;

    public ShareDialog(final Context context, final String title, final String mContext, final String url, final String imgurl, String popTitle, View.OnClickListener itemsOnClick){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.alert_dialog,null);

        this.title = title;
        this.mContext = mContext;
        this.url = url;
        this.imgurl = imgurl;
        this.context = context;

        ll_weixin = (ImageButton) view.findViewById(R.id.ll_weixin);
        ll_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                share(SHARE_MEDIA.WEIXIN,context);
            }
        });
        ll_weixinp = (ImageButton) view.findViewById(R.id.ll_weixinp);
        ll_weixinp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                share(SHARE_MEDIA.WEIXIN_CIRCLE,context);
            }
        });
        ll_qq = (ImageButton) view.findViewById(R.id.ll_qq);
        ll_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                share(SHARE_MEDIA.QQ,context);
            }
        });
        ll_qzone = (ImageButton) view.findViewById(R.id.ll_qzone);
        ll_qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                share(SHARE_MEDIA.QZONE,context);
            }
        });
        ll_weibo = (ImageButton) view.findViewById(R.id.ll_weibo);
        ll_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                share(SHARE_MEDIA.SINA,context);
            }
        });
        ll_copy = (ImageButton) view.findViewById(R.id.ll_copy);
        ll_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(url);
                BaseToast.YToastS(context,"复制成功");
            }
        });

        ll_refresh = (ImageButton) view.findViewById(R.id.ll_refresh);
        ll_refresh.setOnClickListener(itemsOnClick);
        man_img = (ImageButton) view.findViewById(R.id.man_img);
        man_img.setOnClickListener(itemsOnClick);
        ll_type = (ImageButton) view.findViewById(R.id.ll_type);
        ll_type.setOnClickListener(itemsOnClick);
        car_img = (ImageButton) view.findViewById(R.id.car_img);
        car_img.setOnClickListener(itemsOnClick);

        TextView poptitle = view.findViewById(R.id.pop_title);
        poptitle.setText(popTitle);

        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void share(SHARE_MEDIA platform, Context context) {
        UMImage umImage = new UMImage(context,imgurl);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(umImage);
        new ShareAction((Activity) context)
                .setPlatform(platform)//传入平台
                .withText(mContext)//分享内容
                .withMedia(web)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(context,"请稍等...",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context,"分享成功",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,"取消分享",Toast.LENGTH_LONG).show();
        }
    };
}
