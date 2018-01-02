package com.zoomtk.circle.im.im;

import android.content.Context;
import android.view.View;

import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wbq501 on 2017/12/14.
 */

public class IM_Message implements RongIM.ConversationListBehaviorListener{

    private Context mContext;

    public IM_Message(Context context){
        this.mContext = context;

    }

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }
}
