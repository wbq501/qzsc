package com.zoomtk.circle.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.IndexAct;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.MainActivity;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.LoginBean;
import com.zoomtk.circle.dialog.MaterialDialog;
import com.zoomtk.circle.utils.AjaxParamUtil;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 短信登录
 * Created by wbq501 on 2017/10/27.
 */

public class Code_LoginAct extends BaseActivity{
    private static String CODE_TYPE_MESSAGECODE = "1";
    public static final String CHANGE_PASS = "CHANGE_PASS";

    @BindView(R.id.login_backarrow)
    ImageButton login_backarrow;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.yanzheng)
    EditText yanzheng;
    @BindView(R.id.getcode)
    Button getcode;
    @BindView(R.id.login_code)
    Button login_code;

    private CountDownTimer timer = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_code__login;
    }

    @Override
    public void init() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                getcode.setText(l / 1000 + "秒后获取");
            }

            @Override
            public void onFinish() {
                getcode.setText("重新获取");
                getcode.setClickable(true);
            }
        };
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.login_backarrow,R.id.getcode,R.id.login_code})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.login_backarrow:
                finish();
                break;
            case R.id.getcode:
                getcodeDialog();
                break;
            case R.id.login_code:
                login();
                break;
        }
    }

    private void login() {
        String phonenumber = phone_number.getText().toString().trim();
        if (!CommonUtil.checkPhone(Code_LoginAct.this, phonenumber)){
            return;
        }

        String num = yanzheng.getText().toString().trim();
        if (num == null || num.equals("")){
            BaseToast.ToastL(Code_LoginAct.this,"请输入验证码");
            return;
        }

        final ZLoadingDialog dialog = new ZLoadingDialog(Code_LoginAct.this);
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .show();

        Map<String, String> param = new HashMap<>();
        param.put("member_name", phonenumber);
        param.put("member_passwd", AjaxParamUtil.encryption(0 + "", ""));
        param.put("token_expire", "");
        param.put("msg_code", num);
        param.put("login_type", 1+"");//注册短信验证码or登录验证码
        param.put("From", "android");//登录方式
        HttpUtils.Login(param, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                dialog.dismiss();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String s = gson.toJson(msg.getResult());
                    LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("username", username);
                    editor.putString("membername", loginBean.getMember_name());
                    editor.putString("truename", loginBean.getMember_truename());
//                    editor.putString("password", passwd);
                    editor.putBoolean("isLogin", false);
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
                    Intent intent = new Intent(Code_LoginAct.this, IndexAct.class);
                    intent.putExtra("token", loginBean.getToken());
                    intent.putExtra(CHANGE_PASS, CHANGE_PASS);
                    /**是否是跳转到聊天或者其他界面*/
                    Intent dataIntent = getIntent();
                    if (dataIntent != null && !TextUtils.isEmpty(dataIntent.getStringExtra("jump_type"))) {
                        if (dataIntent.getExtras() != null) {
                            intent.putExtras(dataIntent.getExtras());
                        }
                    }
                    startactivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }else {
                    BaseToast.ToastL(Code_LoginAct.this,msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.ToastL(Code_LoginAct.this, Config.errormsg);
            }
        });
    }

    private void getcodeDialog() {
        final String phonenumber = phone_number.getText().toString().trim();
        if (!CommonUtil.checkPhone(Code_LoginAct.this, phonenumber)){
            return;
        }
        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.setTitle("确认手机号码");
        dialog.setMessage("我们将发送短信到这个号码：" + phonenumber);
        dialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessageCode(phonenumber);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 获取验证码
     */
    private void getMessageCode(String phonenumber) {
        Map<String, String> parm = new HashMap<>();
        parm.put("phone", phonenumber);
        parm.put("code_type", CODE_TYPE_MESSAGECODE);
        HttpUtils.getYanZhengURL(parm, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                timer.start();
                getcode.setClickable(false);
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(Code_LoginAct.this, Config.errormsg);
            }
        });
    }
}
