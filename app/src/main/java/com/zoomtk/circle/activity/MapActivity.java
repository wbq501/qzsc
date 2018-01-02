package com.zoomtk.circle.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/14.
 */

public class MapActivity extends BaseActivity implements SensorEventListener {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_addr)
    TextView tv_addr;
    @BindView(R.id.tv_watchAddr)
    TextView tv_watchAddr;
    @BindView(R.id.baiduMap)
    MapView mMapView;

    boolean isFirstLoc = true; // 是否首次定位
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private Double lastX = 0.0;
    private SensorManager mSensorManager;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    // 定位相关
    LocationClient mLocClient;
    BaiduMap mBaiduMap;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationData locData;
    private CoordType mCoordType;


    private String storeName;
    private String areaInfo;
    private String userLongitude, userLatitude;


    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void init() {
        userLongitude = getIntent().getStringExtra("longitude");
        userLatitude = getIntent().getStringExtra("latitude");
        storeName = getIntent().getStringExtra("storeName");
        areaInfo = getIntent().getStringExtra("areaInfo");
        tv_name.setText(storeName);
        tv_addr.setText(areaInfo);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mCoordType = SDKInitializer.getCoordType();//获取全局设置的坐标类型
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.tv_watchAddr})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_watchAddr:
                LatLng pt1 = new LatLng(Double.valueOf(mCurrentLat), Double.valueOf(mCurrentLon));
                LatLng pt2 = new LatLng(Double.valueOf(userLatitude), Double.valueOf(userLongitude));
                // 构建 导航参数
                NaviParaOption para = new NaviParaOption()
                        .startPoint(pt1).endPoint(pt2);

                try {
                    BaiduMapNavigation.openBaiduMapNavi(para, this);
                } catch (BaiduMapAppNotSupportNaviException e) {
                    e.printStackTrace();
                    showDialog();
                }
                break;
        }
    }
    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(MapActivity.this);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(MapActivity.this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(MapActivity.this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        SDKInitializer.setCoordType(mCoordType);//设置为之前的坐标类型，保证此activity不对其他有影响。
    }
}
