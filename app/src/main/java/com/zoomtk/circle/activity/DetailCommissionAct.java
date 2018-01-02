package com.zoomtk.circle.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.ImgAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.DateUtils;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.CustomGridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class DetailCommissionAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tixian)
    TextView tixian;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.is_fail)
    TextView is_fail;
    @BindView(R.id.is_weixin)
    LinearLayout is_weixin;
    @BindView(R.id.rl_remark1)
    RelativeLayout rl_remark1;
    @BindView(R.id.remark1)
    TextView remark1;
    @BindView(R.id.remark2)
    TextView remark2;
    @BindView(R.id.rl_ishide)
    RelativeLayout rl_ishide;
    @BindView(R.id.pingz)
    TextView pingz;
    @BindView(R.id.gridView)
    CustomGridView gridView;
    @BindView(R.id.sure)
    Button sure;
    @BindView(R.id.tishi)
    TextView tishi;


    private String id;
    ImgAdapter imgadapter;
    ArrayList<String> imglists = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.detailcommission;
    }

    @Override
    public void init() {
        id = getIntent().getStringExtra("id");
        imgadapter = new ImgAdapter(DetailCommissionAct.this,imglists);
        gridView.setAdapter(imgadapter);
    }

    @OnClick({R.id.back,R.id.sure})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                Intent intent = new Intent();
                setResult(100,intent);
                finish();
                break;
            case R.id.sure:
                if (sure.getText().toString().trim().equals("确认")){
                    sure();
                }else {
                    subapply();
                }
                break;
        }
    }

    private void subapply() {
        Map<String,String> parms = new HashMap<>();
        parms.put("id",id);
        parms.put("token",token);
        HttpUtils.restartBrokerageWithdraw(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    BaseToast.YToastS(DetailCommissionAct.this,"成功");
                    Intent intent = new Intent();
                    setResult(100,intent);
                    finish();
                }else {
                    BaseToast.YToastS(DetailCommissionAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(DetailCommissionAct.this,errormsg);
            }
        });
    }

    private void sure() {
        Map<String,String> parms = new HashMap<>();
        parms.put("id",id);
        parms.put("token",token);
        HttpUtils.confirm(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    BaseToast.YToastS(DetailCommissionAct.this,"成功");
                    Intent intent = new Intent();
                    setResult(100,intent);
                    finish();
                }else {
                    BaseToast.YToastS(DetailCommissionAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(DetailCommissionAct.this,errormsg);
            }
        });
    }

    @Override
    public void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("id",id);
        parms.put("token",token);
        HttpUtils.getInfo(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());

                    JSONObject object = new JSONObject(result);
                    money.setText(object.getString("total_amount"));
                    time.setText(DateUtils.timeTodate(object.getString("create_time")));
                    type.setText(object.getString("brokerage_desc"));
                    String close_type = object.getString("close_type");
                    if (close_type.equals("1")){//银行转账
                        is_weixin.setVisibility(View.VISIBLE);
                        tishi.setVisibility(View.VISIBLE);
                        String status = object.getString("status");
                        if (status.equals("3")){
                            sure.setText("确认");
                            sure.setVisibility(View.VISIBLE);
                            tishi.setVisibility(View.VISIBLE);
                        }else if (status.equals("0")){
                            sure.setText("重新申请");
                            sure.setVisibility(View.VISIBLE);
                            tishi.setVisibility(View.GONE);
                        }else {
                            sure.setVisibility(View.GONE);
                            tishi.setVisibility(View.GONE);
                        }
                    }else {
                        is_weixin.setVisibility(View.VISIBLE);
                        rl_remark1.setVisibility(View.GONE);
                        rl_ishide.setVisibility(View.GONE);
                        String status = object.getString("status");
                        if (status.equals("0")){
                            sure.setText("重新申请");
                            sure.setVisibility(View.VISIBLE);
                            tishi.setVisibility(View.GONE);
                        }else {
                            sure.setVisibility(View.GONE);
                            tishi.setVisibility(View.GONE);
                        }
                        if (object.getString("brokerage_desc").equals("失败")){
                            tishi.setVisibility(View.VISIBLE);
                            is_fail.setVisibility(View.VISIBLE);
                            String description = object.getString("description");
                            is_fail.setText(description);
                            tishi.setText("如果提示余额不足，请隔日再重新发起提现；或七个工作日之内，我们会主动给您打款。");
                        }else {
                            is_fail.setVisibility(View.GONE);
                            tishi.setVisibility(View.GONE);
                        }
                    }
                    tixian.setText(object.getString("brokerage_type"));
                    String msg_type = object.getString("msg");
                    if (msg_type.equals("") || msg_type == null){
                        remark1.setText("无");
                    }else {
                        remark2.setVisibility(View.VISIBLE);
                        remark2.setText(msg_type);
                    }
                    JSONArray cert_imgs = object.getJSONArray("cert_imgs");
                    for (int i = 0; i < cert_imgs.length(); i++){
                        imglists.add((String) cert_imgs.get(i));
                    }
                    if (imglists.size() == 0)
                        pingz.setText("无");
                    imgadapter.setData(imglists);
                }else {
                    BaseToast.YToastS(DetailCommissionAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(DetailCommissionAct.this,errormsg);
            }
        });
    }
}
