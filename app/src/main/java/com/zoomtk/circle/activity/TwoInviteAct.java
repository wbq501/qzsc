package com.zoomtk.circle.activity;

import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Config.URLConst;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.ErWeiMaModel;
import com.zoomtk.circle.down.DownLoadManager;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.utils.RetrofitFactory;

import java.io.File;
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

public class TwoInviteAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.twoinvite_im01)
    ImageView twoinvite_im01;
    @BindView(R.id.twoinvite_sharefriends)
    ImageButton twoinvite_sharefriends;
    @BindView(R.id.twoinvite_load)
    ImageButton twoinvite_load;

    private String imgurl;
    private String name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_twoinvite;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.scanCode(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    ErWeiMaModel erWeiMaModel = gson.fromJson(result, ErWeiMaModel.class);
                    imgurl = erWeiMaModel.getPic();
                    name = erWeiMaModel.getMember_truename();
                    ImgLoading.LoadCache(TwoInviteAct.this,imgurl,twoinvite_im01);
                }else {
                    BaseToast.YToastS(TwoInviteAct.this, msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(TwoInviteAct.this, BaseConfig.TOEAST);
            }
        });
    }

    @OnClick({R.id.back,R.id.twoinvite_load,R.id.twoinvite_sharefriends})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.twoinvite_load:
                down();
                break;
            case R.id.twoinvite_sharefriends:
                share();
                break;
        }
    }

    private void share() {
        if (TextUtils.isEmpty(imgurl)){
            BaseToast.YToastS(TwoInviteAct.this,"暂无数据");
            return;
        }else {
            if (!CommonUtil.isWeinxinAvilible(TwoInviteAct.this)){
                BaseToast.YToastS(TwoInviteAct.this,"您还没有安装微信");
                return;
            }
            UMImage umImage = new UMImage(TwoInviteAct.this,imgurl);
            UMWeb web = new UMWeb(URLConst.URL + "/addon/WShop/Index/index/sid/" + AppApplication.sid + ".html");
            web.setTitle("我是" + name + "，我正在招募合伙人，开店赚钱零费用零门槛，厂家发货，一对一指导");
            web.setThumb(umImage);
            new ShareAction(TwoInviteAct.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                    .withText("我是" + name + "，我正在招募合伙人，开店赚钱零费用零门槛，厂家发货，一对一指导")//分享内容
                    .withMedia(web)
                    .setCallback(umShareListener)//回调监听器
                    .share();
        }
    }

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            BaseToast.YToastS(TwoInviteAct.this,"请稍等...");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            BaseToast.YToastS(TwoInviteAct.this,"分享成功...");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            BaseToast.YToastS(TwoInviteAct.this,"分享失败...");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            BaseToast.YToastS(TwoInviteAct.this,"分享取消...");
        }
    };

    private void down() {
        if (TextUtils.isEmpty(imgurl)){
            BaseToast.YToastS(TwoInviteAct.this,"暂无数据");
            return;
        }else {
            Observable<ResponseBody> download = RetrofitFactory.getInstence().API().download(null,imgurl);
            download.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable d) {
                    BaseToast.YToastS(TwoInviteAct.this,"开始下载...");
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    if (DownLoadManager.writeResponseBodyToDisk(TwoInviteAct.this,responseBody)){
                        BaseToast.YToastS(TwoInviteAct.this,"下载完成...");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    BaseToast.YToastS(TwoInviteAct.this,"下载错误...");
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }
}
