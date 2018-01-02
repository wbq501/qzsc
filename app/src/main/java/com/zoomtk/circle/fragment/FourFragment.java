package com.zoomtk.circle.fragment;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ut.device.UTDevice;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Config.URLConst;
import com.zoomtk.circle.IndexAct;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.DingDanWebAct;
import com.zoomtk.circle.activity.HelpAct;
import com.zoomtk.circle.activity.MoneyDetilsAct;
import com.zoomtk.circle.activity.MyInfo_SetAct;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.MYINFoModle;
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
 * Created by wbq501 on 2017/10/30.
 */

public class FourFragment extends BaseFragment {

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.personcenter_title)
    TextView personcenter_title;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.friends_count)
    TextView friends_count;
    @BindView(R.id.circle_count)
    TextView circle_count;
    @BindView(R.id.fllow_count)
    TextView fllow_count;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.goto_home, R.id.chat, R.id.avatar, R.id.re_sotreManage, R.id.re_help, R.id.re_fukuan, R.id.re_fahuo, R.id.re_shouhuo, R.id.re_pingjia,
            R.id.re_shouhou, R.id.re_order, R.id.all, R.id.rl_money})
    public void OnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.goto_home:
                IndexAct indexAct = (IndexAct) getActivity();
                indexAct.setTab(0);
                break;
            case R.id.chat://暂时没做
                break;
            case R.id.avatar://上传头像
                new RxPermissions(getActivity()).request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean)
                                    goTakePhoto();
                                else
                                    BaseToast.YToastS(getActivity(), "部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                            }
                        });
                break;
            case R.id.re_sotreManage://设置
                intent = new Intent(getActivity(), MyInfo_SetAct.class);
                startactivity(intent);
                break;
            case R.id.re_help://帮助反馈 没做完
                intent = new Intent(getActivity(), HelpAct.class);
                startactivity(intent);
                break;
            case R.id.rl_money://零钱转账
                intent = new Intent(getActivity(), MoneyDetilsAct.class);
                startactivity(intent);
                break;
            case R.id.re_fukuan://待付款
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/Member/myOrderList/type/new.html");
                startactivity(intent);
                break;
            case R.id.re_fahuo://发货
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/Member/myOrderList/type/pay.html");
                startactivity(intent);
                break;
            case R.id.re_shouhuo://收货
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/Member/myOrderList/type/send.html");
                startactivity(intent);
                break;
            case R.id.re_pingjia://评价
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/Member/myOrderList/type/eva.html");
                startactivity(intent);
                break;
            case R.id.re_shouhou://售后
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/CustomerService/index.html");
                startactivity(intent);
                break;
            case R.id.re_order://我是买家
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/Member/index.html");
                startactivity(intent);
                break;
            case R.id.all://所有订单
                intent = new Intent(getActivity(), DingDanWebAct.class);
                intent.putExtra("url", "http://" + URLConst.adressurl + "index.php?s=/addon/WShop/Member/myOrderList/type/all.html");
                startactivity(intent);
                break;
        }
    }

    @Override
    protected void initdata() {
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);

        HttpUtils.myMenu(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)) {
                    MYINFoModle myinFoModle = gson.fromJson(gson.toJson(msg.getResult()), MYINFoModle.class);
                    ImgLoading.LoadCircle(getActivity(), myinFoModle.getMember_avatar(), avatar);
                    friends_count.setText(myinFoModle.getFriend_num());
                    circle_count.setText(myinFoModle.getCircle_num());
                    fllow_count.setText(myinFoModle.getAttention());
                    user_name.setText(myinFoModle.getMember_truename() + "  |  " + myinFoModle.getMember_name());
                    personcenter_title.setText(myinFoModle.getMember_truename() + "的店铺");
                } else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });
    }

    private void goTakePhoto() {
        final EditHeadIconDialog dialog = new EditHeadIconDialog(getActivity());
        dialog.setCallBack(new EditHeadIconDialog.CallBack() {
            @Override
            public void camera() {
                dialog.cancel();
            }

            @Override
            public void photo() {
                PictureSelector.create(FourFragment.this)
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
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
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
            }
        }
    }

    private void upheadimg(final String headimgpath) {
        Map<String, String> parms = new HashMap<>();
        parms.put("token", token);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(headimgpath));
        HttpUtils.uploadAvatar(requestBody, parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                cleanCache();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)) {
                    BaseToast.ToastS(getActivity(), "上传成功");
                    ImgLoading.LoadCircle(getActivity(), headimgpath, avatar);
                } else {
                    BaseToast.ToastS(getActivity(), msg.getResultInfo() + "");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(getActivity(), errormsg + "");
                cleanCache();
            }
        });
    }

    private void cleanCache() {
        new RxPermissions(getActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean)
                            //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                            PictureFileUtils.deleteCacheDirFile(getActivity());
                        else
                            BaseToast.YToastS(getActivity(), "部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                    }
                });
    }
}
