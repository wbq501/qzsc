package com.zoomtk.circle.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.IndexAct;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.LoginActivity;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.CommissionAct;
import com.zoomtk.circle.activity.InviteAct;
import com.zoomtk.circle.activity.McheckPartnerAct;
import com.zoomtk.circle.activity.NewWithdrawalsAct;
import com.zoomtk.circle.activity.StoreManageAct;
import com.zoomtk.circle.activity.VisitorAct;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.KaiDianBean;
import com.zoomtk.circle.bean.LoginInfoModel;
import com.zoomtk.circle.bean.MYINFoModle;
import com.zoomtk.circle.bean.StoreManageModel;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.view.EditHeadIconDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wbq501 on 2017/10/30.
 */

public class TwoFragment extends BaseFragment{

    @BindView(R.id.is_kaidian)
    LinearLayout is_kaidian;
    @BindView(R.id.kaidian_msg)
    TextView kaidian_msg;
    @BindView(R.id.back_main)
    Button back_main;

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.wo_store)
    LinearLayout wo_store;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone_num)
    TextView phone_num;
    @BindView(R.id.my_store)
    TextView my_store;
    @BindView(R.id.money1)
    TextView money1;
    @BindView(R.id.prompting6)
    ImageView prompting6;
    @BindView(R.id.money2)
    TextView money2;
    @BindView(R.id.prompting7)
    ImageView prompting7;
    @BindView(R.id.rl_menu1)
    RelativeLayout rl_menu1;
    @BindView(R.id.rl_menu2)
    RelativeLayout rl_menu2;
    @BindView(R.id.rl_menu3)
    RelativeLayout rl_menu3;
    @BindView(R.id.rl_menu4)
    RelativeLayout rl_menu4;
    @BindView(R.id.rl_menu5)
    RelativeLayout rl_menu5;
    @BindView(R.id.rl_menu6)
    RelativeLayout rl_menu6;
    @BindView(R.id.rl_man1)
    RelativeLayout rl_man1;
    @BindView(R.id.rl_man2)
    RelativeLayout rl_man2;
    @BindView(R.id.rl_man3)
    RelativeLayout rl_man3;
    @BindView(R.id.menu1_num)
    TextView menu1_num;
    @BindView(R.id.menu2_num)
    TextView menu2_num;
    @BindView(R.id.menu3_num)
    TextView menu3_num;
    @BindView(R.id.menu4_num)
    TextView menu4_num;
    @BindView(R.id.man_num1)
    TextView man_num1;
    @BindView(R.id.man_num2)
    TextView man_num2;
    @BindView(R.id.ll_money1)
    LinearLayout ll_money1;
    @BindView(R.id.ll_money2)
    LinearLayout ll_money2;

    private int order_total;
    private int goods_total;
    private int team_total;
    private int member_total;
    private int make_money_total;
    private SharedPreferences kaidiannum;
    KaiDianBean kaiDianBean;

    private StoreManageModel manageModel;

    private Uri imgUri;
    private String imagePath;
    private String mystoreurl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void init() {
        kaidiannum = getActivity().getSharedPreferences("kaidiannum", Context.MODE_PRIVATE);

        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadmsg();
            }
        });
        smart_refresh.setEnableLoadmore(false);
    }

    @Override
    protected void initdata() {
        loadother();
        loadmsg();
    }

    private void loadmsg() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getWshopInfo(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    LoginInfoModel loginInfoModel = gson.fromJson(gson.toJson(msg.getResult()), LoginInfoModel.class);
                    mystoreurl = loginInfoModel.getWshop_url();
                    Double curr_month_money = loginInfoModel.getCurr_month_income();
                    Double totalmoney = loginInfoModel.getTotal_income();
                    money2.setText("" + totalmoney);
                    money1.setText("" + curr_month_money);
                    AppApplication.sid = Integer.parseInt(loginInfoModel.getSid());

                    SharedPreferences sp_storeName = getActivity().getSharedPreferences("passwordFile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp_storeName.edit();
                    editor.putString("storeName", loginInfoModel.getW_storename());
                    editor.commit();
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });
    }

    private void loadother() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);

        HttpUtils.storeManage(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    manageModel = gson.fromJson(gson.toJson(msg.getResult()),StoreManageModel.class);
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });

        HttpUtils.myMenu(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    MYINFoModle myinFoModle = gson.fromJson(gson.toJson(msg.getResult()), MYINFoModle.class);
                    ImgLoading.LoadCircle(getActivity(),myinFoModle.getMember_avatar(),avatar);

                    name.setText(myinFoModle.getMember_truename());
                    phone_num.setText(myinFoModle.getMember_name());
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });
    }

    //menu 右上角数字
    private void loadnum() {

        order_total = kaidiannum.getInt("order_total",0);
        goods_total = kaidiannum.getInt("goods_total",0);
        team_total = kaidiannum.getInt("team_total",0);
        member_total = kaidiannum.getInt("member_total",0);
        make_money_total = kaidiannum.getInt("make_money_total",0);

        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getOpenStoreCount(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    kaiDianBean = gson.fromJson(gson.toJson(msg.getResult()),KaiDianBean.class);
                    int num1 = kaiDianBean.getOrder_total();

                    if (num1 == 0){
                        menu1_num.setVisibility(View.GONE);
                    }else if (num1 > 99){
                        menu1_num.setVisibility(View.VISIBLE);
                        menu1_num.setText("99+");
                    }else {
                        menu1_num.setVisibility(View.VISIBLE);
                        menu1_num.setText(num1+"");
                    }

                    int num2 = kaiDianBean.getMember_total();
                    if (num2 == 0){
                        menu2_num.setVisibility(View.GONE);
                    }else if (num2 > 99){
                        menu2_num.setVisibility(View.VISIBLE);
                        menu2_num.setText("99+");
                    }else {
                        menu2_num.setVisibility(View.VISIBLE);
                        menu2_num.setText(num2+"");
                    }

                    int num3 = kaiDianBean.getMake_money_total();
                    if (num3 == 0){
                        menu3_num.setVisibility(View.GONE);
                    }else if (num3 > 99){
                        menu3_num.setVisibility(View.GONE);
                        menu3_num.setText("99+");
                    }else {
                        menu3_num.setVisibility(View.GONE);
                        menu3_num.setText(num3+"");
                    }

                    int num4 = kaiDianBean.getGoods_total();
                    if (num4 == 0){
                        menu4_num.setVisibility(View.GONE);
                    }else if (num4 > 99){
                        menu4_num.setVisibility(View.VISIBLE);
                        menu4_num.setText("99+");
                    }else {
                        menu4_num.setVisibility(View.VISIBLE);
                        menu4_num.setText(num4+"");
                    }

                    int man1 = kaiDianBean.getN_team_total();
                    if (man1 == 0){
                        man_num1.setVisibility(View.GONE);
                    }else if (man1 > 99){
                        man_num1.setVisibility(View.GONE);
                        man_num1.setText("99+");
                    }else {
                        man_num1.setVisibility(View.GONE);
                        man_num1.setText(man1+"");
                    }

                    int man2 = kaiDianBean.getTeam_total();
                    if (man2 == 0){
                        man_num2.setText("");
                    }else if (man2 > 99){
                        man_num2.setText("99+");
                    }else {
                        man_num2.setText(man2+"");
                    }
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });
    }

    @OnClick({R.id.my_store,R.id.avatar,R.id.rl_man1,R.id.rl_man2,R.id.rl_man3,R.id.rl_menu1,R.id.rl_menu5,
            R.id.rl_menu2,R.id.rl_menu3,R.id.rl_menu4,R.id.ll_money1,R.id.ll_money2})
    public void OnClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.my_store:
//                IndexAct indexAct = (IndexAct) getActivity();
//                indexAct.setTab(0);
                break;
            case R.id.avatar:
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
                                    BaseToast.YToastS(getActivity(),"部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                            }
                        });

                break;
            case R.id.rl_man2://我的团队
                if (kaiDianBean != null){
                    SharedPreferences.Editor editor = kaidiannum.edit();
                    editor.putInt("team_total", kaiDianBean.getTeam_total());
                    editor.commit();
                }
                intent = new Intent(getActivity(),McheckPartnerAct.class);
                startactivity(intent);
                break;
            case R.id.rl_man1://邀请队员
                intent = new Intent(getActivity(),InviteAct.class);
                startactivity(intent);
                break;
            case R.id.ll_money1://营业额
                intent = new Intent(getActivity(),VisitorAct.class);
                intent.putExtra("money",money1.getText());
                startactivity(intent);
                break;
            case R.id.ll_money2://待提现
                intent = new Intent(getActivity(), CommissionAct.class);
                startactivity(intent);
                break;
            case R.id.rl_man3://提现账户
                intent = new Intent(getActivity(), NewWithdrawalsAct.class);
                startactivity(intent);
                break;
            case R.id.rl_menu1://订单管理
                break;
            case R.id.rl_menu4://商品管理
                break;
            case R.id.rl_menu5://店铺管理
                intent = new Intent(getActivity(),StoreManageAct.class);
                startactivity(intent);
                break;
            case R.id.rl_menu2://客户管理
                break;
            case R.id.rl_menu3://赏金任务
                break;
        }
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
                PictureSelector.create(TwoFragment.this)
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
        if (resultCode == getActivity().RESULT_OK){
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
                    BaseToast.ToastS(getActivity(),"上传成功");
                    ImgLoading.LoadCircle(getActivity(),headimgpath,avatar);
                }else {
                    BaseToast.ToastS(getActivity(),msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(getActivity(),errormsg+"");
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
                            BaseToast.YToastS(getActivity(),"部分权限被拒绝,请在设置里面开启相应权限,若无相应权限会影响使用");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadnum();
    }
}
