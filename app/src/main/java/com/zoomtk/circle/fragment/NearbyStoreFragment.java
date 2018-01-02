package com.zoomtk.circle.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.FragmentListenr;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.MapActivity;
import com.zoomtk.circle.adapter.NearStoreAdapter;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.AdressBean;
import com.zoomtk.circle.bean.GetStoreURLModel;
import com.zoomtk.circle.bean.NearbyStoreBean;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/11.
 */

public class NearbyStoreFragment extends BaseFragment {
    private static String TAG = "NearbyStoreFragment";

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.rl_location)
    RelativeLayout rl_location;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    FragmentListenr listenr = null;
    NearbyStoreBean nearbyStoreBean = null;
    NearStoreAdapter adapter;
    List<AdressBean> lists = null;

    @Override
    protected int getLayoutId() {
        return R.layout.nearbystore_fragment;
    }

    @Override
    protected void init() {
        smart_refresh.setVisibility(View.VISIBLE);
        loadingview.setVisibility(View.GONE);
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                lists.clear();
                getData(nearbyStoreBean);
            }
        });
        smart_refresh.autoRefresh();
        smart_refresh.setEnableLoadmore(false);
        nearbyStoreBean = (NearbyStoreBean) getActivity().getIntent().getSerializableExtra("nearbyStoreBean");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        lists = new ArrayList<>();
        adapter = new NearStoreAdapter(R.layout.nearbyaddr_item,lists);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_map){
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    intent.putExtra("latitude", lists.get(position).getLat());
                    intent.putExtra("longitude", lists.get(position).getLng());
                    intent.putExtra("storeName", lists.get(position).getW_storename());
                    intent.putExtra("areaInfo", lists.get(position).getArea_info());
                    getActivity().startActivity(intent);
                }else if (view.getId() == R.id.tv_num){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+lists.get(position).getMobile()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                chooseAdress(lists.get(position).getAddress(), lists.get(position).getArea_info(),lists.get(position).getLat(),lists.get(position).getLng());
            }
        });
        recycler.setAdapter(adapter);
    }

    //回到首页
    private void chooseAdress(final String adress, final String area_info, final String lat, final String lng) {
        dialog.show();
        Map<String,String> prams = new HashMap<>();
        prams.put("token",token);
        prams.put("lat",lat);
        prams.put("lon",lng);
        HttpUtils.getOfflineOnline(prams, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                dialog.dismiss();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    GetStoreURLModel getStoreURLModel = gson.fromJson(gson.toJson(msg.getResult()), GetStoreURLModel.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("authCode",getStoreURLModel.getAuthCode().getAuth_code());
                    bundle.putString("url",getStoreURLModel.getUrl());
                    bundle.putString("strAddr",adress);
                    bundle.putString("memberId",getStoreURLModel.getAuthCode().getMember_id());
                    nearbyStoreBean.setLng(lng);
                    nearbyStoreBean.setLat(lat);
                    nearbyStoreBean.setArea_info(area_info);
                    listenr.adressmsg(nearbyStoreBean,bundle);
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo()+"");
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.ToastL(getActivity(), Config.errormsg);
            }
        });
    }

    private void getData(final NearbyStoreBean bean) {
        Map<String,String> parms = new HashMap<>();
        parms.put("area_info","");
        parms.put("city",bean.getCity());
        parms.put("lat",bean.getLat());
        parms.put("lng",bean.getLng());
        parms.put("token",token);
        HttpUtils.getStoresAddressList(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                smart_refresh.finishRefresh();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    List<AdressBean> lists_adress = gson.fromJson(result,new TypeToken<List<AdressBean>>(){}.getType());
                    if (lists_adress.size() > 0){
                        lists.addAll(lists_adress);
                        adapter.setNewData(lists);
                        adapter.notifyDataSetChanged();
                    }else {
                        smart_refresh.setVisibility(View.GONE);
                        loadingview.setVisibility(View.VISIBLE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                        loadingview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData(bean);
                            }
                        });
                    }

                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo()+"");
                    smart_refresh.setVisibility(View.GONE);
                    loadingview.setVisibility(View.VISIBLE);
                    loadingview.setShowType(BaseConfig.LOADING_ERROR);
                    loadingview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData(bean);
                        }
                    });
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                BaseToast.ToastL(getActivity(), Config.errormsg);
                smart_refresh.setVisibility(View.GONE);
                loadingview.setVisibility(View.VISIBLE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                loadingview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(bean);
                    }
                });
            }
        });
    }

    @Override
    protected void initdata() {

    }

    @OnClick({R.id.rl_location})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_location:
                listenr.adressmsg(nearbyStoreBean,null);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (nearbyStoreBean != null)
            nearbyStoreBean = null;
    }

    @Override
    public void onAttach(Context context) {
        listenr = (FragmentListenr) context;
        super.onAttach(context);
    }
}
