package com.zoomtk.circle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.activity.Code_LoginAct;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.LoginBean;
import com.zoomtk.circle.utils.AjaxParamUtil;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.OnclickUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/10/28.
 */

public class RegisterAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.yanzheng)
    EditText yanzheng;
    @BindView(R.id.get_yanzheng)
    Button get_yanzheng;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.new_phone)
    EditText new_phone;
    @BindView(R.id.register)
    Button register;

    private CountDownTimer timer = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                get_yanzheng.setText(l / 1000 + "秒后获取");
            }

            @Override
            public void onFinish() {
                get_yanzheng.setText("重新获取");
                get_yanzheng.setClickable(true);
            }
        };
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back,R.id.get_yanzheng})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.get_yanzheng:
                getcode();
                break;
            case R.id.register:
                if (OnclickUtils.isFastClick()){
                    checkMobile();
                }
                break;
        }
    }

    /**
     * 注册
     */
    private void checkMobile() {
        String ettruename = et_name.getText().toString().trim();
        if (ettruename.equals("") || ettruename.length() == 0) {
            BaseToast.ToastL(RegisterAct.this, "请输入真实姓名用于提现");
            return;
        }

        if (ettruename.length() > 6) {
            BaseToast.ToastL(RegisterAct.this,"请输入符号规范的真实姓名");
            return;
        }

        String phonenum = phone_number.getText().toString().trim();
        if (phonenum.length() != 11) {
            BaseToast.ToastL(RegisterAct.this, "手机号码长度不正确");
            return;
        }
        String yzm = yanzheng.getText().toString().trim();
        if (yzm.equals("") || yzm == null) {
            BaseToast.ToastL(RegisterAct.this, "请输入验证码");
            return;
        }
        String psd = password.getText().toString().trim();
        if (psd.length() < 6 || psd.length() > 16) {
            BaseToast.ToastL(RegisterAct.this, "请输入6-16的密码");
            return;
        }
        String newphone = new_phone.getText().toString().trim();
        if (newphone.equals("") || newphone == null){
            BaseToast.ToastL(RegisterAct.this,"请输入推荐人手机号或账号名称！！！");
            return;
        }

        Map<String, String> parm = new HashMap<>();
        parm.put("parent_phone", phonenum);
        HttpUtils.checkMobile(parm, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                  register();
                }else {
                    BaseToast.ToastL(RegisterAct.this, msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(RegisterAct.this, Config.errormsg);
            }
        });
    }

    private void register() {
        Map<String, String> parm = new HashMap<>();
        parm.put("phone", et_name.getText().toString().trim());
        parm.put("password", password.getText().toString().trim());
        parm.put("msg_code", yanzheng.getText().toString().trim());
        parm.put("w_store_name", et_name.getText().toString().trim());
        parm.put("really_name", et_name.getText().toString().trim());
        parm.put("p_sid", getString(R.string.self_sid));
        parm.put("parent_phone", new_phone.getText().toString().trim());
        parm.put("appid", getString(R.string.appid));

        HttpUtils.registerWshop(parm, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    login();
                }else {
                    BaseToast.ToastL(RegisterAct.this,msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(RegisterAct.this, Config.errormsg);
            }
        });
    }

    private void login() {
        final ZLoadingDialog dialog = new ZLoadingDialog(RegisterAct.this);
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .show();

        Map<String, String> param = new HashMap<>();
        param.put("member_name", phone_number.getText().toString().trim());
        param.put("member_passwd", AjaxParamUtil.encryption(0 + "", password.getText().toString().trim()));
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
                    editor.putString("username", phone_number.getText().toString().trim());
                    editor.putString("membername", loginBean.getMember_name());
                    editor.putString("truename", loginBean.getMember_truename());
                    editor.putString("password", password.getText().toString().trim());
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
                    Intent intent = new Intent(RegisterAct.this, IndexAct.class);
                    intent.putExtra("token", loginBean.getToken());
                    startactivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }else {
                    BaseToast.ToastL(RegisterAct.this,msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.ToastL(RegisterAct.this, Config.errormsg);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getcode() {
        final String phonenumber = phone_number.getText().toString().trim();
        if (!CommonUtil.checkPhone(RegisterAct.this, phonenumber)){
            return;
        }

        Map<String, String> parm = new HashMap<>();
        parm.put("phone", phonenumber);
        parm.put("code_type", 0+"");
        HttpUtils.getYanZhengURL(parm, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                timer.start();
                get_yanzheng.setClickable(false);
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(RegisterAct.this, Config.errormsg);
            }
        });
    }
}
