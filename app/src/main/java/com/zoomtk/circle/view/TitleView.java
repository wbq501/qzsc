package com.zoomtk.circle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zoomtk.circle.R;
import com.zoomtk.circle.utils.CommonUtil;

/**
 * 顶部title 封装着玩玩就是不用
 * Created by wbq501 on 2017/11/20.
 */

public class TitleView extends LinearLayout implements View.OnClickListener{
    private ImageView back;
    private ImageView iv_right;
    private TextView tv_title;
    private TextView tv_right;

    private TitleLeftOnClick titleLeftOnClick;
    private TitleOnClick titleOnClick;
    private TitleRightOnClick titleRightOnClick;

    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.title_layout, this);
        back = findViewById(R.id.back);
        iv_right = findViewById(R.id.iv_right);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
        init(context,attrs,view);

        back.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs,View view) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        int title_bg = typedArray.getColor(R.styleable.TitleView_title_bg,getResources().getColor(R.color.title_bg));

        boolean isleftimg = typedArray.getBoolean(R.styleable.TitleView_isleftimg, true);
        int leftimg = typedArray.getResourceId(R.styleable.TitleView_leftimg, R.mipmap.title_back);

        boolean isTitle = typedArray.getBoolean(R.styleable.TitleView_isTitle, true);
        String titletext = typedArray.getString(R.styleable.TitleView_titletext);
        int titleColor = typedArray.getColor(R.styleable.TitleView_titileColor, Color.WHITE);
        int titleSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleSize, CommonUtil.sp2px(12));

        boolean isrightimg = typedArray.getBoolean(R.styleable.TitleView_isrightimage, false);
        int rightimg = typedArray.getResourceId(R.styleable.TitleView_rightimg, R.mipmap.logo36);

        boolean isrightImgOrText = typedArray.getBoolean(R.styleable.TitleView_isrightImgOrText, true);

        boolean isrighttext = typedArray.getBoolean(R.styleable.TitleView_isrighttext, false);
        String righttext = typedArray.getString(R.styleable.TitleView_righttext);
        int rightColor = typedArray.getColor(R.styleable.TitleView_righttextColor, Color.WHITE);
        int righttextSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_righttextSize, CommonUtil.dip2px(10));
        typedArray.recycle();

        view.setBackgroundColor(title_bg);

        if (isleftimg){
            back.setVisibility(View.VISIBLE);
            back.setImageResource(leftimg);
        }else {
            back.setVisibility(View.GONE);
        }

        if (isTitle){
            if (TextUtils.isEmpty(titletext)){
                tv_title.setVisibility(View.GONE);
            }else {
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText(titletext);
                tv_title.setTextColor(titleColor);
                tv_title.setTextSize(titleSize);
            }
        }else {
            tv_title.setVisibility(View.GONE);
        }

        if (isrightimg && isrightImgOrText && isrighttext){//当右边图片和文字都出现时默认右边显示图片
            isrighttext = false;
            isrightimg = true;
        }

        if (isrightimg && !isrightImgOrText && isrighttext){
            isrighttext = true;
            isrightimg = false;
        }

        if (isrightimg){
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageResource(rightimg);
        }else {
            iv_right.setVisibility(View.GONE);
        }

        if (isrighttext){
            if (TextUtils.isEmpty(righttext)){
                tv_right.setVisibility(View.GONE);
            }else {
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText(righttext);
                tv_right.setTextColor(rightColor);
                tv_right.setTextSize(righttextSize);
            }
        }else {
            tv_right.setVisibility(View.GONE);
        }
    }

    public void setTitleOnClick(TitleLeftOnClick titleLeftOnClick) {
        this.titleLeftOnClick = titleLeftOnClick;
    }

    public void setTitleOnClick(TitleOnClick titleOnClick) {
        this.titleOnClick = titleOnClick;
    }

    public void setTitleRightOnClick(TitleRightOnClick titleRightOnClick) {
        this.titleRightOnClick = titleRightOnClick;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                titleLeftOnClick.leftOnClick();
                break;
            case R.id.tv_title:
                titleOnClick.titleOnClick();
                break;
            case R.id.tv_right:
            case R.id.iv_right:
                titleRightOnClick.titleRightClick();
                break;
        }
    }

    public interface TitleLeftOnClick{
        void leftOnClick();
    }

    public interface TitleOnClick{
        void titleOnClick();
    }

    public interface TitleRightOnClick{
        void titleRightClick();
    }
}
