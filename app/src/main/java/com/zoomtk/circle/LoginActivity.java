package com.zoomtk.circle;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.activity.Code_LoginAct;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.LoginBean;
import com.zoomtk.circle.utils.AjaxParamUtil;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by wbq501 on 2017/10/27.
 */

public class LoginActivity extends BaseActivity{

    @BindView(R.id.cardNumAuto)
    AutoCompleteTextView cardNumAuto;
    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.logBT)
    Button logBT;
    @BindView(R.id.message_code)
    TextView message_code;
    @BindView(R.id.register_btn)
    TextView register_btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        boolean isLogin = sp.getBoolean("isLogin", false);
        if (isLogin){
            cardNumAuto.setText(sp.getString("username",""));
            passwordET.setText(sp.getString("password",""));
//            login();//自动执行登录
        }
        requestPermissions();
    }

    private void requestPermissions() {
        new RxPermissions(LoginActivity.this).request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean)
                            BaseToast.YToastS(LoginActivity.this,"权限已开启，你是谁？为什么会用我的应用,我已经发现你了");
                        else
                            BaseToast.YToastS(LoginActivity.this,"部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                    }
                });
    }

    @Override
    public void initdata() {

    }

    private void login(){

        final String username = cardNumAuto.getText().toString().trim();
        final String passwd = passwordET.getText().toString().trim();

        if (!CommonUtil.checkPhone(LoginActivity.this, username)){
            return;
        }

        if (TextUtils.isEmpty(passwd)){
            BaseToast.ToastL(LoginActivity.this,"请输入密码");
            return;
        }

        dialog.show();

        Map<String, String> param = new HashMap<>();
        param.put("member_name", username);
        param.put("member_passwd", AjaxParamUtil.encryption(0 + "", passwd));
        param.put("token_expire", "900000");
        param.put("msg_code", "");
        param.put("login_type", 0+"");//注册短信验证码or登录验证码
        param.put("From", "android");//登录方式
        HttpUtils.Login(param, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                dialog.dismiss();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String s = gson.toJson(msg.getResult());
                    LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", username);
                    editor.putString("membername", loginBean.getMember_name());
                    editor.putString("truename", loginBean.getMember_truename());
                    editor.putString("password", passwd);
                    editor.putBoolean("isLogin", true);
                    editor.putString("member_phone", loginBean.getMember_mobile());
                    editor.putString("token", loginBean.getToken());
                    editor.putString("member_id", loginBean.getMember_id());
                    try {
                        editor.putInt("spid",loginBean.getSpid());
                        editor.putInt("open_wshop",loginBean.getOpen_wshop());
                        editor.putString("wshop_errmsg",loginBean.getOpen_wshop_errmsg());
                    }catch (Exception e){
                    }
                    editor.commit();
//                    Intent intent = new Intent(LoginActivity.this, IndexAct.class);
//                    intent.putExtra("token", loginBean.getToken());
//                    startactivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    initIM(loginBean);
                }else {
                    BaseToast.ToastL(LoginActivity.this,msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.ToastL(LoginActivity.this, Config.errormsg);
            }
        });
    }

    private void initIM(final LoginBean loginBean) {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",loginBean.getToken());
        HttpUtils.getChatToken(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    Config.IMTOKEN = (String) msg.getResult();
                    RongIM.connect(Config.IMTOKEN, new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {

                        }

                        @Override
                        public void onSuccess(String s) {
                            Intent intent = new Intent(LoginActivity.this, IndexAct.class);
                            intent.putExtra("token", loginBean.getToken());
                            startactivity(intent);
                            finish();
                            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            BaseToast.YToastL(LoginActivity.this,errorCode.getValue()+"");
                        }
                    });
                }else {
                    BaseToast.YToastL(LoginActivity.this,"获取融云token失败");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastL(LoginActivity.this,"获取融云token失败");
            }
        });
    }

    @OnClick({R.id.message_code,R.id.register_btn,R.id.logBT})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.message_code:
                startactivity(new Intent(this, Code_LoginAct.class));
                break;
            case R.id.logBT:
                login();
                break;
            case R.id.register_btn:
                startActivityForResult(new Intent(LoginActivity.this, RegisterAct.class), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2 && !data.getStringExtra("name").equals("")) {
            cardNumAuto.setText(data.getStringExtra("name"));
        }
    }
}
