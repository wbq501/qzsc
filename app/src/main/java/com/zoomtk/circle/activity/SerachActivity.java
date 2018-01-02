package com.zoomtk.circle.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.CircleListAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CircleItem;
import com.zoomtk.circle.bean.FindCicleModle;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 圈子搜索
 * Created by wbq501 on 2017/12/21.
 */

public class SerachActivity extends BaseActivity{

    @BindView(R.id.et_sousuo)
    EditText et_sousuo;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    private String searmsg;

    private ArrayList<CircleItem.JoinCircleBean> lists;
    private int currpage = 1;
    private int pagecount = 1;

    CircleListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_serach;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        loadingview.setVisibility(View.VISIBLE);
        smart_refresh.setVisibility(View.GONE);
        loadingview.setShowType(BaseConfig.LOADING_NO);

        lists = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(SerachActivity.this);
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
                    BaseToast.YToastS(SerachActivity.this,"咦！已经到底啦");
                }else {
                    currpage++;
                    loadData(currpage);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SerachActivity.this,TrendsOfCircleAct.class);
                intent.putExtra("circle_id",lists.get(position).getCircle_id());
                startactivity(intent);
            }
        });
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back,R.id.btn_sosuo})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_sosuo:
                searmsg = et_sousuo.getText().toString().trim();
                loadData(1);
                break;
        }
    }

    private void loadData(int page){
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("curr_page",page+"");
        parms.put("info",searmsg+"");
        HttpUtils.findCircle(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    FindCicleModle findCicleModle = gson.fromJson(result, FindCicleModle.class);
                    List<FindCicleModle.RecCirclBean> list = findCicleModle.getRec_circl();
                    for (int i = 0; i < list.size(); i++){
                        FindCicleModle.RecCirclBean recCirclBean = list.get(i);
                        CircleItem.JoinCircleBean bean = new CircleItem.JoinCircleBean();
                        bean.setCircle_id(recCirclBean.getCircle_id());
                        bean.setCircle_img(recCirclBean.getCircle_img());
                        bean.setCircle_name(recCirclBean.getCircle_name());
                        bean.setCircle_desc(recCirclBean.getCircle_desc());
                        bean.setCircle_mcount(Integer.valueOf(recCirclBean.getCircle_mcount()));
                        bean.setCircle_mastername(recCirclBean.getCircle_mastername());
                        bean.setIs_new(recCirclBean.getIs_new());
                        lists.add(bean);
                    }
                    adapter.setNewData(lists);
                    adapter.notifyDataSetChanged();
                    if (lists.size() == 0){
                        loadingview.setVisibility(View.GONE);
                        smart_refresh.setVisibility(View.VISIBLE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                    }else {
                        loadingview.setVisibility(View.GONE);
                        smart_refresh.setVisibility(View.VISIBLE);
                    }
                }else {
                    if (lists.size() == 0){
                        loadingview.setVisibility(View.VISIBLE);
                        smart_refresh.setVisibility(View.GONE);
                        loadingview.setShowType(BaseConfig.LOADING_NO);
                    }
                    BaseToast.YToastS(SerachActivity.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                loadingview.setVisibility(View.VISIBLE);
                smart_refresh.setVisibility(View.GONE);
                loadingview.setShowType(BaseConfig.LOADING_ERROR);
                BaseToast.YToastS(SerachActivity.this,errormsg);
            }
        });
    }
}
