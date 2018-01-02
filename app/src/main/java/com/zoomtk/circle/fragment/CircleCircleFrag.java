package com.zoomtk.circle.fragment;

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
import com.zoomtk.circle.activity.TrendsOfCircleAct;
import com.zoomtk.circle.adapter.TrendsListAdapter;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.TalkAboutListModel;
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
 * 圈子
 */

public class CircleCircleFrag extends BaseFragment{

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    private int curr_page = 1;//分页
    private int pagecount = 1;//分页总数

    private List<TalkAboutListModel.TracelogListEntity> lists;
    TrendsListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_circle;
    }

    @Override
    protected void init() {
        lists = new ArrayList<>();
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                lists.clear();
                curr_page = 1;
                loadshowlist(curr_page);
            }
        });

        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (curr_page == pagecount){
                    smart_refresh.finishLoadmore();
                    BaseToast.YToastS(getActivity(),"咦！已经到底啦");
                }else {
                    curr_page++;
                    loadshowlist(curr_page);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        adapter = new TrendsListAdapter(R.layout.listitem_trends,lists);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    protected void initdata() {
        smart_refresh.autoRefresh();
    }

    @OnClick({R.id.push})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.push://发表
                break;
        }
    }

    private void loadshowlist(int page) {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("curr_page",page+"");
        HttpUtils.talkAboutList(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                smart_refresh.setVisibility(View.VISIBLE);
                loadingview.setVisibility(View.GONE);
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    TalkAboutListModel talkAboutListModel = gson.fromJson(result, TalkAboutListModel.class);
                    pagecount = talkAboutListModel.getPage_count();
                    List<TalkAboutListModel.TracelogListEntity> tracelog_list = talkAboutListModel.getTracelog_list();
                    lists.addAll(tracelog_list);
                    adapter.setNewData(lists);
                    adapter.notifyDataSetChanged();
                }else {
                    BaseToast.YToastS(getActivity(),msg.getResultInfo());
                }
                if (lists.size() == 0){
                    smart_refresh.setVisibility(View.VISIBLE);
                    loadingview.setVisibility(View.GONE);
                    loadingview.setShowType(BaseConfig.LOADING_NO);
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                smart_refresh.setVisibility(View.GONE);
                loadingview.setVisibility(View.VISIBLE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                BaseToast.YToastS(getActivity(),errormsg);
            }
        });
    }
}
