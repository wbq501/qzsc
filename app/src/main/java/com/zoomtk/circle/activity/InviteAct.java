package com.zoomtk.circle.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.ShareBean;
import com.zoomtk.circle.bean.StoreManageModel;
import com.zoomtk.circle.bean.StoreShareData;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.RetrofitFactory;
import com.zoomtk.circle.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by wbq501 on 2017/11/28.
 */

public class InviteAct extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.rl_code)
    RelativeLayout rl_code;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_context)
    TextView tv_context;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invitepartner;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        Observable<ResponseBody> loginCode = RetrofitFactory.getInstence().API().GetGonggao(HttpUtils.getRequestparams(parms));
        loginCode.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    if (result == null){
                        BaseToast.YToastS(InviteAct.this,BaseConfig.TOEAST);
                    }else {
                        JSONObject object = new JSONObject(result);
                        tv_msg.setText(object.getString("public_tell_title")+"");
                        tv_context.setText(Html.fromHtml(object.getString("public_tell_content"))+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                BaseToast.YToastS(InviteAct.this, BaseConfig.TOEAST);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @OnClick({R.id.back,R.id.rl_code,R.id.rl_share})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rl_share:
                getShareContext();
                break;
            case R.id.rl_code:
                Intent intent = new Intent(InviteAct.this,TwoInviteAct.class);
                startactivity(intent);
                break;
        }
    }

    private void getShareContext() {
        final Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("type",3+"");
        HttpUtils.getStoreShare(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    StoreShareData storeShareData = gson.fromJson(gson.toJson(msg.getResult()), StoreShareData.class);
                    getShareLink(parms,storeShareData);
                }else {
                    BaseToast.YToastS(InviteAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(InviteAct.this,errormsg);
            }
        });
    }

    private void getShareLink(final Map<String, String> parms, final StoreShareData storeShareData) {
        parms.put("sid", AppApplication.sid+"");
        HttpUtils.appShareLink(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                String sharelink = "";
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    JSONObject object = new JSONObject(result);
                    sharelink = object.getString("share_link");
                    getStoreInfo(parms,sharelink,storeShareData);
                }else {
                    BaseToast.YToastS(InviteAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(InviteAct.this,errormsg);
            }
        });
    }

    private void getStoreInfo(final Map<String, String> parms, final String sharelink, final StoreShareData storeShareData) {
        HttpUtils.storeManage(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    StoreManageModel storeManageModel = gson.fromJson(gson.toJson(msg.getResult()),StoreManageModel.class);
                    getshare(parms,sharelink,storeShareData,storeManageModel);
                }else {
                    BaseToast.YToastS(InviteAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(InviteAct.this,errormsg);
            }
        });
    }

    private void getshare(final Map<String, String> parms, final String sharelink, final StoreShareData storeShareData, final StoreManageModel storeManageModel) {
        HttpUtils.getShareParameters(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    ShareBean shareBean = gson.fromJson(result,ShareBean.class);
                    ShareBean shareWX = new ShareBean();
                    ShareBean shareWXC = new ShareBean();
                    ShareBean shareSina = new ShareBean();

//                    if (TextUtils.isEmpty(sharelink)){
                        shareWX.setShare_url(shareBean.getShare_url());
                        shareWXC.setShare_url(shareBean.getShare_url());
                        shareSina.setShare_url(shareBean.getShare_url());
//                    }else {
//                        shareWX.setShare_url(sharelink);
//                        shareWXC.setShare_url(sharelink);
//                        shareSina.setShare_url(sharelink);
//                    }
                    String member_truename = storeManageModel.getMember_truename();
                    shareWX.setShare_title(storeShareData.getWeixin_title().replace("{$name}",member_truename == null ? "":member_truename));
                    shareWX.setShare_content(storeShareData.getWeixin_body().replace("{$name}",member_truename == null ? "":member_truename));
                    shareWX.setShare_image(shareBean.getShare_image());

                    shareWXC.setShare_title(storeShareData.getWeixin_friend_title().replace("{$name}",member_truename == null ? "":member_truename));
                    shareWXC.setShare_content(storeShareData.getWeixin_body().replace("{$name}",member_truename == null ? "":member_truename));
                    shareWXC.setShare_image(shareBean.getShare_image());

                    shareSina.setShare_title(storeShareData.getMicroblog_title().replace("{$name}",member_truename == null ? "":member_truename)+"链接地址");
                    shareSina.setShare_content(storeShareData.getWeixin_body().replace("{$name}",member_truename == null ? "":member_truename)+"链接地址");
                    shareSina.setShare_image(shareBean.getShare_image());

                    ShareUtils.ShareAdressType(InviteAct.this,umShareListener,shareWX,shareWXC,shareSina);
                }else {
                    BaseToast.YToastS(InviteAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(InviteAct.this,errormsg);
            }
        });
    }

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(InviteAct.this,"请稍等...",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(InviteAct.this,"分享成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable t) {
            Toast.makeText(InviteAct.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(InviteAct.this,"取消分享",Toast.LENGTH_LONG).show();
        }
    };
}
