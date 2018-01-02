package com.zoomtk.circle.im.ui;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wbq501 on 2017/12/13.
 */

public class ConversationActivity extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_name)
    TextView tv_name;

    @Override
    public int getLayoutId() {
        return R.layout.conversation;
    }

    @Override
    public void init() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return getUserById(userId);
            }
        },true);
        String title = getIntent().getData().getQueryParameter("title");
        tv_name.setText(title+"");
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.tv_name,R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
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
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(userInfo1.getId(),userInfo1.getReally_name(), Uri.parse(userInfo1.getAvatar())));
                    BaseLog.LogE("bbbbbbb",userInfo1.getName());
                }else {
                    BaseToast.ToastS(ConversationActivity.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(ConversationActivity.this,errormsg);
            }
        });
        return null;
    }
}
