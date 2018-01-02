package com.zoomtk.circle.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zoomtk.circle.LoginActivity;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.utils.StatusBarCompat;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import skin.support.app.SkinCompatActivity;

/**
 * Created by wbq501 on 2017/10/27.
 */

public abstract class BaseActivity extends SkinCompatActivity{

    public static String TAG;

    Unbinder bind;
    public Gson gson = new Gson();
    public SharedPreferences sp;
    public String token;
    public ZLoadingDialog dialog;

    @Subscribe
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        StatusBarCompat.compat(this,getResources().getColor(R.color.rb_red));
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
        token = sp.getString("token", "");
        AppApplication.getInstance().addActivity(this);
//        if (Build.VERSION.SDK_INT >= 24){ //强制7.0
//            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//            StrictMode.setVmPolicy(builder.build());
//        }
        initview();
        init();
        initdata();
    }

    private void initview() {
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY);  // 设置字体颜色
    }

    public abstract int getLayoutId();
    public abstract void init();
    public abstract void initdata();

    public void startactivity(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public void startactivityForResult(Intent intent,int requestCode){
        startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        if(gson !=null){
            gson = null;
        }
        if (dialog != null) {
            dialog = null;
        }
        if (token != null){
            token = null;
        }
//        AppApplication.getInstance().exit();
    }
}
