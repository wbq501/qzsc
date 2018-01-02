package com.zoomtk.circle.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.VisitorBean;
import com.zoomtk.circle.utils.HttpUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wbq501 on 2017/12/4.
 */

public class VisitorAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.yue)
    TextView yue;

    @BindView(R.id.order1)
    TextView order1;
    @BindView(R.id.order2)
    TextView order2;
    @BindView(R.id.order3)
    TextView order3;

    @BindView(R.id.weixin_money1)
    TextView weixin_money1;
    @BindView(R.id.weixin_money2)
    TextView weixin_money2;
    @BindView(R.id.weixin_money3)
    TextView weixin_money3;

    @BindView(R.id.money1)
    TextView money1;
    @BindView(R.id.money2)
    TextView money2;
    @BindView(R.id.money3)
    TextView money3;

    @BindView(R.id.qu_money1)
    TextView qu_money1;
    @BindView(R.id.qu_money2)
    TextView qu_money2;
    @BindView(R.id.qu_money3)
    TextView qu_money3;

    @BindView(R.id.fh_money1)
    TextView fh_money1;
    @BindView(R.id.fh_money2)
    TextView fh_money2;
    @BindView(R.id.fh_money3)
    TextView fh_money3;

    @BindView(R.id.z_money1)
    TextView z_money1;
    @BindView(R.id.z_money2)
    TextView z_money2;
    @BindView(R.id.z_money3)
    TextView z_money3;

    @BindView(R.id.f_money1)
    TextView f_money1;
    @BindView(R.id.f_money2)
    TextView f_money2;
    @BindView(R.id.f_money3)
    TextView f_money3;

    @BindView(R.id.t_money1)
    TextView t_money1;
    @BindView(R.id.t_money2)
    TextView t_money2;
    @BindView(R.id.t_money3)
    TextView t_money3;

    @BindView(R.id.b_money1)
    TextView b_money1;
    @BindView(R.id.b_money2)
    TextView b_money2;
    @BindView(R.id.b_money3)
    TextView b_money3;


    private String money;

    @Override
    public int getLayoutId() {
        return R.layout.activity_visitor;
    }

    @Override
    public void init() {
        money = getIntent().getStringExtra("money");
        yue.setText("￥"+money);
    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.wshopMonthIncome(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    VisitorBean visitorBean = gson.fromJson(gson.toJson(msg), VisitorBean.class);
                    VisitorBean.CommonOrderBean common_order = visitorBean.getCommon_order();//普通订单
                    VisitorBean.TransferOrderBean transfer_order = visitorBean.getTransfer_order();//微信收款
                    VisitorBean.GoodsClickBean goods_click = visitorBean.getGoods_click();//点击分享
                    VisitorBean.GoodsShareBean goods_share = visitorBean.getGoods_share();//分享
                    VisitorBean.RegBillBean reg_bill = visitorBean.getReg_bill();//注册击飞
                    VisitorBean.WkMissionBean wk_mission = visitorBean.getWk_mission();//赏金任务
                    VisitorBean.RegionalAgentBean regional_agent = visitorBean.getRegional_agent();//区域代理
                    VisitorBean.ShareAgentBean share_agent = visitorBean.getShare_agent();//分红佣金
                    VisitorBean.ChiefAgentBean chief_agent = visitorBean.getChief_agent();//总团队长
                    VisitorBean.ParentGentBean parent_gent = visitorBean.getParent_gent();//父团队长
                    VisitorBean.TotalAgentBean total_agent = visitorBean.getTotal_agent();//团队长佣金
                    VisitorBean.DisAgentBean dis_agent = visitorBean.getDis_agent();//伯乐佣金

                    DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

                    order1.setText(decimalFormat.format(common_order.getClosed()));
                    order2.setText(decimalFormat.format(common_order.getUnclosed()));
                    order3.setText(decimalFormat.format(common_order.getUnconfirmed()));
                    weixin_money1.setText(decimalFormat.format(transfer_order.getClosed()));
                    weixin_money2.setText(decimalFormat.format(transfer_order.getUnclosed()));
                    weixin_money3.setText(decimalFormat.format(transfer_order.getUnconfirmed()));
                    money1.setText(decimalFormat.format(wk_mission.getClosed()));
                    money2.setText(decimalFormat.format(wk_mission.getUnclosed()));
                    money3.setText(decimalFormat.format(wk_mission.getUnconfirmed()));
                    qu_money1.setText(decimalFormat.format(regional_agent.getClosed()));
                    qu_money2.setText(decimalFormat.format(regional_agent.getUnclosed()));
                    qu_money3.setText(decimalFormat.format(regional_agent.getUnconfirmed()));
                    fh_money1.setText(decimalFormat.format(share_agent.getClosed()));
                    fh_money2.setText(decimalFormat.format(share_agent.getUnclosed()));
                    fh_money3.setText(decimalFormat.format(share_agent.getUnconfirmed()));
                    z_money1.setText(decimalFormat.format(chief_agent.getClosed()));
                    z_money2.setText(decimalFormat.format(chief_agent.getUnclosed()));
                    z_money3.setText(decimalFormat.format(chief_agent.getUnconfirmed()));
                    f_money1.setText(decimalFormat.format(parent_gent.getClosed()));
                    f_money2.setText(decimalFormat.format(parent_gent.getUnclosed()));
                    f_money3.setText(decimalFormat.format(parent_gent.getUnconfirmed()));
                    t_money1.setText(decimalFormat.format(total_agent.getClosed()));
                    t_money2.setText(decimalFormat.format(total_agent.getUnclosed()));
                    t_money3.setText(decimalFormat.format(total_agent.getUnconfirmed()));
                    b_money1.setText(decimalFormat.format(dis_agent.getClosed()));
                    b_money2.setText(decimalFormat.format(dis_agent.getUnclosed()));
                    b_money3.setText(decimalFormat.format(dis_agent.getUnconfirmed()));
                }else {
                    BaseToast.YToastS(VisitorAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(VisitorAct.this,errormsg);
            }
        });
    }

}
