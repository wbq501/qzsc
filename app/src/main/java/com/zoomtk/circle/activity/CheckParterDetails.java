package com.zoomtk.circle.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.CheckParterDetailsAdapter;
import com.zoomtk.circle.adapter.CheckPartnerListAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CheckPartnerBean;
import com.zoomtk.circle.bean.DistriButorModelBean;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/25.
 */

public class CheckParterDetails extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    private String sid,Member_truename;

    private int curr_page = 1;
    private int total_page = 1;

    List<CheckPartnerBean.OrderListBean> lists;
    CheckParterDetailsAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.checkparterdetails;
    }

    @Override
    public void init() {
        sid = getIntent().getStringExtra("sid");
        Member_truename = getIntent().getStringExtra("Member_truename");
        title.setText(Member_truename+"的订单");

        smart_refresh.setVisibility(View.VISIBLE);
        loadingview.setVisibility(View.GONE);
        smart_refresh.autoRefresh();
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                lists.clear();
                curr_page = 1;
                getData(curr_page);
            }
        });
        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (curr_page < total_page){
                    curr_page++;
                    getData(curr_page);
                }else {
                    smart_refresh.finishLoadmore();
                    BaseToast.YToastS(CheckParterDetails.this,"没有更多");
                }
            }
        });

        lists = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckParterDetails.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        adapter = new CheckParterDetailsAdapter(R.layout.checkparter_item,lists);
        recycler.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void getData(int currpagefir){
        Map<String,String> prams = new HashMap<>();
        prams.put("token",token);
        prams.put("sid",sid);
        prams.put("curr_page",currpagefir+"");
        HttpUtils.getTeamMemberOrders(prams, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                smart_refresh.setVisibility(View.VISIBLE);
                loadingview.setVisibility(View.GONE);
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    CheckPartnerBean checkPartnerBean = gson.fromJson(result, CheckPartnerBean.class);
                    List<CheckPartnerBean.OrderListBean> order_list = checkPartnerBean.getOrder_list();
                    total_page = checkPartnerBean.getTotal_page();
                    lists.addAll(order_list);
                    adapter.setNewData(lists);
                    adapter.notifyDataSetChanged();
                    if (lists.size() <= 0){
                        smart_refresh.setVisibility(View.GONE);
                        loadingview.setVisibility(View.VISIBLE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                        loadingview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData(curr_page);
                            }
                        });
                    }
                }else {
                    BaseToast.YToastS(CheckParterDetails.this,msg.getResultInfo());
                    if (lists.size() == 0){
                        smart_refresh.setVisibility(View.GONE);
                        loadingview.setVisibility(View.VISIBLE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                        loadingview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData(curr_page);
                            }
                        });
                    }
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                BaseToast.YToastS(CheckParterDetails.this,errormsg);
                smart_refresh.setVisibility(View.GONE);
                loadingview.setVisibility(View.VISIBLE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                loadingview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(curr_page);
                    }
                });
            }
        });
    }
}
