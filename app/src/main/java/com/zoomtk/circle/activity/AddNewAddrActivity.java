package com.zoomtk.circle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.bigkoo.pickerview.OptionsPickerView;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.CityBack;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CityModel;
import com.zoomtk.circle.bean.DeliveryAddressModel;
import com.zoomtk.circle.bean.DistrictModel;
import com.zoomtk.circle.bean.ProvinceModel;
import com.zoomtk.circle.fragment.OneFragment;
import com.zoomtk.circle.utils.AdressChooseUtils;
import com.zoomtk.circle.utils.CommonUtil;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.PickerCityUtils;
import com.zoomtk.circle.utils.XmlParserHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/15.
 */

public class AddNewAddrActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;
    @BindView(R.id.tv_selectAddr)
    TextView tv_selectAddr;
    @BindView(R.id.tv_AddrDetails)
    TextView tv_AddrDetails;
    @BindView(R.id.et_AddrNum)
    EditText et_AddrNum;
    @BindView(R.id.tv_deleteAddr)
    TextView tv_deleteAddr;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_save)
    TextView tv_save;

    DeliveryAddressModel.AddresslistBean mAddresslistBean;

    double latitude;
    double longtitude;
    private String strAddr;
    private String provicename;
    private String cityname = "";
    private String city;
    private String districtname;
    private String proId;
    private String cityId;
    private String dirId;
    private String addrName;

    private boolean isFinish = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_new_addr;
    }

    @Override
    public void init() {
        mAddresslistBean = (DeliveryAddressModel.AddresslistBean) getIntent().getSerializableExtra("info");
        if (mAddresslistBean != null){
            tv_deleteAddr.setVisibility(View.VISIBLE);
            tv_title.setText("修改收货地址");
            et_name.setText(mAddresslistBean.getTrue_name());
            et_phoneNum.setText(mAddresslistBean.getMob_phone());
            tv_selectAddr.setText(mAddresslistBean.getArea_info());
            tv_AddrDetails.setText(mAddresslistBean.getAddress());
            et_AddrNum.setText(mAddresslistBean.getResidential());
            try {
                latitude = Double.valueOf(mAddresslistBean.getLat());
                longtitude = Double.valueOf(mAddresslistBean.getLng());
            } catch (Exception e) {
                e.printStackTrace();
            }
            provicename = mAddresslistBean.getProvince();
            cityname = mAddresslistBean.getCity();
            districtname = mAddresslistBean.getArea();
            proId = mAddresslistBean.getArea_id();
            cityId = mAddresslistBean.getCity_id();
            dirId = mAddresslistBean.getArea_id();

        }else {
            tv_deleteAddr.setVisibility(View.GONE);
            tv_title.setText("新增地址");
        }
    }

    @Override
    public void initdata() {
        et_name.getText().toString().trim();
    }

    @OnClick({R.id.iv_back,R.id.tv_save,R.id.tv_selectAddr,R.id.tv_deleteAddr,R.id.tv_AddrDetails})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                savemsg();
                break;
            case R.id.tv_selectAddr:
                PickerCityUtils.ChooseCity(AddNewAddrActivity.this, new CityBack() {
                    @Override
                    public void choose(String provinceName, String cityName, String DistrictName, String provinceId, String cityId, String districtId) {
                        provicename = provinceName;
                        cityname = cityName;
                        districtname = DistrictName;
                        proId = provinceId;
                        AddNewAddrActivity.this.cityId = cityId;
                        dirId = districtId;
                        city = provinceName+cityName+DistrictName;
                        tv_selectAddr.setText(city);
                    }

                    @Override
                    public void error(String msg) {
                        BaseToast.YToastS(AddNewAddrActivity.this,msg);
                    }
                });
                break;
            case R.id.tv_deleteAddr:
                deladdress();
                break;
            case R.id.tv_AddrDetails:
                if (cityname.length() > 0){
                    Intent intent = new Intent(AddNewAddrActivity.this, SelectMapAddrActivity.class);
                    intent.putExtra("city", cityname);
                    if (tv_AddrDetails.getText().toString().length() > 0){
                        intent.putExtra("oldAddr", tv_AddrDetails.getText().toString());
                        intent.putExtra("addrName", addrName);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longtitude", longtitude);
                    }
                    startActivityForResult(intent, 300);
                }else {
                    BaseToast.YToastL(AddNewAddrActivity.this,"请选择所在城市");
                }
                break;
        }
    }


    private void deladdress() {
        Map<String,String> parms = new HashMap<>();
        parms.put("address_id",mAddresslistBean.getAddress_id());
        parms.put("token",token);
        HttpUtils.delMemberInfo(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    finish();
                }else {
                    BaseToast.YToastL(AddNewAddrActivity.this, msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastL(AddNewAddrActivity.this, Config.errormsg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && data != null){
            PoiInfo poiInfo = (PoiInfo) data.getParcelableExtra("poiInfo");

            tv_AddrDetails.setText(poiInfo.name);
            longtitude = poiInfo.location.longitude;
            latitude = poiInfo.location.latitude;
            addrName = poiInfo.name;
        }
    }

    private void savemsg() {
        String etname = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(etname)){
            BaseToast.YToastS(AddNewAddrActivity.this,"请输入联系人");
            return;
        }
        String etphoneNum = et_phoneNum.getText().toString().trim();
        if (!CommonUtil.checkPhone(AddNewAddrActivity.this,etphoneNum)){
            BaseToast.YToastS(AddNewAddrActivity.this,"请输入正确的手机号");
            return;
        }
        String tvselectAddr = tv_selectAddr.getText().toString();
        if (TextUtils.isEmpty(tvselectAddr)){
            BaseToast.YToastS(AddNewAddrActivity.this,"请选择所在城市");
            return;
        }
        String tvAddrDetails = tv_AddrDetails.getText().toString();
        if (TextUtils.isEmpty(tvAddrDetails)){
            BaseToast.YToastS(AddNewAddrActivity.this,"请选择收货地址");
            return;
        }
        String etAddrNum = et_AddrNum.getText().toString().trim();
        if (TextUtils.isEmpty(etAddrNum)){
            BaseToast.YToastS(AddNewAddrActivity.this,"请输入详细地址");
            return;
        }

        final Map<String, String> map = new HashMap<>();
        map.put("true_name", etname);
        map.put("mob_phone", etphoneNum);
        map.put("province", provicename);
        map.put("residential", etAddrNum);
        map.put("address", tvAddrDetails);
        map.put("city", cityname);
        map.put("area", districtname);
        map.put("lat", String.valueOf(latitude));
        map.put("lng", String.valueOf(longtitude));
        map.put("token",token);
        if (mAddresslistBean != null){
            map.put("address_id", mAddresslistBean.getAddress_id());
            HttpUtils.editMemberInfo(map, new RequestBack() {
                @Override
                public void success(BaseJson msg) throws Exception {
                    if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                        finish();
                    }else {
                        BaseToast.YToastL(AddNewAddrActivity.this,msg.getResultInfo());
                    }
                }

                @Override
                public void error(String errormsg) {
                    BaseToast.YToastL(AddNewAddrActivity.this, Config.errormsg);
                }
            });
        }else {
            HttpUtils.addMemberInfo(map, new RequestBack() {
                @Override
                public void success(BaseJson msg) throws Exception {
                    if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                        finish();
                    }else {
                        BaseToast.YToastL(AddNewAddrActivity.this,msg.getResultInfo());
                    }
                }

                @Override
                public void error(String errormsg) {
                    BaseToast.YToastL(AddNewAddrActivity.this, Config.errormsg);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();    //获取纬度信息
            longtitude = location.getLongitude();    //获取经度信息
            strAddr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String streetNumber = location.getStreetNumber();
        }
    }

    private void initLocation() {
        mLocationClient = new LocationClient(AddNewAddrActivity.this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        mLocationClient.start();
    }
}
