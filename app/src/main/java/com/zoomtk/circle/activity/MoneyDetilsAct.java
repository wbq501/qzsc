package com.zoomtk.circle.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.utils.MoneyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class MoneyDetilsAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_histry)
    TextView tv_histry;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.btn_money1)
    Button btn_money1;
    @BindView(R.id.btn_money2)
    Button btn_money2;

    @Override
    public int getLayoutId() {
        return R.layout.money_detils;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getmoney();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MoneyUtils.clearActivity();
    }

    @OnClick({R.id.back,R.id.tv_histry,R.id.btn_money1,R.id.btn_money2})
    public void OnClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.back:
                MoneyUtils.clearActivity();
                finish();
                break;
            case R.id.tv_histry:
//                intent = new Intent(MoneyDetilsAct.this,MoneyDetilsAct2.class);
//                startActivity(intent);
                break;
            case R.id.btn_money1:
//                intent = new Intent(MoneyDetilsAct.this,TransferAccounts.class);
//                startActivity(intent);
                break;
            case R.id.btn_money2:
                break;
        }
    }
}
