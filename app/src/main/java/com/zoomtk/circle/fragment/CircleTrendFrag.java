package com.zoomtk.circle.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.SerachActivity;
import com.zoomtk.circle.activity.TrendsOfCircleAct;
import com.zoomtk.circle.adapter.CircleListAdapter;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CircleItem;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/13.
 */

public class CircleTrendFrag extends BaseFragment{

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    private ArrayList<CircleItem.JoinCircleBean> lists;
    private int currpage = 1;
    private int pagecount = 1;

    CircleListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.ciccle_trend;
    }

    @Override
    protected void init() {
        loadingview.setVisibility(View.GONE);
        smart_refresh.setVisibility(View.VISIBLE);

        lists = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        adapter = new CircleListAdapter(R.layout.listitem_circle,lists);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycler.setAdapter(adapter);
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                currpage = 1;
                lists.clear();
                loadData(currpage);
            }
        });
        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currpage == pagecount){
                    smart_refresh.finishLoadmore();
                    BaseToast.YToastS(getActivity(),"咦！已经到底啦");
                }else {
                    currpage++;
                    loadData(currpage);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), TrendsOfCircleAct.class);
                intent.putExtra("circle_id",lists.get(position).getCircle_id());
                startactivity(intent);
            }
        });
    }

    private void loadData(int page){
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("curr_page",page+"");
        HttpUtils.getJoinCircle(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    CircleItem circleItem = gson.fromJson(result,CircleItem.class);
                    List<CircleItem.JoinCircleBean> list = circleItem.getJoin_circle();
                    lists.addAll(list);
                    adapter.setNewData(lists);
                    adapter.notifyDataSetChanged();
                }else {
                    BaseToast.YToastS(getActivity(),msg.getResultInfo());
                }
                if (lists.size() == 0){
                    loadingview.setVisibility(View.VISIBLE);
                    smart_refresh.setVisibility(View.GONE);
                    loadingview.setShowType(BaseConfig.LOADING_NO);
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                loadingview.setVisibility(View.VISIBLE);
                smart_refresh.setVisibility(View.GONE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                BaseToast.YToastS(getActivity(),errormsg);
            }
        });
    }

    @Override
    protected void initdata() {
        loadData(currpage);
    }

    @OnClick({R.id.rl_search})
    public void OnClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.rl_search:
                intent = new Intent(getActivity(),SerachActivity.class);
                startactivity(intent);
                break;
        }
    }
}
