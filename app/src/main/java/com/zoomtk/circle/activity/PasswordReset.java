package com.zoomtk.circle.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.LoginActivity;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/10/28.
 */

public class PasswordReset extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.reset_member_name)
    TextView reset_member_name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sure_pass)
    EditText sure_pass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_reset;
    }

    @Override
    public void init() {
        String membername = this.getSharedPreferences("passwordFile", MODE_PRIVATE).getString("member_phone", "");
        reset_member_name.setText(membername);
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.done:
                resetpass();
                break;
        }
    }

    private void resetpass() {
        String pass = password.getText().toString().trim();
        String surepass = sure_pass.getText().toString().trim();
        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(surepass)) {
            BaseToast.ToastL(PasswordReset.this,"密码不能为空");
            return;
        }
        if (!pass.equals(surepass)) {
            BaseToast.ToastL(PasswordReset.this,"两次输入的密码不一致");
            return;
        }

        dialog.show();
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("new_passwd", pass);
        HttpUtils.changeOldPasswd(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    BaseToast.ToastL(PasswordReset.this,"重置密码成功");
                    finish();
                }else {
                    BaseToast.ToastL(PasswordReset.this,msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(PasswordReset.this, Config.errormsg);
            }
        });
    }
}
