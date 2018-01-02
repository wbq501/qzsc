package com.zoomtk.circle.activity;

import android.widget.ImageView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.utils.ImgLoading;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class LookImgAct extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.look_img;
    }

    @Override
    public void init() {
        ImageView look_img = (ImageView) findViewById(R.id.look_img);
        String imgurl = getIntent().getStringExtra("imgurl");
        ImgLoading.LoadCircle(LookImgAct.this,imgurl,look_img);
    }

    @Override
    public void initdata() {

    }
}
