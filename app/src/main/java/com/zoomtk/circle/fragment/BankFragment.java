package com.zoomtk.circle.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.yoojia.inputs.verifiers.BankCardVerifier;
import com.github.yoojia.inputs.verifiers.IDCardVerifier;
import com.github.yoojia.inputs.verifiers.MobileVerifier;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.CityBack;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.LoginActivity;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.AddNewAddrActivity;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.BankmsgBean;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.PickerCityUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class BankFragment extends BaseFragment{

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone_num)
    EditText phone_num;
    @BindView(R.id.id_card)
    EditText id_card;
    @BindView(R.id.bank_name)
    EditText bank_name;
    @BindView(R.id.bank_num)
    EditText bank_num;
    @BindView(R.id.bank_city)
    TextView bank_city;
    @BindView(R.id.bank_adress)
    EditText bank_adress;
    @BindView(R.id.btn_sure)
    Button btn_sure;

    private String provicename;
    private String cityname;

    private String city;

    private String proId;
    private String cityId;

    private String bank_province_id;
    private String bank_city_id;

    @Override
    protected int getLayoutId() {
        return R.layout.bankfragment;
    }

    @Override
    protected void init() {
        bank_num.addTextChangedListener(watcher);
        id_card.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().trim().length();
                String idcard = s.toString().trim();
                if (length == 17){
                    if (idcard.contains("X")){
                        id_card.setText(idcard.replace("X",""));
                        id_card.setSelection(id_card.getText().toString().length());//焦点到输入框最后位置
                    }else {
                        int sum = 0;
                        int[] num = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
                        for (int i =0; i < length; i++){
                            sum += (Integer.parseInt(idcard.substring(i, i + 1))) * (num[i]);
                        }
                        if (sum % 11 == 2){
                            id_card.setText(idcard+"X");
                            id_card.setSelection(id_card.getText().toString().length()-1);//焦点到输入框最后位置
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.bank_city,R.id.btn_sure})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.bank_city:
                PickerCityUtils.ChooseCity2(getActivity(), new CityBack() {
                    @Override
                    public void choose(String provinceName, String cityName, String DistrictName, String provinceId, String cityid, String districtId) {
                        provicename = provinceName;
                        cityname = cityName;

                        proId = provinceId;
                        cityId = cityid;

                        city = provinceName+cityName+DistrictName;
                        bank_city.setText(city);
                    }

                    @Override
                    public void error(String msg) {
                        BaseToast.YToastS(getActivity(),msg);
                    }
                });
                break;
            case R.id.btn_sure:
                submsg();
                break;
        }
    }

    private void submsg() {
        String names = name.getText().toString().trim();
        String phonenum = phone_num.getText().toString().trim();
        String idcard = id_card.getText().toString().trim();
        String bankname = bank_name.getText().toString().trim();
        String banknum = bank_num.getText().toString().trim();
        String bankcity = this.bank_city.getText().toString().trim();
        String bankadress = bank_adress.getText().toString().trim();

        if (names.equals("") || names == null){
            BaseToast.YToastS(getActivity(),"请输入真实姓名");
            return;
        }

        MobileVerifier mobileVerifier = new MobileVerifier();
        try {
            boolean isPhone = mobileVerifier.performTestNotEmpty(phonenum);
            if (!isPhone){
                BaseToast.YToastS(getActivity(),"手机号码不正确");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            BaseToast.YToastS(getActivity(),"验证手机号出错");
            return;
        }

        IDCardVerifier idCardVerifier = new IDCardVerifier();
        boolean isIdcard = idCardVerifier.performTestNotEmpty(idcard);
        if (!isIdcard){
            BaseToast.YToastS(getActivity(),"请输入正确的身份证号码");
            return;
        }

        if (TextUtils.isEmpty(bankname)){
            BaseToast.YToastS(getActivity(),"请输入银行名称");
            return;
        }
        BankCardVerifier bankCardVerifier = new BankCardVerifier();
        boolean isBank = bankCardVerifier.performTestNotEmpty(banknum);

        if (!isBank){
            BaseToast.YToastS(getActivity(),"银行卡格式不正确");
            return;
        }

        if (TextUtils.isEmpty(bankcity)){
            BaseToast.YToastS(getActivity(),"请选择归属地");
            return;
        }

        if (bankadress.equals("") || bankadress == null){
            BaseToast.YToastS(getActivity(),"请输入开户网点信息");
            return;
        }

        Map<String,String> parms = new Hashtable<>();
        parms.put("token",token);
        parms.put("cardholder_name",names);
        parms.put("bank_phone",phonenum);
        parms.put("id_card_num",idcard);
        parms.put("bank_name",bankname);
        parms.put("bank_card",banknum.replace(" ",""));
        parms.put("bank_province_id",bank_province_id);
        parms.put("bank_city_id",bank_city_id);
        parms.put("bank_attribution_locale",bankcity);
        parms.put("bank_branch_info",bankadress);

        HttpUtils.saveBankAccount(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    JSONObject jsonObject = new JSONObject(result);
                    BaseToast.YToastS(getActivity(),jsonObject.getString("result")+"");
                }else {
                    BaseToast.YToastS(getActivity(),msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(getActivity(),errormsg);
            }
        });
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString().trim().replace(" ", "");
            String result = "";
            if (str.length() >= 4) {
                bank_num.removeTextChangedListener(watcher);
                for (int i = 0; i < str.length(); i++) {
                    result += str.charAt(i);
                    if ((i + 1) % 4 == 0) {
                        result += " ";
                    }
                }
                if (result.endsWith(" ")) {
                    result = result.substring(0, result.length() - 1);
                }
                bank_num.setText(result);
                bank_num.addTextChangedListener(watcher);
                bank_num.setSelection(bank_num.getText().toString().length());//焦点到输入框最后位置
            }
        }
    };

    @Override
    protected void initdata() {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getBankAccountInfo(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    BankmsgBean bankmsgBean = gson.fromJson(gson.toJson(msg.getResult()), BankmsgBean.class);
                    name.setFocusable(false);
                    name.setEnabled(false);
                    name.setText(bankmsgBean.getCardholder_name());
                    phone_num.setText(bankmsgBean.getBank_phone());
                    id_card.setText(bankmsgBean.getId_card_num());
                    bank_province_id = bankmsgBean.getBank_province_id();
                    bank_city_id = bankmsgBean.getBank_city_id();
                    bank_name.setText(bankmsgBean.getBank_name());
                    bank_num.setText(bankmsgBean.getBank_name());
                    bank_city.setText(bankmsgBean.getProvince_name()+bankmsgBean.getCity_name());
                    bank_adress.setText(bankmsgBean.getBank_branch_info());
                }else {
                    BaseToast.YToastS(getActivity(),msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(getActivity(),errormsg);
            }
        });
    }
}
