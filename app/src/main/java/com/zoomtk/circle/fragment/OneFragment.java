package com.zoomtk.circle.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Config.URLConst;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.AddrActivity;
import com.zoomtk.circle.activity.ShopingDetils;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.GetStoreURLModel;
import com.zoomtk.circle.bean.NearbyStoreBean;
import com.zoomtk.circle.bean.ShareBean;
import com.zoomtk.circle.dialog.ShareDialog;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by wbq501 on 2017/10/30.
 */

public class OneFragment extends BaseFragment {
    private static String TAG = "OneFragment";

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_addr)
    TextView tv_addr;
    @BindView(R.id.back_home)
    LinearLayout back_home;
    @BindView(R.id.title_right)
    LinearLayout title_right;
    @BindView(R.id.go_car)
    FrameLayout go_car;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.fl)
    FrameLayout fl;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    private double latitude;
    private double lontitude;
    private String strAddr;
    private boolean dwnum = true;

    private boolean isStore = false;
    private boolean isadress = true;
    private boolean iswebFinish = false;

    private String authCode, url, memberId, mystoreurl;

    private String openurl = "http://www" + URLConst.allUrl;
    private String title = AppApplication.getContext().getString(R.string.app_name);
    private String tvContext = AppApplication.getContext().getString(R.string.app_name);
    private String imgurl;
    private String mainurl;
    private String poptitle = "此商品由" + AppApplication.getContext().getString(R.string.app_domain) + "提供";

    public boolean needClearHistory = false;

    private String sid;
    private String shopname;

    NearbyStoreBean bean = null;
    ShareDialog shareDialog;

    private int adressnum = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void init() {
        bean = new NearbyStoreBean();
        initLocation();
    }

    @Override
    protected void initdata() {

    }

    private void loadmsg() {
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        parms.put("lat", latitude + "");
        parms.put("lon", lontitude + "");
        parms.put("app_system", "android");
        parms.put("app_shop", "1");
        HttpUtils.getOfflineOnline(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)) {
                    fl.setVisibility(View.VISIBLE);
                    loadingview.setVisibility(View.GONE);
                    GetStoreURLModel urlModel = gson.fromJson(gson.toJson(msg.getResult()), GetStoreURLModel.class);
                    authCode = urlModel.getAuthCode().getAuth_code();
                    if (isStore) {
                        url = mystoreurl;
                        isStore = false;
                    } else {
                        url = urlModel.getUrl();
                    }
                    memberId = urlModel.getAuthCode().getMember_id();
                    sid = urlModel.getSid();
                    shopname = urlModel.getWshop_name();
                    openurl = url.replace("/from/android", "");
                    title = "欢迎光临" + urlModel.getWshop_name() + "的商城！优惠哦！";
                    tvContext = urlModel.getWshop_name() + "开的商城卖的全都是品牌厂家直接发货的正品，价格优惠！支持我，请在上面挑选商品购物吧";
                    imgurl = urlModel.getAvatar();
                    if (imgurl == null || imgurl.equals(""))
                        imgurl = "http://www" + URLConst.allUrl + "images/morentouxiang.png";

                    loadurl();
                } else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo() + "");
                    fl.setVisibility(View.GONE);
                    loadingview.setShowType(BaseConfig.LOADING_NO);
                    loadingview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initLocation();
                        }
                    });
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(getActivity(), Config.errormsg);
                fl.setVisibility(View.GONE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                loadingview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initLocation();
                    }
                });
            }
        });
    }

    private void loadurl() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        mainurl = url + "&login_type=app&auth_code=" + authCode + "&uid=" + memberId;
        webView.loadUrl(mainurl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                if (needClearHistory) {
                    needClearHistory = false;
                    view.clearHistory();//清除历史记录
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                iswebFinish = true;
                poptitle = view.getTitle();
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) {
                    openDetils(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setAlpha(1.0f);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.back_home,R.id.go_car,R.id.adress,R.id.title_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back://分类
                openDetils(URLConst.URL+"/addon/WShop/Index/goodsClassify.html");
                break;
            case R.id.back_home://回到当前位置主页
                initLocation();
                break;
            case R.id.go_car://购物车
                openDetils(URLConst.URL+"/addon/WShop/Cart/cart.html");
                break;
            case R.id.adress://点击地址跳转
                Intent intent = new Intent(getActivity(), AddrActivity.class);
                intent.putExtra("nearbyStoreBean",bean);
                startactivityForResult(intent,500);
                break;
            case R.id.title_right:
                getShare();
                break;
        }
    }

    private void getShare() {
        if (!iswebFinish){
            BaseToast.YToastL(getActivity(),"页面还在加载中...");
            return;
        }
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("type",1+"");
        HttpUtils.getShareParameters(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    ShareBean shareBean = gson.fromJson(gson.toJson(msg.getResult()), ShareBean.class);
                    shareDialog = new ShareDialog(getActivity(),shareBean.getShare_title(),shareBean.getShare_content()
                            ,shareBean.getShare_url(),shareBean.getShare_image(),poptitle,itemsOnClick);
                    shareDialog.showAtLocation(view.findViewById(R.id.activity_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });
    }

    View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shareDialog.dismiss();
            switch (v.getId()){
                case R.id.ll_refresh:
                    reresh();
                    break;
                case R.id.man_img:
                    kefu();
                    break;
                case R.id.ll_type:
                    openDetils(URLConst.URL+"/addon/WShop/Index/goodsClassify.html");
                    break;
                case R.id.car_img:
                    openDetils(URLConst.URL+"/addon/WShop/Cart/cart.html");
                    break;
            }
        }
    };

    private void reresh() {
        String urls = webView.getUrl();
        String s = url + "&login_type=app&auth_code=" + authCode + "&uid=" + memberId;
        if (urls == null)
            return;
        if (urls.equals(s)) {
            webView.loadUrl(url);
        }else {
            webView.reload();
        }
    }

    private void kefu() {

    }

    private void openDetils(String url) {
        Intent intent = new Intent(getActivity(), ShopingDetils.class);
        intent.putExtra("url", url);
        intent.putExtra("sid", sid);
        intent.putExtra("shopname", shopname);
        intent.putExtra("mainurl", mainurl);
        intent.putExtra("imgurl", imgurl);
        intent.putExtra("openurl", openurl);
        startactivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
        Bundle bundle = getActivity().getIntent().getBundleExtra("bundle");
        if (bundle != null && isadress) {
            isadress = false;
            authCode = bundle.getString("authCode");
            url = bundle.getString("url");
            memberId = bundle.getString("memberId");
            tv_addr.setText(bundle.getString("strAddr"));
            loadurl();
        }
    }

    @Subscribe
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void onMessageEvent(NearbyStoreBean bean) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
        }
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.pauseTimers();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && data != null){
            NearbyStoreBean nearbyStoreBean = (NearbyStoreBean) data.getSerializableExtra("nearbyStoreBean");
            Bundle bundle = data.getBundleExtra("bundle");
            if (bundle != null){
                authCode = bundle.getString("authCode");
                url = bundle.getString("url");
                strAddr = bundle.getString("strAddr");
                memberId = bundle.getString("memberId");
                tv_addr.setText(strAddr);
                loadurl();
            }else {
                initLocation();
            }
        }
    }

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();    //获取纬度信息
            lontitude = location.getLongitude();    //获取经度信息
            strAddr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String streetNumber = location.getStreetNumber();
            tv_addr.setText(street+""+streetNumber);
            bean.setCity(city);
            bean.setLat(latitude+"");
            bean.setLng(lontitude+"");
            if (bean != null){
                adressnum ++;
            }
            if (adressnum <= 1){
                loadmsg();
            }
        }
    }

    private void initLocation() {

        new RxPermissions(getActivity()).request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean)
                            BaseToast.YToastS(getActivity(),"部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                    }
                });

        mLocationClient = new LocationClient(getActivity().getApplication());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        mLocationClient.start();
    }


}
