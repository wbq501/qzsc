package com.zoomtk.circle.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.StoreManageModel;
import com.zoomtk.circle.fragment.TwoFragment;
import com.zoomtk.circle.utils.CacheDataManager;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.view.EditHeadIconDialog;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wbq501 on 2017/12/12.
 * 店铺管理
 */

public class StoreManageAct extends BaseActivity{

    @BindView(R.id.store_name)
    TextView store_name;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.store_abstract)
    TextView store_abstract;
    @BindView(R.id.store_cover)
    TextView store_phone;
    @BindView(R.id.store_suozaidiweishezhi)
    TextView store_adress;
    @BindView(R.id.store_dianputouxiang)
    RoundedImageView store_head;


    @Override
    public int getLayoutId() {
        return R.layout.activity_storemanage;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.storeManage(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    StoreManageModel storeManageModel = gson.fromJson(gson.toJson(msg.getResult()), StoreManageModel.class);
                    store_name.setText(storeManageModel.getW_storename());
                    name.setText(storeManageModel.getMember_truename());
                    store_phone.setText(storeManageModel.getPhone());
                    store_adress.setText(storeManageModel.getAddress());
                    store_abstract.setText(storeManageModel.getStore_info());
                    ImgLoading.LoadCircle(StoreManageAct.this,storeManageModel.getAvatar(),store_head);
                }else {
                    BaseToast.YToastL(StoreManageAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastL(StoreManageAct.this,errormsg);
            }
        });
    }

    @OnClick({R.id.back,R.id.edit_name,R.id.truename,R.id.edit_abstract,R.id.edit_cover,R.id.store_suozaidiweishezhi,
                R.id.edit_dianputouxiang,R.id.clean,R.id.change_pass})
    public void OnClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.edit_name://店铺名称
                intent = new Intent(StoreManageAct.this,StoreEditAct.class);
                intent.putExtra("editkey",Config.STORE_NAME);
                intent.putExtra("editvalue",store_name.getText().toString().trim());
                startactivityForResult(intent,Config.STORE_NAME);
                break;
            case R.id.truename://真实名称
                intent = new Intent(StoreManageAct.this,StoreEditAct.class);
                intent.putExtra("editkey",Config.NAME);
                intent.putExtra("editvalue",name.getText().toString().trim());
                startactivityForResult(intent,Config.NAME);
                break;
            case R.id.edit_abstract://店铺简介
                intent = new Intent(StoreManageAct.this,StoreEditAct.class);
                intent.putExtra("editkey",Config.STORE_ABSTRACT);
                intent.putExtra("editvalue",store_abstract.getText().toString().trim());
                startactivityForResult(intent,Config.STORE_ABSTRACT);
                break;
            case R.id.edit_cover://联系电话
                break;
            case R.id.store_suozaidiweishezhi://所在地区
                intent = new Intent(StoreManageAct.this,UpdateAddrActivity.class);
                startactivityForResult(intent,1000);
                break;
            case R.id.edit_dianputouxiang://店铺头像
                new RxPermissions(StoreManageAct.this).request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean)
                                    goTakePhoto();
                                else
                                    BaseToast.YToastS(StoreManageAct.this,"部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                            }
                        });
                break;
            case R.id.change_pass://修改密码
                intent = new Intent(StoreManageAct.this,PasswordReset.class);
                startactivity(intent);
                break;
            case R.id.clean://清理缓存
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            handler.sendEmptyMessage(3);
                            CacheDataManager.clearAllCache(StoreManageAct.this);
                            Thread.sleep(2000);
                            if (CacheDataManager.getTotalCacheSize(StoreManageAct.this).startsWith("0")){
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
        }
    }

    private void goTakePhoto() {
        final EditHeadIconDialog dialog = new EditHeadIconDialog(StoreManageAct.this);
        dialog.setCallBack(new EditHeadIconDialog.CallBack() {
            @Override
            public void camera() {
                dialog.cancel();
            }

            @Override
            public void photo() {
                PictureSelector.create(StoreManageAct.this)
                        .openGallery(PictureMimeType.ofImage())
                        .imageSpanCount(3)
                        .previewImage(true)
                        .enableCrop(true)
                        .isCamera(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(true)
                        .maxSelectNum(1)
                        .showCropGrid(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            String result = data.getStringExtra("editvalue");
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    upheadimg(selectList.get(0).getCutPath());
                    break;
                case 11:
                    store_name.setText(result);
                    break;
                case 12:
                    store_abstract.setText(result);
                    break;
                case 20:
                    name.setText(result);
                    break;
                case 1000:
                    String provicename=data.getStringExtra("provicename");
                    String cityname=data.getStringExtra("cityname");
                    String districtname=data.getStringExtra("districtname");
                    String city=provicename+cityname+districtname+data.getStringExtra("detailedaddress");
                    store_adress.setText(city);
                    break;
            }
        }
    }

    private void upheadimg(final String headimgpath) {
        Map<String, String> parms = new HashMap<>();
        parms.put("token",token);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),new File(headimgpath));
        HttpUtils.uploadAvatar(requestBody,parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                cleanCache();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    BaseToast.ToastS(StoreManageAct.this,"上传成功");
                    ImgLoading.LoadCircle(StoreManageAct.this,headimgpath,store_head);
                }else {
                    BaseToast.ToastS(StoreManageAct.this,msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(StoreManageAct.this,errormsg+"");
                cleanCache();
            }
        });
    }

    private void cleanCache() {
        new RxPermissions(StoreManageAct.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean)
                            //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                            PictureFileUtils.deleteCacheDirFile(StoreManageAct.this);
                        else
                            BaseToast.YToastS(StoreManageAct.this,"部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                    }
                });
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    BaseToast.YToastS(StoreManageAct.this,"清理缓存完成");
                    break;
                case 1:
                    BaseToast.YToastS(StoreManageAct.this,"清理缓存失败,再试一次");
                    break;
                case 3:
                    BaseToast.YToastS(StoreManageAct.this,"清理缓存开始");
                    break;
            }
        }
    };
}
