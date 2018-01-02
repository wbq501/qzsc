package com.zoomtk.circle.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.CityBack;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.PickerCityUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/12.
 */

public class UpdateAddrActivity extends BaseActivity{

    @BindView(R.id.store_suozaidiweishezhi)
    TextView store_adress;
    @BindView(R.id.et_addr)
    EditText input_adress;
    @BindView(R.id.update_store_edit)
    TextView tv_save;


    private String provicename;
    private String cityname;
    private String city;
    private String districtname;
    private String proId;
    private String cityId;
    private String dirId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_addr;
    }

    @Override
    public void init() {

    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back,R.id.rl_selectcity,R.id.update_store_edit})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rl_selectcity:
                PickerCityUtils.ChooseCity(UpdateAddrActivity.this, new CityBack() {
                    @Override
                    public void choose(String provinceName, String cityName, String DistrictName, String provinceId, String cityId, String districtId) {
                        provicename = provinceName;
                        cityname = cityName;
                        districtname = DistrictName;
                        proId = provinceId;
                        UpdateAddrActivity.this.cityId = cityId;
                        dirId = districtId;
                        city = provinceName+cityName+DistrictName;
                        store_adress.setText(city);
                    }

                    @Override
                    public void error(String msg) {
                        BaseToast.YToastS(UpdateAddrActivity.this,msg);
                    }
                });
                break;
            case R.id.update_store_edit:
                Map<String,String> parms = new HashMap<>();
                parms.put("token",token);
                parms.put("op",6+"");
                parms.put("content",city+input_adress.getText().toString());
                HttpUtils.storeManage(parms, new RequestBack() {
                    @Override
                    public void success(BaseJson msg) throws Exception {
                        if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                            Intent intent = new Intent();
                            intent.putExtra("provicename", provicename);
                            intent.putExtra("cityname", cityname);
                            intent.putExtra("districtname", districtname);
                            intent.putExtra("detailedaddress", input_adress.getText().toString());
                            intent.putExtra("proid", proId);
                            intent.putExtra("cityid", cityId);
                            intent.putExtra("dirid", dirId);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else {
                            BaseToast.YToastL(UpdateAddrActivity.this,msg.getResultInfo());
                        }
                    }

                    @Override
                    public void error(String errormsg) {
                        BaseToast.YToastL(UpdateAddrActivity.this,errormsg);
                    }
                });
                break;
        }
    }
}
