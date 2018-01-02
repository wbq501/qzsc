package com.zoomtk.circle.activity;

import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.utils.APKVersionCodeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/7.
 */

public class AboutUsAct extends BaseActivity{

    @BindView(R.id.vision)
    TextView vision;

    @Override
    public int getLayoutId() {
        return R.layout.about_us;
    }

    @Override
    public void init() {
        vision.setText(APKVersionCodeUtils.getVerName(AboutUsAct.this));
    }

    @OnClick({R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void initdata() {

    }
}
