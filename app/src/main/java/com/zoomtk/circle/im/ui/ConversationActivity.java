package com.zoomtk.circle.im.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.ChatUserInfoAct;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wbq501 on 2017/12/13.
 */

public class ConversationActivity extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_destil)
    ImageView iv_destil;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    public int getLayoutId() {
        return R.layout.conversation;
    }

    @Override
    public void init() {
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        String title = getIntent().getData().getQueryParameter("title");
        tv_name.setText(title+"");
        if (mConversationType.equals(Conversation.ConversationType.GROUP)){
            iv_destil.setBackgroundResource(R.mipmap.de_address_group);
        }else if (mConversationType.equals(Conversation.ConversationType.PRIVATE) | mConversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE) | mConversationType.equals(Conversation.ConversationType.DISCUSSION)){
            iv_destil.setBackgroundResource(R.mipmap.de_default_portrait);
        }else {
            iv_destil.setVisibility(View.GONE);
        }
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.tv_name,R.id.back,R.id.iv_destil})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_destil:
                if (mConversationType == Conversation.ConversationType.GROUP
                        || mConversationType == Conversation.ConversationType.CHATROOM
                        || mConversationType == Conversation.ConversationType.DISCUSSION) {
                    Intent intent = new Intent(ConversationActivity.this, ChatGroupInfoAct.class);
                    intent.setData(getIntent().getData());
                    startActivity(intent);
                } else if (mConversationType == Conversation.ConversationType.PRIVATE) {
                    Intent intent = new Intent(ConversationActivity.this, ChatUserInfoAct.class);
                    intent.putExtra("show_type",2);
                    intent.setData(getIntent().getData());
                    startActivity(intent);
                }
                break;
        }
    }

}
