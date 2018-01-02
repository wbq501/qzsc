package com.zoomtk.circle.im.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.LetAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.Content;
import com.zoomtk.circle.bean.CreateCircleSuccessEvent;
import com.zoomtk.circle.bean.TongXunLuModel;
import com.zoomtk.circle.im.utils.PinyinComparator;
import com.zoomtk.circle.im.utils.PinyinUtils;
import com.zoomtk.circle.utils.ConvertUtils;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.NmView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

/**
 * 创建群聊
 * Created by wbq501 on 2017/12/18.
 */

public class CreateCircle extends BaseActivity{

    @BindView(R.id.et_start_group_head)
    EditText ed_search;
    @BindView(R.id.list_start_group)
    ListView lv;
    @BindView(R.id.nmView)
    NmView nmView;
    @BindView(R.id.tv_confirm)
    TextView mConfirm;

    private LetAdapter letAdapter;
    //所有通讯录数据
    private List<Content> mAllDatas=new ArrayList<>();
    //搜索的数据
    private List<Content> mSearchDatas;
    private String mToken;

    private TextView mDialogText;
    private WindowManager mWindowManager;
    View mHead;

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_group;
    }

    @Override
    public void init() {
        ed_search.setFocusable(true);
        ed_search.setFocusableInTouchMode(true);
        ed_search.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        mDialogText = (TextView) LayoutInflater.from(this).inflate(R.layout.list_position, null);//中间出现的只是字母
        mHead = LayoutInflater.from(this).inflate(R.layout.header_start_group_chat, null);

        lv.addHeaderView(mHead);
        mDialogText.setVisibility(View.INVISIBLE);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mWindowManager.addView(mDialogText, lp);

        nmView.setTextView(mDialogText);
        letAdapter = new LetAdapter(CreateCircle.this, new ArrayList<Content>(), 1);
        lv.setAdapter(letAdapter);
        lv.removeHeaderView(mHead);
        lv.setDividerHeight(0);
        nmView.setListView(lv);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (lv.getHeaderViewsCount() == 0) {
                    }
                    letAdapter.setData(mAllDatas);
                } else {
                    if (lv.getHeaderViewsCount() > 0) {
                    }
                    List<Content> datas = getSearchDatas(s.toString());
                    letAdapter.setData(datas);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Content c = (Content) parent.getItemAtPosition(position);
                letAdapter.changeSelectStatus(c.getId());
                updateSelectCount();
            }
        });
    }

    @Override
    public void initdata() {
        dialog.show();
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getFriends(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                dialog.dismiss();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    TongXunLuModel tongXunLuModel = gson.fromJson(gson.toJson(msg.getResult()), TongXunLuModel.class);
                    for (TongXunLuModel.ListBean mBean: tongXunLuModel.getList()) {
                        Content mContent=new Content();
                        mContent.setId(mBean.getId());
                        mContent.setName(mBean.getReally_name());
                        mContent.setTouxiang(mBean.getAvatar());
                        mAllDatas.add(mContent);
                    }
                    String name = "";
                    ArrayList<Content> removeData = new ArrayList<Content>();
                    for (int i = 0; i < mAllDatas.size(); i++) {
                        name = mAllDatas.get(i).getName();
                        if (TextUtils.isEmpty(name)) {
                            removeData.add(mAllDatas.get(i));
                            continue;
                        }
                        String pinyinString = PinyinUtils.getPinYin(name);
                        char first = pinyinString.charAt(0);
                        if (first < 'A' || first > 'Z') {
                            first = '#';
                        }
                        mAllDatas.get(i).setLetter(first + "");
                    }
                    //删除名字是空的用户
                    if (removeData.size() > 0) {
                        for (Content i : removeData) {
                            mAllDatas.remove(i);
                        }
                    }
                    //根据a-z进行排序
                    Collections.sort(mAllDatas, new PinyinComparator());
                    letAdapter.setData(mAllDatas);
                }else {
                    BaseToast.YToastS(CreateCircle.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.YToastS(CreateCircle.this,errormsg);
            }
        });
    }

    @OnClick({R.id.back,R.id.tv_confirm})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:
                startGroup();
                break;
        }
    }

    private void startGroup() {
        final EditText et = new EditText(this);
        et.setHint("请输入圈子名称");
        et.setHintTextColor(getResources().getColor(R.color.tixingshuru));
        et.setTextColor(getResources().getColor(R.color.gray_shengqingtixian));
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("创建圈子").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = et.getText().toString().trim();
                Map<String,String> parms = new HashMap<>();
                parms.put("group_name",groupName);
                parms.put("user_id", ConvertUtils.list2String(letAdapter.getSelectId(),","));
                parms.put("token",token);
                HttpUtils.createGroupChat(parms, new RequestBack() {
                    @Override
                    public void success(BaseJson msg) throws Exception {
                        if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                            String result = gson.toJson(msg.getResult());
                            JSONObject object = new JSONObject(result);
                            int circle_id = object.getInt("circle_id");
                            EventBus.getDefault().post(new CreateCircleSuccessEvent(""+circle_id));
                            finish();
                        }else {
                            BaseToast.YToastS(CreateCircle.this,msg.getResultInfo());
                        }
                    }

                    @Override
                    public void error(String errormsg) {
                        BaseToast.YToastS(CreateCircle.this,errormsg);
                    }
                });

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 更新右上角选择的个数
     */
    private void updateSelectCount() {
        List<String> list = letAdapter.getSelectId();
        if (list == null || list.size() == 0) {
            mConfirm.setText("确定");
        } else {
            mConfirm.setText("确定(" + list.size() + ")");
        }
    }

    /**
     * 查询搜索的结果
     */
    private List<Content> getSearchDatas(String keyword) {
        List<Content> result = new ArrayList<>();
        for (Content c : mAllDatas) {
            if (c.getName().contains(keyword)) {
                result.add(c);
            }
        }
        return result;
    }
}
