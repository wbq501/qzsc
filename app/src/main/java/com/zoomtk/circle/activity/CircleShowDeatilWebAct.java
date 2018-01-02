package com.zoomtk.circle.activity;

import android.view.View;
import android.webkit.WebView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 详情页，圈子秀
 * Created by admin on 2017/12/29.
 */

public class CircleShowDeatilWebAct extends BaseActivity{

    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_show_deatil_web;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
