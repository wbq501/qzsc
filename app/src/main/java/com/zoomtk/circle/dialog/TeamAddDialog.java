package com.zoomtk.circle.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseToast;

/**
 * Created by wbq501 on 2017/11/23.
 */

public class TeamAddDialog extends Dialog{

    private int ednum = 10;
    private View.OnClickListener onClickListener;

    private boolean isNull = false;

    public TeamAddDialog(@NonNull Context context, View.OnClickListener onClickListener) {
        super(context);
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.team_add);
        setCanceledOnTouchOutside(false);
        init();
    }

    private void init() {
        TextView add = (TextView) findViewById(R.id.add);
        TextView reduce = (TextView) findViewById(R.id.reduce);
        TextView sure = (TextView) findViewById(R.id.sure);
        sure.setOnClickListener(onClickListener);
        TextView cancle = (TextView) findViewById(R.id.cancle);
        cancle.setOnClickListener(onClickListener);
        final EditText num = (EditText) findViewById(R.id.num);
        num.setSelection(num.getText().toString().length());
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                num.setSelection(num.getText().toString().length());
                String numed = num.getText().toString().trim();
                if (numed.equals("")){
                    isNull = true;
                }else {
                    isNull = false;
                    ednum = Integer.valueOf(numed);
                    if (ednum > 10){
                        BaseToast.YToastS(getContext(),"最大生成店铺为十");
                        ednum = 10;
                        num.setText(ednum+"");
                    }
                    if (ednum <= 0){
                        BaseToast.YToastS(getContext(),"最小生成店铺为一");
                        ednum = 1;
                        num.setText(ednum+"");
                    }
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull)
                    return;
                if (ednum <= 1){
                    BaseToast.YToastS(getContext(),"最小生成店铺为一");
                    ednum = 1;
                }else {
                    ednum--;
                }
                num.setText(ednum+"");
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull)
                    return;
                if (ednum >= 10){
                    BaseToast.YToastS(getContext(),"最大生成店铺为十");
                    ednum = 10;
                }else {
                    ednum++;
                }
                num.setText(ednum+"");
            }
        });
    }

    public boolean isNull() {
        return isNull;
    }

    public int getEdnum() {
        return ednum;
    }
}
