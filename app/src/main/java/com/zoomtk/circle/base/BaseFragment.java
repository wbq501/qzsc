package com.zoomtk.circle.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zoomtk.circle.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zoomtk.circle.base.BaseConfig.*;

/**
 * Created by wbq501 on 2017/10/30.
 */

public abstract class BaseFragment extends Fragment{

    Unbinder bind;

    public Gson gson = new Gson();
    public SharedPreferences sp;
    public String token;
    public ZLoadingDialog dialog;

    public View view;

    @Subscribe
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(getLayoutId(),container,false);
        }
        bind = ButterKnife.bind(this,view);
        sp = getActivity().getSharedPreferences("passwordFile", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        initview();
        init();
        initdata();
        return view;
    }

    private void initview() {
        dialog = new ZLoadingDialog(getActivity());
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY);  // 设置字体颜色
    }

    protected abstract int getLayoutId();
    protected abstract void init();
    protected abstract void initdata();

    public void startactivity(Intent intent){
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public void startactivityForResult(Intent intent,int requestCode){
        startActivityForResult(intent,requestCode);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null)
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
    }
}
