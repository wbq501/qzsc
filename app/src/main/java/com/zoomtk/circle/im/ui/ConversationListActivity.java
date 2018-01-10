package com.zoomtk.circle.im.ui;

import android.net.Uri;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wbq501 on 2017/12/13.
 */

public class ConversationListActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.conversationlist;
    }

    @Override
    public void init() {
//        Map<String,Boolean> map = new HashMap<>();
//        map.put(Conversation.ConversationType.PRIVATE.getName(),false);
//        map.put(Conversation.ConversationType.GROUP.getName(),false);
//        RongIM.getInstance().startConversationList(getApplicationContext(),map);
//        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                String targetId = conversations.get(0).getTargetId();
//                Conversation.ConversationType type = conversations.get(0).getConversationType();
//                BaseToast.YToastL(ConversationListActivity.this,targetId);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                BaseToast.YToastL(ConversationListActivity.this,errorCode.getMessage());
//            }
//        });

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return getUserById(userId);
            }
        },true);
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String userId) {
//                return getUserGroup(userId);
//            }
//        },true);
    }

    @Override
    public void initdata() {

    }

    private UserInfo getUserGroup(String userId) {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("id",userId);
        HttpUtils.getFriend(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    com.zoomtk.circle.bean.UserInfo userInfo1 = gson.fromJson(gson.toJson(msg.getResult()), com.zoomtk.circle.bean.UserInfo.class);
                    RongIM.getInstance().refreshGroupInfoCache(new Group(userInfo1.getId(),userInfo1.getReally_name(), Uri.parse(userInfo1.getAvatar())));
                }else {
                    BaseToast.ToastS(ConversationListActivity.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(ConversationListActivity.this,errormsg);
            }
        });
        return null;
    }

    private UserInfo getUserById(String userId){
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("id",userId);
        HttpUtils.getFriend(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    com.zoomtk.circle.bean.UserInfo userInfo1 = gson.fromJson(gson.toJson(msg.getResult()), com.zoomtk.circle.bean.UserInfo.class);
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(userInfo1.getId(),userInfo1.getReally_name(),Uri.parse(userInfo1.getAvatar())));
                }else {
                    BaseToast.ToastS(ConversationListActivity.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(ConversationListActivity.this,errormsg);
            }
        });
        return null;
    }
}
