package com.zoomtk.circle.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.zoomtk.circle.bean.LoginAuthCodeModel;
import com.zoomtk.circle.bean.NearbyStoreInfo;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class DingDanWebAct extends BaseActivity{

    @BindView(R.id.webview_dingdan)
    WebView webView;

    private String url = "http://www.jingruan.cn";
    String member_id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ding_dan_web;
    }

    @Override
    public void init() {
        member_id = getSharedPreferences("passwordFile", MODE_PRIVATE).getString("member_id", "");
        url=getIntent().getStringExtra("url");
        boolean canUsercookie = comPareCookie(url);
        if (canUsercookie) {
            String webur = url + "&login_type=autoapplogin";
            webView.loadUrl(webur);
        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            //WebView加载web资源
            Map<String,String> parms = new HashMap<>();
            parms.put("token",token);
            HttpUtils.getLoginAuthCode(parms, new RequestBack() {
                @Override
                public void success(BaseJson msg) throws Exception {
                    if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                        LoginAuthCodeModel loginAuthCodeModel = gson.fromJson(gson.toJson(msg.getResult()), LoginAuthCodeModel.class);
                        String weburl = url + "&sid=" + AppApplication.sid + "&login_type=app&uid=" + loginAuthCodeModel.getMember_id() + "&auth_code=" + loginAuthCodeModel.getAuth_code();
                        webView.loadUrl(weburl);
                    }else {
                        BaseToast.YToastS(DingDanWebAct.this,msg.getResultInfo());
                    }
                }

                @Override
                public void error(String errormsg) {
                    BaseToast.YToastS(DingDanWebAct.this,errormsg);
                }
            });
        }
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        //优先使用缓存
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.addJavascriptInterface(new Pay(),"android");
        EventBus.getDefault().register(DingDanWebAct.this);
    }

    @OnClick({R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();
    }

    @Override
    public void initdata() {

    }

    public class Pay{
        @JavascriptInterface
        public void sendReq(String WX_APP_ID,String appId,String partnerId,String prepayId,
                            String packageValue,String nonceStr,String timeStamp,
                            String sign){
            if (!CommonUtil.isWeinxinAvilible(DingDanWebAct.this)){
                BaseToast.YToastS(DingDanWebAct.this,"您还没有安装微信,请先安装微信");
                return;
            }

            IWXAPI api = WXAPIFactory.createWXAPI(DingDanWebAct.this, WX_APP_ID);
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
            payRequest.sign = CommonUtil.createSign("UTF-8",parameters);
            api.sendReq(payRequest);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null){
            webView.clearHistory();
            webView.clearCache(true);
        }
        EventBus.getDefault().unregister(DingDanWebAct.this);
    }

    public void onEventMainThread(EventMsg msg){
        Log.e("aaa",msg.getBaseReq().errCode+"");
        BaseResp resp = msg.getBaseReq();
        if (resp.errCode == 0){
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            pushandroid();
        }else if(resp.errCode == -1){
            Toast.makeText(this, "支付失败" + resp.errCode + "str:" + resp.errStr, Toast.LENGTH_SHORT).show();
        }else if(resp.errCode == -2){
            Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
//            pushandroid();
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
                BaseToast.ToastL(DingDanWebAct.this, Config.errormsg);
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
                BaseToast.ToastL(DingDanWebAct.this, Config.errormsg);
            }
        });
    }

    //判断cookie是否可用
    protected boolean comPareCookie(String url) {
        if (url == null) return false;
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);

        try {
            if (cookie.contains("wxm_key")) {
                String[] map = cookie.split(";");
                for (String mapkey : map) {
                    if (mapkey.contains("open_id=") && mapkey.trim().equals("open_id=" + member_id)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
