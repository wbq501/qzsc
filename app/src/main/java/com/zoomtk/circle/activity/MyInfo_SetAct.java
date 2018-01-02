package com.zoomtk.circle.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.zoomtk.circle.Interface.ResponseBack;
import com.zoomtk.circle.LoginActivity;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.VersionModel;
import com.zoomtk.circle.dialog.DownDialog;
import com.zoomtk.circle.service.DownIntentService;
import com.zoomtk.circle.utils.APKVersionCodeUtils;
import com.zoomtk.circle.utils.CacheDataManager;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class MyInfo_SetAct extends BaseActivity {

    @BindView(R.id.tv_current_version)
    TextView version;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info__set;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back, R.id.change_pass, R.id.clean_rela, R.id.rl_update, R.id.about_us, R.id.loginout})
    public void OnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change_pass://修改密码
                intent = new Intent(MyInfo_SetAct.this,PasswordReset.class);
                startactivity(intent);
                break;
            case R.id.clean_rela://清除缓存
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            handler.sendEmptyMessage(3);
                            CacheDataManager.clearAllCache(MyInfo_SetAct.this);
                            Thread.sleep(2000);
                            if (CacheDataManager.getTotalCacheSize(MyInfo_SetAct.this).startsWith("0")){
                                handler.sendEmptyMessage(0);
                            }else {
                                handler.sendEmptyMessage(1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
                break;
            case R.id.rl_update://在线升级
                updateapkVersion();
                break;
            case R.id.about_us://关于我们
                intent = new Intent(MyInfo_SetAct.this,AboutUsAct.class);
                startactivity(intent);
                break;
            case R.id.loginout://退出登录
                intent = new Intent(MyInfo_SetAct.this, LoginActivity.class);
                startactivity(intent);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("token");
                editor.remove("spid");
                editor.commit();

                SharedPreferences.Editor kaidianeditor = getSharedPreferences("kaidiannum", Context.MODE_PRIVATE).edit();
                kaidianeditor.remove("order_total");
                kaidianeditor.remove("goods_total");
                kaidianeditor.remove("team_total");
                kaidianeditor.remove("member_total");
                kaidianeditor.remove("make_money_total");
                kaidianeditor.commit();
                AppApplication.getInstance().exit();
                break;
        }
    }

    private void updateapkVersion() {
        boolean isNet = CommonUtil.isNetworkAvailable(MyInfo_SetAct.this);
        final boolean isWifi = CommonUtil.isWifi(MyInfo_SetAct.this);

        if (!isNet){
            BaseToast.YToastS(MyInfo_SetAct.this,"亲,没有网络！");
            return;
        }

        Map<String,String> parms = new HashMap<>();
        parms.put("appid",getString(R.string.appid));
        parms.put("member_id",sp.getString("member_id",""));
        HttpUtils.getSoftVersion(parms, new ResponseBack() {
            @Override
            public void success(Object o) throws Exception {
                final VersionModel versionModel = gson.fromJson(o.toString(), VersionModel.class);
                String app_version = versionModel.getApp_version();
                int newversion = Integer.valueOf(app_version.replace(".",""));
                int oldversion = Integer.valueOf(APKVersionCodeUtils.getVerName(MyInfo_SetAct.this).replace(".",""));
                if (newversion > oldversion){
                    if (isWifi){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo_SetAct.this);
                        dialog.setTitle("温馨提示");
                        dialog.setMessage("当前是WiFi状态下,是否更新");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateapk(versionModel.getApp_path());
                                dialog.dismiss();
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MyInfo_SetAct.this);
                        dialog.setTitle("温馨提示");
                        dialog.setMessage("当前不是WiFi状态下,是否更新");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateapk(versionModel.getApp_path());
                                dialog.dismiss();
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }else {
                    BaseToast.YToastS(MyInfo_SetAct.this,"已是最高版本");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(MyInfo_SetAct.this,errormsg);
            }
        });
    }

    private void updateapk(String apkurl) {
        DownDialog downDialog = new DownDialog(this,true,apkurl);
        downDialog.show();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    BaseToast.YToastS(MyInfo_SetAct.this,"清理缓存完成");
                    break;
                case 1:
                    BaseToast.YToastS(MyInfo_SetAct.this,"清理缓存失败,再试一次");
                    break;
                case 3:
                    BaseToast.YToastS(MyInfo_SetAct.this,"清理缓存开始");
                    break;
            }
        }
    };
}
