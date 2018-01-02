package com.zoomtk.circle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wbq501 on 2017/11/2.
 */

public class HihtDialog extends Dialog {

    private String msg;
    private String token;
    TextView hiht_msg;

    public HihtDialog(@NonNull Context context, String msg,String token) {
        super(context);
        this.msg = msg;
        this.token = token;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hiht_dialog);
        init();
        initdata();
    }

    private void initdata() {
        Map<String, String> param = new HashMap<>();
        param.put("key",msg);
        param.put("token", token);
        HttpUtils.getPrompt(param, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    hiht_msg.setText(msg.getResult().toString());
                }else {
                    BaseToast.YToastS(getContext(),msg.getResultInfo());
                    dismiss();
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(getContext(),errormsg);
                dismiss();
            }
        });
    }

    private void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        hiht_msg = (TextView) findViewById(R.id.hiht_msg);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
