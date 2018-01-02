package com.zoomtk.circle.activity;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.ShareBean;
import com.zoomtk.circle.bean.StoreManageModel;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ShareUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wbq501 on 2017/11/23.
 */

public class PKActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_right)
    LinearLayout title_right;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.pk_web)
    WebView pk_web;

    private int currentProgress;
    private boolean isFinished = false;

    @Override
    public int getLayoutId() {
        return R.layout.pk_layout;
    }

    @Override
    public void init() {
        title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PKActivity.this,"请稍等...",Toast.LENGTH_LONG).show();
                Map<String,String> parm = new HashMap<>();
                parm.put("token",token);
                parm.put("type",6+"");//每页的分享类型
                HttpUtils.getShareParameters(parm, new RequestBack() {
                    @Override
                    public void success(BaseJson msg) throws Exception {
                        if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                            ShareBean shareBean = gson.fromJson(gson.toJson(msg.getResult()), ShareBean.class);
                            ShareUtils.ShareAdress(PKActivity.this,umShareListener,shareBean);
                        }else {
                            BaseToast.YToastS(PKActivity.this,msg.getResultInfo());
                        }
                    }

                    @Override
                    public void error(String errormsg) {
                        BaseToast.YToastS(PKActivity.this,errormsg);
                    }
                });
            }
        });
    }

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(PKActivity.this,"请稍等...",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(PKActivity.this,"分享成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable t) {
            Toast.makeText(PKActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(PKActivity.this,"取消分享",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void initdata() {
        Map<String,String> prams = new HashMap<>();
        prams.put("token",token);
        HttpUtils.storeManage(prams, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    StoreManageModel storeManageModel = gson.fromJson(gson.toJson(msg.getResult()), StoreManageModel.class);
                    loadmsg(storeManageModel.getStore_id());
                }else {
                    BaseToast.YToastS(PKActivity.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(PKActivity.this,errormsg);
            }
        });
    }

    private void loadmsg(String store_id) {
        Map<String,String> prams = new HashMap<>();
        prams.put("token",token);
        prams.put("store_id",store_id);
        HttpUtils.getAgentTeamPkUrl(prams, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    JSONObject object = new JSONObject(gson.toJson(msg.getResult()));
                    loadurl(object.getString("url"));
                }else {
                    BaseToast.YToastS(PKActivity.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(PKActivity.this,errormsg);
            }
        });
    }

    private void loadurl(String url) {
        WebSettings webSettings = pk_web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        pk_web.setWebChromeClient(new WebChromeClient());
        pk_web.loadUrl(url);
        pk_web.setWebViewClient(new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                isFinished = true;
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setAlpha(1.0f);
            }
        });

        pk_web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                currentProgress = mProgressBar.getProgress();
                if (PKActivity.this.isDestroyed())
                    return;
                if (newProgress >= 100){
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pk_web != null){
            pk_web.clearHistory();
            pk_web.clearCache(true);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && pk_web.canGoBack()){
            pk_web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
