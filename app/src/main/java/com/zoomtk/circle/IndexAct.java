package com.zoomtk.circle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.Interface.ResponseBack;
import com.zoomtk.circle.activity.Code_LoginAct;
import com.zoomtk.circle.activity.MyInfo_SetAct;
import com.zoomtk.circle.activity.PasswordReset;
import com.zoomtk.circle.adapter.MyFragmentPagerAdapter;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.app.CloseActivityClass;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.EventMsg;
import com.zoomtk.circle.bean.NearbyStoreBean;
import com.zoomtk.circle.bean.UserInfo;
import com.zoomtk.circle.bean.VersionModel;
import com.zoomtk.circle.dialog.DownDialog;
import com.zoomtk.circle.fragment.FourFragment;
import com.zoomtk.circle.fragment.OneFragment;
import com.zoomtk.circle.fragment.ThreeFragment;
import com.zoomtk.circle.fragment.TwoFragment;
import com.zoomtk.circle.service.GrayService;
import com.zoomtk.circle.utils.APKVersionCodeUtils;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.view.MyRadioButton;
import com.zoomtk.circle.view.NoViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by wbq501 on 2017/10/28.
 */

public class IndexAct extends BaseActivity{
    private static String TAG = "IndexAct";

    @BindView(R.id.pager)
    NoViewPager pager;
    @BindView(R.id.rb_microstore)
    MyRadioButton rb_microstore;
    @BindView(R.id.rb_discover)
    MyRadioButton rb_discover;
    @BindView(R.id.index_discover_circle)
    TextView index_discover_circle;
    @BindView(R.id.rb_circle)
    MyRadioButton rb_circle;
    @BindView(R.id.index_circle_circle)
    TextView index_circle_circle;
    @BindView(R.id.rb_myinfo)
    MyRadioButton rb_myinfo;

    List<Fragment> lists;
    private String member_id;

    public int currentitem = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    public void init() {
        if (getIntent().hasExtra(Code_LoginAct.CHANGE_PASS)) {
            Intent mIntent = new Intent(IndexAct.this, PasswordReset.class);
            startactivity(mIntent);
        }
        member_id = sp.getString("member_id", "");
        pager.setOffscreenPageLimit(4);

        CloseActivityClass.activityList.add(this);
        startService(new Intent(AppApplication.getContext(), GrayService.class));

//        initIM();
//        initUserInfo();
    }

    private void initUserInfo() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getFriends(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    UserInfo userInfo = gson.fromJson(gson.toJson(msg.getResult()), UserInfo.class);

                }else {
                    BaseToast.ToastL(IndexAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(IndexAct.this,errormsg);
            }
        });
    }

    private void initIM() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getChatToken(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    Config.IMTOKEN = (String) msg.getResult();
                    RongIM.connect(Config.IMTOKEN, new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {

                        }

                        @Override
                        public void onSuccess(String s) {

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            BaseToast.YToastL(IndexAct.this,errorCode.getValue()+"");
                        }
                    });
                }else {
                    BaseToast.YToastL(IndexAct.this,"获取融云token失败");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastL(IndexAct.this,"获取融云token失败");
            }
        });
    }

    @Override
    public void initdata() {
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),addfragments());
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentitem);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                changepage(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateapkVersion();
    }

    private void updateapkVersion() {
        boolean isNet = CommonUtil.isNetworkAvailable(IndexAct.this);
        final boolean isWifi = CommonUtil.isWifi(IndexAct.this);

        if (!isNet){
            BaseToast.YToastS(IndexAct.this,"亲,没有网络！");
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
            int oldversion = Integer.valueOf(APKVersionCodeUtils.getVerName(IndexAct.this).replace(".",""));
            if (newversion > oldversion){
                if (isWifi){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(IndexAct.this);
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("当前版本有重要更新！！！");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateapk(versionModel.getApp_path());
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(IndexAct.this);
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
                BaseToast.YToastS(IndexAct.this,"已是最高版本");
            }
        }

        @Override
        public void error(String errormsg) {
            BaseToast.YToastS(IndexAct.this,errormsg);
        }
    });
    }

    private void updateapk(String app_path) {
//        DownDialog downDialog = new DownDialog(this,false,app_path);
//        downDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void onMessageEvent(NearbyStoreBean bean) {

    }

    @OnClick({R.id.rb_microstore,R.id.rb_discover,R.id.rb_circle,R.id.rb_myinfo})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.rb_microstore://首页
                pager.setCurrentItem(0);
                changepage(0);
                break;
            case R.id.rb_discover://开店
                pager.setCurrentItem(1);
                changepage(1);
                break;
            case R.id.rb_circle://圈子
                pager.setCurrentItem(2);
                changepage(2);
                break;
            case R.id.rb_myinfo://我的
                pager.setCurrentItem(3);
                changepage(3);
                break;
        }
    }

    public void setTab(int num){
        pager.setCurrentItem(num);
        changepage(num);
    }

    private void changepage(int i) {
        switch (i){
            case 0:
                rb_microstore.setChecked(true);
                rb_myinfo.setChecked(false);
                rb_discover.setChecked(false);
                rb_circle.setChecked(false);
                rb_microstore.setTextColor(getResources().getColor(R.color.rb_red));
                rb_discover.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_circle.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_myinfo.setTextColor(getResources().getColor(R.color.rb_gray));
                break;
            case 1:
                rb_microstore.setChecked(false);
                rb_discover.setChecked(true);
                rb_circle.setChecked(false);
                rb_myinfo.setChecked(false);
                rb_microstore.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_discover.setTextColor(getResources().getColor(R.color.rb_red));
                rb_circle.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_myinfo.setTextColor(getResources().getColor(R.color.rb_gray));
                break;
            case 2:
                rb_microstore.setChecked(false);
                rb_discover.setChecked(false);
                rb_circle.setChecked(true);
                rb_myinfo.setChecked(false);
                rb_microstore.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_discover.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_circle.setTextColor(getResources().getColor(R.color.rb_red));
                rb_myinfo.setTextColor(getResources().getColor(R.color.rb_gray));
                break;
            case 3:
                rb_microstore.setChecked(false);
                rb_discover.setChecked(false);
                rb_circle.setChecked(false);
                rb_myinfo.setChecked(true);
                rb_microstore.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_discover.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_circle.setTextColor(getResources().getColor(R.color.rb_gray));
                rb_myinfo.setTextColor(getResources().getColor(R.color.rb_red));
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private List<Fragment> addfragments() {
        lists = new ArrayList<>();
        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        ThreeFragment threeFragment = new ThreeFragment();
        FourFragment fourFragment = new FourFragment();

        lists.add(oneFragment);
        lists.add(twoFragment);
        lists.add(threeFragment);
        lists.add(fourFragment);

        return lists;
    }
}
