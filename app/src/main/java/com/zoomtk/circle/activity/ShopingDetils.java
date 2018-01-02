package com.zoomtk.circle.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Config.URLConst;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.EventMsg;
import com.zoomtk.circle.bean.GetStoreURLModel;
import com.zoomtk.circle.bean.NearbyStoreInfo;
import com.zoomtk.circle.bean.ShareBean;
import com.zoomtk.circle.bean.ShopingDetilsBean;
import com.zoomtk.circle.dialog.ShareDialog;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.MD5Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/10/30.
 */

public class ShopingDetils extends BaseActivity {

    @BindView(R.id.iv_back)
    LinearLayout iv_back;
    @BindView(R.id.close)
    TextView close;
    @BindView(R.id.tv)
    TextView tv_title;
    @BindView(R.id.title_right)
    LinearLayout title_right;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.webView)
    WebView webView;

    private String url;
    private String mainurl;
    private String shopname;
    private String sid;
    private String imgurl;
    private String openurl = "http://www"+ URLConst.allUrl;
    private String title = AppApplication.getContext().getString(R.string.app_name);
    private String tvContext = AppApplication.getContext().getString(R.string.app_name);
    private String initurl;

    private String poptitle = "此商品由"+ AppApplication.getContext().getString(R.string.app_domain)+"提供";
    private boolean isOne = true;

    private int sharetype = 1;//分享的类型
    private String newshareurl = "http://www"+URLConst.allUrl;
    private String newsharetitle = AppApplication.getContext().getString(R.string.app_name);
    private String newshareContext = AppApplication.getContext().getString(R.string.app_name);

    private boolean iswebFinish = false;
    ShareDialog shareDialog;

    @Override
    public int getLayoutId() {
        return R.layout.shopingdetils;
    }

    @Override
    public void init() {
        url = getIntent().getStringExtra("url");
        mainurl = getIntent().getStringExtra("mainurl");
        shopname = getIntent().getStringExtra("shopname");
        sid = getIntent().getStringExtra("sid");
        imgurl = getIntent().getStringExtra("imgurl");
        openurl = getIntent().getStringExtra("openurl");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        initurl = url;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                iswebFinish = true;
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                String title = view.getTitle();
                if (title.contains("WShop/Chat")) {
                    tv_title.setText("");
                } else {
                    tv_title.setText(title);
                }
                poptitle = view.getTitle();
                if (url.contains("goodsDetail")) {
                    sharetype = 2;
                    newshareurl = url;
                    loadshare(url);
                } else if (url.contains("show/v")) {
                    sharetype = 2;
                    newshareurl = url;
                    loadshare(url);
                } else {
                    sharetype = 1;
                    newshareurl = url;
                    ShopingDetils.this.title = "欢迎光临" + shopname + "的商城！优惠哦！";
                    tvContext = shopname + "开的商城卖的全都是品牌厂家直接发货的正品，价格优惠！支持我，请在上面挑选商品购物吧";
                    openurl = getIntent().getStringExtra("openurl");
                    imgurl = getIntent().getStringExtra("imgurl");
                    if (imgurl == null || imgurl.equals(""))
                        imgurl = "http://www" + URLConst.allUrl + "images/morentouxiang.png";
                }
                if (isOne) {
                    initurl = view.getUrl();
                    isOne = false;
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (ShopingDetils.this.isDestroyed())
                    return;
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setAlpha(1.0f);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("Index/index.html")) {
                    webView.clearHistory();
                    finish();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    if (ShopingDetils.this.isDestroyed())
                        return;
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (ShopingDetils.this.isDestroyed())
                        return;
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        webView.addJavascriptInterface(new Pay(), "android");
        EventBus.getDefault().register(ShopingDetils.this);
    }

    private void loadshare(String url) {
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("url",url);
        HttpUtils.getShareContent(param, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    ShopingDetilsBean shopingDetilsBean = gson.fromJson(gson.toJson(msg.getResult()), ShopingDetilsBean.class);
                    title = shopingDetilsBean.getShare_title();
                    tvContext = shopingDetilsBean.getShare_content();
                    if (shopingDetilsBean.getUrl().contains("sid")){
                        openurl = shopingDetilsBean.getUrl();
                    }else {
                        openurl = shopingDetilsBean.getUrl()+"/sid/"+sid;
                    }
                    imgurl = shopingDetilsBean.getImage_url();
                    if (imgurl == null || imgurl.equals(""))
                        imgurl = "http://www"+URLConst.allUrl+"images/morentouxiang.png";
                }else {
                    BaseToast.ToastL(ShopingDetils.this, msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(ShopingDetils.this, Config.errormsg);
            }
        });
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.close,R.id.iv_back,R.id.title_right})
    public void OnClicke(View view){
        switch (view.getId()){
            case R.id.close:
                webView.clearHistory();
                finish();
                break;
            case R.id.iv_back:
                webView.goBack();
                if(webView.getUrl().equals(initurl)){
                    finish();
                }
                break;
            case R.id.title_right:
                if (!iswebFinish){
                    BaseToast.YToastS(ShopingDetils.this,"页面还在加载中...");
                    return;
                }
                goshare();
                break;
        }
    }

    private void goshare() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("type",1+"");
        if (sharetype == 1){

        }else {
            parms.put("url",newshareurl);
        }
        HttpUtils.getShareParameters(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    ShareBean shareBean = gson.fromJson(gson.toJson(msg.getResult()), ShareBean.class);
                    shareDialog = new ShareDialog(ShopingDetils.this,shareBean.getShare_title(),shareBean.getShare_content()
                            ,shareBean.getShare_url(),shareBean.getShare_image(),poptitle,itemsOnClick);
                    shareDialog.showAtLocation(findViewById(R.id.activity_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                }else {
                    BaseToast.ToastL(ShopingDetils.this, msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(ShopingDetils.this, Config.errormsg);
            }
        });
    }

    View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shareDialog.dismiss();
            switch (v.getId()){
                case R.id.ll_refresh:
                    String urls = webView.getUrl();
                    String s = url;
                    if (urls == null)
                        return;
                    if (urls.equals(s)) {
                        webView.loadUrl(url);
                    }else {
                        webView.reload();
                    }
                    break;
                case R.id.man_img:
                    kefu();
                    break;
                case R.id.ll_type:
                    webView.loadUrl(URLConst.URL+"/addon/WShop/Index/goodsClassify.html");
                    break;
                case R.id.car_img:
                    webView.loadUrl(URLConst.URL+"/addon/WShop/Cart/cart.html");
                    break;
            }
        }
    };

    private void kefu() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMsg msg){
        Log.e("aaa",msg.getBaseReq().errCode+"");
        BaseResp resp = msg.getBaseReq();
        if (resp.errCode == 0){
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            pushandroid();
        }else if(resp.errCode == -1){
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        }else if(resp.errCode == -2){
            Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
            pushandroid();
        }else {
            Toast.makeText(this, "支付出错", Toast.LENGTH_SHORT).show();
        }
    }

    private void pushandroid(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", getSharedPreferences("passwordFile", Context.MODE_PRIVATE).getString("token", ""));
        map.put("lat", NearbyStoreInfo.lat);
        map.put("lon", NearbyStoreInfo.lng);
        map.put("app_system", "android");
        HttpUtils.getOfflineOnline(map, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    payfinish();
                }else {
                    payfinish();
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(ShopingDetils.this, Config.errormsg);
            }
        });
    }

    boolean isPay = false;
    private void payfinish() {
        final Map<String, String> map = new HashMap<>();
        map.put("app_system", "android");
        map.put("token", token);
        HttpUtils.getPayCallBackUrl(map, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    webView.loadUrl(gson.toJson(msg.getResult()));
                    isPay = true;
                }else {
                    webView.loadUrl(URLConst.URL+"/addon/WShop/Member/myOrderList/from/android");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(ShopingDetils.this, Config.errormsg);
            }
        });
    }

    public class Pay{
        @JavascriptInterface
        public void sendReq(String WX_APP_ID,String appId,String partnerId,String prepayId,
                            String packageValue,String nonceStr,String timeStamp,
                            String sign){
            if (!isWeixinAvilible(ShopingDetils.this)){
                BaseToast.YToastS(ShopingDetils.this,"您还没有安装微信,请先安装微信");
                return;
            }
            IWXAPI api = WXAPIFactory.createWXAPI(ShopingDetils.this, WX_APP_ID);
            api.registerApp(WX_APP_ID);
            PayReq payRequest = new PayReq();
            payRequest.appId = WX_APP_ID;//微信开放平台审核通过的应用APPID
            payRequest.partnerId = partnerId;//微信支付分配的商户号
            payRequest.prepayId = prepayId;//微信返回的支付交易会话ID    预支付订单号
            payRequest.packageValue = "Sign=WXPay";//固定字符串
            payRequest.nonceStr = nonceStr;//随机字符串，不长于32位。推荐随机数生成算法
            payRequest.timeStamp = timeStamp;//时间戳，请见接口规则-参数规定
//            payRequest.sign = sign;//签名，详见签名生成算法
            payRequest.extData = "app data";
            SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
            parameters.put("appid", WX_APP_ID);
            parameters.put("noncestr", nonceStr);//随机字符串
            parameters.put("package", packageValue);
            parameters.put("partnerid", partnerId);
            parameters.put("prepayid", prepayId);
            parameters.put("timestamp", timeStamp);
            payRequest.sign = createSign("UTF-8",parameters);
            api.sendReq(payRequest);
        }
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 微信支付签名算法sign
     *
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + AppApplication.getContext().getString(R.string.weixinappkey)); //KEY是商户秘钥
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding)
                .toUpperCase();
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null){
            webView.clearHistory();
            webView.clearCache(true);
        }
        EventBus.getDefault().unregister(ShopingDetils.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            if(webView.getUrl().equals(initurl)){
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
