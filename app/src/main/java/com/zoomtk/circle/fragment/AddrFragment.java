package com.zoomtk.circle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.FragmentListenr;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.AddNewAddrActivity;
import com.zoomtk.circle.adapter.AddrAdapter;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.DeliveryAddressModel;
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

public class AddrFragment extends BaseFragment{

    @BindView(R.id.tv_addNewAddr)
    TextView tv_addNewAddr;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.rl_location)
    RelativeLayout rl_location;

    FragmentListenr listenr = null;
    NearbyStoreBean nearbyStoreBean = null;

    private int currentPage = 1;
    private int totalpagefir = 1;
    private List<DeliveryAddressModel.AddresslistBean> lists;
    AddrAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.addr_fragment;
    }

    @Override
    protected void init() {
        smart_refresh.setVisibility(View.VISIBLE);
        loadingview.setVisibility(View.GONE);
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currentPage = 1;
                lists.clear();
                getData(currentPage);
            }
        });
        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage == totalpagefir){
                    smart_refresh.finishRefresh();
                    BaseToast.YToastS(getActivity(),"咦！已经到底啦");
                }else {
                    currentPage++;
                    getData(currentPage);
                }
            }
        });
        nearbyStoreBean = (NearbyStoreBean) getActivity().getIntent().getSerializableExtra("nearbyStoreBean");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        lists = new ArrayList<>();
        adapter = new AddrAdapter(R.layout.addr_item,lists);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_edit){
                    Intent intent = new Intent(getActivity(), AddNewAddrActivity.class);
                    intent.putExtra("info", lists.get(position));
                    getActivity().startActivity(intent);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                chooseAdress(lists.get(position).getAddress(),lists.get(position).getArea_info(),lists.get(position).getLat(),lists.get(position).getLng());
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


    @Override
    protected void initdata() {

    }

    @Override
    public void onResume() {
        super.onResume();
        smart_refresh.autoRefresh();
    }

    private void getData(int i){
        Map<String, String> prams = new HashMap<>();
        prams.put("curr_page", String.valueOf(i));
        prams.put("page_num", String.valueOf(10));
        prams.put("token",token);
        HttpUtils.getAddressList(prams, new RequestBack() {
            @Override
            public void success(BaseJson msg) {
                smart_refresh.finishRefresh();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    DeliveryAddressModel deliveryAddressModel = gson.fromJson(result, DeliveryAddressModel.class);
                    List<DeliveryAddressModel.AddresslistBean> list_adress = deliveryAddressModel.getAddresslist();
                    if (list_adress.size() == 0 && currentPage == 1){
                        smart_refresh.setVisibility(View.GONE);
                        loadingview.setVisibility(View.VISIBLE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                        loadingview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                currentPage = 1;
                                lists.clear();
                                getData(currentPage);
                            }
                        });
                    }else {
                        totalpagefir = deliveryAddressModel.getPage_count();
                        lists.addAll(list_adress);
                        adapter.setNewData(lists);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    BaseToast.ToastL(getActivity(), msg.getResultInfo()+"");
                    smart_refresh.setVisibility(View.GONE);
                    loadingview.setVisibility(View.VISIBLE);
                    loadingview.setShowType(BaseConfig.LOADING_ERROR);
                    loadingview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentPage = 1;
                            lists.clear();
                            getData(currentPage);
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
                        currentPage = 1;
                        lists.clear();
                        getData(currentPage);
                    }
                });
            }
        });
    }

    @OnClick({R.id.rl_location,R.id.tv_addNewAddr})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_location:
                listenr.adressmsg(nearbyStoreBean,null);
                break;
            case R.id.tv_addNewAddr:
                Intent intent = new Intent(getActivity(), AddNewAddrActivity.class);
                getActivity().startActivity(intent);
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
