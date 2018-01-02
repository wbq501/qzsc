package com.zoomtk.circle.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.SelectMapItemAdapter;
import com.zoomtk.circle.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mapapi.overlayutil.PoiOverlay;

/**
 * Created by wbq501 on 2017/11/17.
 */

public class SelectMapAddrActivity extends BaseActivity implements OnGetPoiSearchResultListener,OnGetGeoCoderResultListener{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.baiduMap)
    MapView mMapView;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private String oldAddr;
    private String addrName;
    private String city;
    private Double latitude;
    private Double longtitude;

    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private PoiSearch mPoiSearch = null;
    private BaiduMap mBaiduMap = null;

    private List<PoiInfo> lists;

    SelectMapItemAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_select_map_addr;
    }

    @Override
    public void init() {

        city = getIntent().getStringExtra("city");
        oldAddr = getIntent().getStringExtra("oldAddr");
        addrName = getIntent().getStringExtra("addrName");
        latitude = getIntent().getDoubleExtra("latitude",40.056885);
        longtitude = getIntent().getDoubleExtra("longtitude", 116.308142);

        if (TextUtils.isEmpty(oldAddr)){
            mMapView.setVisibility(View.GONE);
        }else {
            mMapView.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        lists = new ArrayList<>();
        adapter = new SelectMapItemAdapter(R.layout.selectmapaddr_item,lists);
        recycler.setAdapter(adapter);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMaxAndMinZoomLevel(7,11);
        initPoi();
        initGeo();
    }

    private void initGeo() {
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        LatLng ptCenter = new LatLng(latitude, longtitude);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(ptCenter));
    }

    private void initPoi() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    @Override
    public void initdata() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (lists != null)
                        lists.clear();
                    iv_clear.setVisibility(View.VISIBLE);
                    mPoiSearch.searchInCity((new PoiCitySearchOption())
                            .city(city).keyword(et_search.getText().toString()).pageNum(10));
                }else {
                    iv_clear.setVisibility(View.GONE);
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiInfo poiInfo = lists.get(position);
                Intent intent = new Intent();
                intent.putExtra("poiInfo",poiInfo);
                setResult(300,intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        mSearch.destroy();
        if(mMapView != null)
            mMapView.onDestroy();
    }

    @OnClick({R.id.iv_clear,R.id.iv_back,R.id.iv_search})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_clear:
                et_search.setText("");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                if (lists != null)
                    lists.clear();
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city(city).keyword(et_search.getText().toString()).pageNum(10));
                break;
        }
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(SelectMapAddrActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            List<PoiInfo> allPoi = result.getAllPoi();
            lists.addAll(allPoi);
            adapter.setNewData(lists);
            adapter.notifyDataSetChanged();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
        }
    }
    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(SelectMapAddrActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(SelectMapAddrActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(SelectMapAddrActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.icon_gcoding)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }
}
