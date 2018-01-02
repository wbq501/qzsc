package com.zoomtk.circle.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/12.
 */

public class StoreEditAct extends BaseActivity{

    @BindView(R.id.store_edit)
    EditText store_edit;
    @BindView(R.id.update_store_edit)
    TextView tv_save;
    @BindView(R.id.store_title)
    TextView title;

    int type;
    String context;
    private int op;

    private boolean isSave = true;
    private boolean isToast = true;
    private String eidtInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_edit;
    }

    @Override
    public void init() {
        type = getIntent().getIntExtra("editkey",0);
        context = getIntent().getStringExtra("editvalue");
        switch (type){
            case 11:
                title.setText("修改店铺名称");
                store_edit.setImeOptions(EditorInfo.IME_ACTION_DONE);
                store_edit.setSingleLine(true);
                InputFilter[] filters = {new InputFilter.LengthFilter(5)};
                store_edit.setFilters(filters);
                op = 1;
                break;
            case 12:
                title.setText("修改店铺简介");
                op = 2;
                break;
            case 20:
                title.setText("修改真实姓名");
                store_edit.setImeOptions(EditorInfo.IME_ACTION_DONE);
                store_edit.setSingleLine(true);
                InputFilter[] filters2 = {new InputFilter.LengthFilter(5)};
                store_edit.setFilters(filters2);
                op = 4;
                break;
        }
        store_edit.setText(context);
        store_edit.setSelection(context.length());
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        store_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                int length = store_edit.getText().toString().trim().length();
                if (length>0){
                    isSave = true;
                    tv_save.setBackground(getResources().getDrawable(R.drawable.store_edit_save_btn));
                    if (store_edit.getText().toString().trim().equals(context)){
                        isSave = false;
                        tv_save.setBackground(getResources().getDrawable(R.drawable.store_edit_save_btn2));
                    }
                }else {
                    isSave = false;
                    tv_save.setBackground(getResources().getDrawable(R.drawable.store_edit_save_btn2));
                }
            }
        });
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back,R.id.update_store_edit})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.update_store_edit:
                if (isSave){
                    updateStoreInfo(view);
                }
                break;
        }
    }

    private void updateStoreInfo(View view) {
        eidtInfo = store_edit.getText().toString();
        if (eidtInfo.equals(context)){
            if (isToast){
                Toast.makeText(getApplicationContext(), "新信息和之前的信息不能相同", Toast.LENGTH_SHORT).show();
                isToast = false;
            }
            return;
        }
        if (eidtInfo.equals("")) {
            Toast.makeText(getApplicationContext(), "修改信息不能为空，请重新修改，谢谢", Toast.LENGTH_SHORT).show();
            return;
        }
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("op",op+"");
        parms.put("content",eidtInfo);
        HttpUtils.storeManage(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    Intent intent=new Intent();
                    intent.putExtra("editvalue", eidtInfo);
                    setResult(type, intent);
                    finish();
                    BaseToast.YToastL(StoreEditAct.this,"保存成功");
                }else {
                    BaseToast.YToastL(StoreEditAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastL(StoreEditAct.this,errormsg);
            }
        });
    }
}
