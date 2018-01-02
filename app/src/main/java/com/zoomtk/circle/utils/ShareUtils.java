package com.zoomtk.circle.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zoomtk.circle.activity.PKActivity;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.ShareBean;

/**
 * Created by wbq501 on 2017/11/24.
 */

public class ShareUtils {

    public static void ShareAdress(final Activity activity, final UMShareListener umShareListener,final ShareBean shareBean){
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                .addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_copyurl", "umeng_socialize_copy", "umeng_socialize_copy")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")){
                            ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setText(shareBean.getShare_url());
                            BaseToast.YToastS(activity,"复制成功");
                        }else {
                            UMImage umImage = new UMImage(activity,shareBean.getShare_image());
                            UMWeb web = new UMWeb(shareBean.getShare_url());
                            web.setTitle(shareBean.getShare_title());
                            web.setThumb(umImage);
                            new ShareAction(activity)
                                    .setPlatform(share_media)//传入平台
                                    .withText(shareBean.getShare_content())//分享内容
                                    .withMedia(web)
                                    .setCallback(umShareListener)//回调监听器
                                    .share();
                        }
                    }
                }).open();
    }

    public static void ShareAdressType(final Activity activity, final UMShareListener umShareListener,
                                       final ShareBean shareBeanWX,final ShareBean shareBeanWXC,final ShareBean shareBeanSina){
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                .addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_copyurl", "umeng_socialize_copy", "umeng_socialize_copy")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")){
                            ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setText(shareBeanWX.getShare_url());
                            BaseToast.YToastS(activity,"复制成功");
                        }else {
                            UMImage umImage = new UMImage(activity,shareBeanWX.getShare_image());
                            UMWeb web = new UMWeb(shareBeanWX.getShare_url());
                            if (share_media == SHARE_MEDIA.SINA){
                                web.setTitle(shareBeanSina.getShare_title());
                                web.setThumb(umImage);
                                new ShareAction(activity)
                                        .setPlatform(share_media)//传入平台
                                        .withText(shareBeanSina.getShare_content())//分享内容
                                        .withMedia(web)
                                        .setCallback(umShareListener)//回调监听器
                                        .share();
                            }else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE){
                                web.setTitle(shareBeanWXC.getShare_title());
                                web.setThumb(umImage);
                                new ShareAction(activity)
                                        .setPlatform(share_media)//传入平台
                                        .withText(shareBeanWXC.getShare_content())//分享内容
                                        .withMedia(web)
                                        .setCallback(umShareListener)//回调监听器
                                        .share();
                            }else {
                                web.setTitle(shareBeanWX.getShare_title());
                                web.setThumb(umImage);
                                new ShareAction(activity)
                                        .setPlatform(share_media)//传入平台
                                        .withText(shareBeanWX.getShare_content())//分享内容
                                        .withMedia(web)
                                        .setCallback(umShareListener)//回调监听器
                                        .share();
                            }
                        }
                    }
                }).open();
    }
}
