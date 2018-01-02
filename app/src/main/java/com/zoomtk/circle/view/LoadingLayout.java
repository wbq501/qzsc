package com.zoomtk.circle.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseConfig;

import java.util.jar.Attributes;

/**
 * Created by wbq501 on 2017/11/11.
 */

public class LoadingLayout extends LinearLayout{
    private int showType;
    TextView load_msg;

    public LoadingLayout(Context context) {
        super(context);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loading_error_no_msg,this);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        load_msg = (TextView) findViewById(R.id.load_msg);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShowType(int showType) {
        load_msg.setText("数据加载中...");
        switch (showType){
            case BaseConfig.LOADING:
                load_msg.setText("数据加载中...");
                break;
            case BaseConfig.LOADING_ERROR:
                load_msg.setText("数据错误\n点击重试");
                break;
            case BaseConfig.LOADING_NO:
                load_msg.setText("暂无数据");
                break;
        }
        postInvalidate();
    }
}
