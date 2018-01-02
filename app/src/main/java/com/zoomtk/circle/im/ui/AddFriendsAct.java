package com.zoomtk.circle.im.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.DatuAct;
import com.zoomtk.circle.activity.SearchFriendAct;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.MemberInfo;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.view.TitleView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加朋友
 * Created by wbq501 on 2017/12/18.
 */

public class AddFriendsAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_add_friend)
    EditText et_add_friend;
    @BindView(R.id.sousuo)
    TextView sousuo;
    @BindView(R.id.img_my_code)
    ImageView img_my_code;
    @BindView(R.id.addfriend_lv)
    ListView lv;
    @BindView(R.id.addfriend_quanzihao)
    TextView name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_addfriends;
    }

    @Override
    public void init() {
        et_add_friend.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        && !TextUtils.isEmpty(et_add_friend.getText().toString())) {
                    Intent intent = new Intent(AddFriendsAct.this, SearchFriendAct.class);
                    intent.putExtra("search_key", et_add_friend.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("fields","member_name,code_image");
        HttpUtils.getMemberInfo(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    MemberInfo memberInfo = gson.fromJson(gson.toJson(msg.getResult()), MemberInfo.class);
                    name.setText("我的圈子号："+memberInfo.getMember_name());
                    ImgLoading.LoadCircle(AddFriendsAct.this,memberInfo.getCode_image(),img_my_code);
                }else {
                    BaseToast.YToastS(AddFriendsAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(AddFriendsAct.this,errormsg);
            }
        });
    }

    @OnClick({R.id.back,R.id.sousuo,R.id.img_my_code})
    public void OnClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sousuo:
                intent = new Intent(AddFriendsAct.this, SearchFriendAct.class);
                intent.putExtra("search_key", et_add_friend.getText().toString());
                startactivity(intent);
                break;
            case R.id.img_my_code:
                intent = new Intent(AddFriendsAct.this, DatuAct.class);
                startactivity(intent);
                break;
        }
    }
}
