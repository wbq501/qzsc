package com.zoomtk.circle.im.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.im.Interface.AsyncTaskManager;
import com.zoomtk.circle.im.Interface.OnDataListener;

import retrofit2.HttpException;

/**
 * Created by wbq501 on 2017/12/13.
 */

public class IMBaseActivity extends FragmentActivity implements OnDataListener{

    protected Context mContext;
    public AsyncTaskManager mAsyncTaskManager;
    private ViewFlipper mContentView;
    protected LinearLayout mHeadLayout;
    protected ImageView mBtnLeft;
    protected Button mBtnRight;
    protected TextView mTitle;
    protected TextView mHeadRightText;
    private Drawable mBtnBackDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base);
        init();
    }

    public void init() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);// 使得音量键控制媒体声音
        mContext = this;

        // 初始化公共头部
        mContentView = (ViewFlipper) findViewById(R.id.layout_container);
        mHeadLayout = (LinearLayout) findViewById(R.id.layout_head);
        mHeadRightText = (TextView) findViewById(R.id.text_right);
        mBtnLeft = (ImageView) findViewById(R.id.btn_left);
        mBtnRight = (Button) findViewById(R.id.btn_right);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mBtnBackDrawable = getResources().getDrawable(R.mipmap.title_back);
        mBtnBackDrawable.setBounds(0, 0, mBtnBackDrawable.getMinimumWidth(),
                mBtnBackDrawable.getMinimumHeight());


        mAsyncTaskManager = AsyncTaskManager.getInstance(getApplicationContext());
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    /**
     * 设置头部是否可见
     *
     * @param visibility
     */
    public void setHeadVisibility(int visibility) {
        mHeadLayout.setVisibility(visibility);
    }

    /**
     * 设置左边是否可见
     *
     * @param visibility
     */
    public void setHeadLeftButtonVisibility(int visibility) {
        mBtnLeft.setVisibility(visibility);
    }

    /**
     * 设置右边是否可见
     *
     * @param visibility
     */
    public void setHeadRightButtonVisibility(int visibility) {
        mBtnRight.setVisibility(visibility);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId) {
        setTitle(getString(titleId), false);
    }

    /**
     * 设置标题
     */
    public void setTitle(int titleId, boolean flag) {
        setTitle(getString(titleId), flag);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        setTitle(title, false);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title, boolean flag) {
        mTitle.setText(title);
    }

    /**
     * 点击左按钮
     */
    public void onHeadLeftButtonClick(View v) {
        finish();
    }

    /**
     * 点击右按钮
     */
    public void onHeadRightButtonClick(View v) {

    }

    public ImageView getHeadLeftButton() {
        return mBtnLeft;
    }

    public void setHeadLeftButton(ImageView leftButton) {
        this.mBtnLeft = leftButton;
    }

    public Button getHeadRightButton() {
        return mBtnRight;
    }

    public void setHeadRightButton(Button rightButton) {
        this.mBtnRight = rightButton;
    }

    public Drawable getHeadBackButtonDrawable() {
        return mBtnBackDrawable;
    }

    public void setBackButtonDrawable(Drawable backButtonDrawable) {
        this.mBtnBackDrawable = backButtonDrawable;
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }


    /**
     * 发送请求（需要检查网络）
     *
     * @param requestCode 请求码
     */
    public void request(int requestCode) {
        if (mAsyncTaskManager != null) {
            mAsyncTaskManager.request(requestCode, this);
        }
    }

    /**
     * 发送请求（需要检查网络）
     *
     * @param id 请求数据的用户ID或者groupID
     * @param requestCode 请求码
     */
    public void request(String id , int requestCode) {
        if (mAsyncTaskManager != null) {
            mAsyncTaskManager.request(id, requestCode, this);
        }
    }

    /**
     * 发送请求
     *
     * @param requestCode    请求码
     * @param isCheckNetwork 是否需检查网络，true检查，false不检查
     */
    public void request(int requestCode, boolean isCheckNetwork) {
        if (mAsyncTaskManager != null) {
            mAsyncTaskManager.request(requestCode, isCheckNetwork, this);
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelRequest() {
        if (mAsyncTaskManager != null) {
            mAsyncTaskManager.cancelRequest();
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {

    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        switch (state) {
            // 网络不可用给出提示
            case AsyncTaskManager.HTTP_NULL_CODE:
                BaseToast.YToastL(mContext,"当前网络不可用");
                break;

            // 网络有问题给出提示
            case AsyncTaskManager.HTTP_ERROR_CODE:
                BaseToast.YToastL(mContext, "网络问题请稍后重试");
                break;

            // 请求有问题给出提示
            case AsyncTaskManager.REQUEST_ERROR_CODE:
                // NToast.shortToast(mContext, R.string.common_request_error);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
