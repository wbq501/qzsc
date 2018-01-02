package com.zoomtk.circle.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.Interface.ResponseBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.TiXianModle;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.KeybordUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class WeiXinFragment extends BaseFragment{

    @BindView(R.id.info)
    TextView mInfo;
    @BindView(R.id.adduser_nameet)
    EditText mAdduserNameet;
    @BindView(R.id.openid)
    TextView mOpenid;
    @BindView(R.id.hint)
    TextView mHint;
    @BindView(R.id.commit)
    Button mCommit;
    @BindView(R.id.remind)
    TextView mRemind;
    @BindView(R.id.link)
    WebView mLink;
    @BindView(R.id.im)
    ImageView mIm;
    @BindView(R.id.linear)
    LinearLayout mLinear;


    private String oldname;
    private boolean isSave = true;

    @Override
    protected int getLayoutId() {
        return R.layout.weixinfragment;
    }

    @Override
    protected void init() {
        KeybordUtils.hideSoftInput(getActivity(),mAdduserNameet);
        mAdduserNameet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable s) {
                int length = mAdduserNameet.getText().toString().trim().length();
                if (length>0){
                    isSave = true;
                    mCommit.setBackground(getResources().getDrawable(R.drawable.shape_round_red));
                }else {
                    isSave = false;
                    mCommit.setBackground(getResources().getDrawable(R.drawable.store_edit_save_btn2));
                }
            }
        });
    }

    @OnClick({R.id.commit})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.commit:
                if (isSave){
                    changeMemberTrueName();
                }
                break;
        }
    }

    private void changeMemberTrueName() {
        if (mAdduserNameet != null && !mAdduserNameet.getText().toString().equals(oldname)) {
            Map<String,String> parms = new HashMap<>();
            parms.put("token",token);
            parms.put("op", 4 + "");
            parms.put("content", mAdduserNameet.getText().toString());
            HttpUtils.userstoreManage(parms, new ResponseBack() {
                @Override
                public void success(Object o) throws Exception {
                    BaseToast.YToastS(getActivity(), o.toString());
                }

                @Override
                public void error(String errormsg) {
                    BaseToast.YToastS(getActivity(), errormsg);
                }
            });
        } else {
            BaseToast.YToastS(getActivity(), "新名字不能为空或与旧名字相同");
        }
    }

    @Override
    protected void initdata() {
        mRemind.setText("如果您提现失败，请按以下步骤完成微信提现账号绑定：\n" +
                "1、用您本人的微信关注“京软IMS”公众号；\n" +
                "2、进入“京软IMS”公众号，点击底部菜单“账号绑定”；\n" +
                "3、进入登陆页面，填写您的手机号和密码登陆后，即完成绑定；\n" +
                "4、重新登陆App，点击“立即提现”，将在三秒内打款到你微信钱包中；");
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.operateWithdrawalAccount(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    TiXianModle tiXianModle = gson.fromJson(gson.toJson(msg.getResult()), TiXianModle.class);
                    mCommit.setVisibility(View.VISIBLE);
                    mInfo.setVisibility(View.VISIBLE);
                    mRemind.setVisibility(View.VISIBLE);
                    mIm.setVisibility(View.VISIBLE);
                    mLinear.setVisibility(View.VISIBLE);
                    mInfo.setText(tiXianModle.getOpenid()+ "");
                    mAdduserNameet.setText(tiXianModle.getMember_truename() + "");
                    mAdduserNameet.setSelection(tiXianModle.getMember_truename().length());
                    mOpenid.setText(tiXianModle.getOpenid() + "");
                    mHint.setText(tiXianModle.getTishi1());
                    oldname = tiXianModle.getMember_truename();
                }else {
                    mCommit.setVisibility(View.GONE);
                    mInfo.setVisibility(View.GONE);
                    mRemind.setVisibility(View.GONE);
                    mIm.setVisibility(View.GONE);
                    mLinear.setVisibility(View.GONE);
                    BaseToast.YToastS(getActivity(), msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(getActivity(), errormsg);
            }
        });
    }
}
