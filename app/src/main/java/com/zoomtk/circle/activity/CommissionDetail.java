package com.zoomtk.circle.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.CommissionDetailAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CommissionBean;
import com.zoomtk.circle.bean.CommissionDetailBean;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class CommissionDetail extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;


    ArrayList<CommissionDetailBean.BwListBean> lists;
    private int currentPage = 1;
    private int totalpagefir = 1;
    CommissionDetailAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.commission_detail;
    }

    @Override
    public void init() {
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
                    smart_refresh.finishLoadmore();
                    BaseToast.YToastS(CommissionDetail.this,"咦！已经到底啦");
                }else {
                    currentPage++;
                    getData(currentPage);
                }
            }
        });

        smart_refresh.autoRefresh();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CommissionDetail.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        lists=new ArrayList<>();
        adapter = new CommissionDetailAdapter(R.layout.commission_detail_item,lists);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CommissionDetail.this,DetailCommissionAct.class);
                intent.putExtra("id",lists.get(position).getId());
                startActivityForResult(intent,100);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                lists.clear();
                currentPage = 1;
                getData(currentPage);
                smart_refresh.autoRefresh();
                break;
        }
    }

    private void getData(final int currentPage) {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("curr_page",currentPage+"");
        HttpUtils.getList(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                smart_refresh.setVisibility(View.VISIBLE);
                loadingview.setVisibility(View.GONE);
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    CommissionDetailBean commissionDetailBean = gson.fromJson(result, CommissionDetailBean.class);
                    totalpagefir = commissionDetailBean.getPage_total();
                    lists.addAll(commissionDetailBean.getBw_list());
                    adapter.setNewData(lists);
                    adapter.notifyDataSetChanged();
                }else {
                    BaseToast.ToastL(CommissionDetail.this, msg.getResultInfo());
                    if (lists.size() <= 0){
                        smart_refresh.setVisibility(View.GONE);
                        loadingview.setVisibility(View.VISIBLE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                        loadingview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData(currentPage);
                            }
                        });
                    }
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                BaseToast.ToastL(CommissionDetail.this, Config.errormsg);
                smart_refresh.setVisibility(View.GONE);
                loadingview.setVisibility(View.VISIBLE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                loadingview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(currentPage);
                    }
                });
            }
        });
    }

    @OnClick({R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void initdata() {

    }
}
