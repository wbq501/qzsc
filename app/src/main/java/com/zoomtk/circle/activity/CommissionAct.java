package com.zoomtk.circle.activity;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CommissionBean;
import com.zoomtk.circle.bean.StoreManageModel;
import com.zoomtk.circle.dialog.MaterialDialog;
import com.zoomtk.circle.utils.HttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/4.
 */

public class CommissionAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.yue)
    TextView yue;
    @BindView(R.id.bendianshouru)
    TextView bendianshouru;
    @BindView(R.id.hehuoren)
    TextView hehuoren;
    @BindView(R.id.fenhong)
    TextView fenhong;
    @BindView(R.id.zhijie)
    TextView zhijie;
    @BindView(R.id.quyu)
    TextView quyu;
    @BindView(R.id.all_duizhang)
    TextView all_duizhang;
    @BindView(R.id.duizhang)
    TextView duizhang;
    @BindView(R.id.weishouhuo1)
    TextView weishouhuo1;
    @BindView(R.id.zs_money)
    TextView zs_money;

    @BindView(R.id.weishouhuo)
    TextView weishouhuo;
    @BindView(R.id.yishouhuo)
    TextView yishouhuo;
    @BindView(R.id.rela)
    RelativeLayout rela;
    @BindView(R.id.dtxyj)
    LinearLayout dtxyj;

    @BindView(R.id.xsyj1)
    TextView xsyj1;
    @BindView(R.id.hhryj1)
    TextView hhryj1;
    @BindView(R.id.fhyj1)
    TextView fhyj1;
    @BindView(R.id.sjrw1)
    TextView sjrw1;
    @BindView(R.id.qydlyj1)
    TextView qydlyj1;
    @BindView(R.id.zjyj1)
    TextView zjyj1;
    @BindView(R.id.sjjlyj1)
    TextView sjjlyj1;
    @BindView(R.id.jlyj1)
    TextView jlyj1;
    @BindView(R.id.blyj1)
    TextView blyj1;

    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.shengqingtixian)
    TextView shengqingtixian;
    @BindView(R.id.tishi)
    TextView tishi;

    private String transfer_type = "";
    private boolean is_dtx = true;
    private int br_id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commission;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.back,R.id.rela,R.id.shengqingtixian,R.id.detail,R.id.account})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rela:
                if (is_dtx){
                    dtxyj.setVisibility(View.VISIBLE);
                    is_dtx = false;
                }else {
                    dtxyj.setVisibility(View.GONE);
                    is_dtx = true;
                }
                break;
            case R.id.shengqingtixian:
                requestWithdrawa();
                break;
            case R.id.detail:
                startActivity(new Intent(CommissionAct.this, CommissionDetail.class));
                break;
            case R.id.account:
                open();
                break;
        }
    }

    private void requestWithdrawa() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.withdrawnApply(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String resultcode = msg.getResultCode();
                    final MaterialDialog dialog = new MaterialDialog(CommissionAct.this);
                    View view = LayoutInflater.from(CommissionAct.this).inflate(R.layout.dialog_commison, null);
                    dialog.setContentView(view);
                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int width = metrics.widthPixels / 8;

                    TextView title = (TextView) view.findViewById(R.id.hint);
                    TextView content = (TextView) view.findViewById(R.id.content);
                    TextView sure = (TextView) view.findViewById(R.id.delt);
                    switch (resultcode) {
                        case "-1001":
                            title.setText("您还没绑定openid");
                            sure.setVisibility(View.GONE);
                            dialog.setNegativeButton("去绑定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    open();
                                }
                            });
                            dialog.setPositiveButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                            break;
                        case "-1002":
                            title.setText("您还没设置真实姓名");
                            sure.setVisibility(View.GONE);
                            dialog.setNegativeButton("设置", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    open();
                                }
                            });
                            dialog.setPositiveButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                            break;
                        case Config.SUCCESS_CODE:
                            initdata();
                            JSONObject object = new JSONObject(gson.toJson(msg.getResult()));
                            if (transfer_type.equals("0")){
                                content.setText("申请成功,请等待审核");
                            }else {
                                content.setText("请到提现详情中查看");
                                br_id = object.getInt("br_id");
                            }
                            sure.setVisibility(View.VISIBLE);
                            sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    if (transfer_type.equals("0")){

                                    }else {
                                        Intent intent = new Intent(CommissionAct.this,DetailCommissionAct.class);
                                        intent.putExtra("id",br_id+"");
                                        startActivity(intent);
                                    }
                                }
                            });
                            dialog.setCanceledOnTouchOutside(true);
                            break;

                        default:
                            String resultinfo = msg.getResultInfo() == null ? "" : msg.getResultInfo();
                            title.setText(resultinfo);
                            sure.setVisibility(View.VISIBLE);
                            sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCanceledOnTouchOutside(true);
                            break;
                    }
                    dialog.show();
                    dialog.setMargine(width, 0, width, 0);
                }else {
                    BaseToast.YToastS(CommissionAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(CommissionAct.this,errormsg);
            }
        });
    }

    private void open() {
        if (transfer_type.equals("") || transfer_type == null){
            BaseToast.YToastS(CommissionAct.this,"数据还在加载中...");
            return;
        }
        Intent intent = new Intent(CommissionAct.this, NewWithdrawalsAct.class);
        intent.putExtra("type",Integer.parseInt(transfer_type));
        startActivity(intent);
    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.myBrokerage(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    CommissionBean commissionBean = gson.fromJson(result, CommissionBean.class);
                    yue.setText("￥" + commissionBean.getTotal_closed());
                    weishouhuo.setText("￥" + commissionBean.getTotal_unconfirmed());
                    yishouhuo.setText("￥" + commissionBean.getTotal_closed());
                    bendianshouru.setText(commissionBean.getSelf_unclosed());
                    hehuoren.setText(commissionBean.getPartners_unclosed());
                    tishi.setText(commissionBean.getTip_str() == null ? "" : commissionBean.getTip_str());
                    fenhong.setText(commissionBean.getShareboss_unclosed());
                    quyu.setText(commissionBean.getRegionalAgent_unclosed());

                    all_duizhang.setText(commissionBean.getChiefAgent_unclosed());
                    duizhang.setText(commissionBean.getPartners_unclosed());
                    weishouhuo1.setText(commissionBean.getTotalAgent_unclosed());
                    zs_money.setText(commissionBean.getDiamondDistribution_unclosed());

                    xsyj1.setText(commissionBean.getSelf_unconfirmed());//销售佣金
                    hhryj1.setText(commissionBean.getPartners_unconfirmed());//合伙人佣金
                    sjrw1.setText(commissionBean.getWkmission_unconfirmed());//赏金任务
                    fhyj1.setText(commissionBean.getShareboss_unconfirmed());//分红佣金
                    qydlyj1.setText(commissionBean.getRegionalAgent_unconfirmed());//区域代理
                    zjyj1.setText(commissionBean.getChiefAgent_unconfirmed());//总监佣金
                    sjjlyj1.setText(commissionBean.getParentAgent_unconfirmed());//上级经理
                    jlyj1.setText(commissionBean.getTotal_unconfirmed());//经理佣金
                    blyj1.setText(commissionBean.getDiamondDistribution_unconfirmed());//伯乐佣金

                    transfer_type = commissionBean.getTransfer_type();
                }else {
                    BaseToast.YToastS(CommissionAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(CommissionAct.this,errormsg);
            }
        });
    }
}
