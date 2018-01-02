package com.zoomtk.circle.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 详细资料
 * Created by wbq501 on 2017/12/21.
 */

public class ChatUserInfoAct extends BaseActivity{

    @BindView(R.id.img_user_info_head)
    ImageView userhead;
    @BindView(R.id.tv_userName)
    TextView userName;
    @BindView(R.id.tv_uName)
    TextView name;
    @BindView(R.id.cb_chat_top)
    CheckBox cb_chat_top;
    @BindView(R.id.cb_receive_message)
    CheckBox cb_receive_message;
    @BindView(R.id.tv_send_message)
    TextView tv_send_message;
    @BindView(R.id.tv_add_friend)
    TextView tv_add_friend;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_user_info;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back,R.id.tv_clear_message})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_clear_message://清空消息记录
                break;
        }
    }
}
