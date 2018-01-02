package com.zoomtk.circle.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.DistriButorModels;
import com.zoomtk.circle.dialog.HihtDialog;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/23.
 */

public class McheckPartnerAct extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.pk)
    TextView pk;
    @BindView(R.id.mcheck_num)
    TextView mcheck_num;
    @BindView(R.id.prompting1)
    ImageView prompting1;
    @BindView(R.id.mcheck_xiaoshou)
    TextView mcheck_xiaoshou;
    @BindView(R.id.prompting2)
    ImageView prompting2;
    @BindView(R.id.mcheck_leijidingdanshu)
    TextView mcheck_leijidingdanshu;
    @BindView(R.id.prompting3)
    ImageView prompting3;
    @BindView(R.id.mcheck_leijihuiyuanshu)
    TextView mcheck_leijihuiyuanshu;
    @BindView(R.id.prompting4)
    ImageView prompting4;
    @BindView(R.id.mcheck_check)
    Button mcheck_check;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ckeckpartneryiji;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.back,R.id.pk,R.id.prompting1,R.id.prompting2,R.id.prompting3,R.id.prompting4,R.id.mcheck_check})
    public void OnClick(View view){
        HihtDialog dialog = null;
        Intent intent = null;
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.pk:
                intent = new Intent(McheckPartnerAct.this,PKActivity.class);
                startactivity(intent);
                break;
            case R.id.mcheck_check:
                intent = new Intent(McheckPartnerAct.this,CheckPartnerAct.class);
                startactivity(intent);
                break;
            case R.id.prompting1:
                dialog = new HihtDialog(McheckPartnerAct.this,"off_dis_num",token);//朋友开店
                dialog.show();
                break;
            case R.id.prompting2:
                dialog = new HihtDialog(McheckPartnerAct.this,"sale_amount",token);//累计销量
                dialog.show();
                break;
            case R.id.prompting3:
                dialog = new HihtDialog(McheckPartnerAct.this,"sale_order",token);//累计订单数
                dialog.show();
                break;
            case R.id.prompting4:
                dialog = new HihtDialog(McheckPartnerAct.this,"total_member",token);//累计队员数
                dialog.show();
                break;
        }
    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.myDistributor(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    DistriButorModels distriButorModels = gson.fromJson(gson.toJson(msg.getResult()), DistriButorModels.class);
                    mcheck_num.setText(distriButorModels.getFirst().getMember_num());
                    mcheck_xiaoshou.setText(distriButorModels.getFirst().getSale_amount());
                    mcheck_leijidingdanshu.setText(distriButorModels.getFirst().getSale_order());
                    mcheck_leijihuiyuanshu.setText(distriButorModels.getTotal_num());
                }else {
                    BaseToast.ToastL(McheckPartnerAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastL(McheckPartnerAct.this,errormsg);
            }
        });
    }
}
