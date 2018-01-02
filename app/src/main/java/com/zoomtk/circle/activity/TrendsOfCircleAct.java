package com.zoomtk.circle.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import com.zoomtk.circle.adapter.TrendsListAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseLog;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.CircleInfoModel;
import com.zoomtk.circle.bean.TalkAboutListModel;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.utils.ImgLoading;
import com.zoomtk.circle.view.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 圈子动态
 * Created by admin on 2017/12/25.
 */

public class TrendsOfCircleAct extends BaseActivity{

    @BindView(R.id.circle_avatar)
    ImageView circle_avatar;
    @BindView(R.id.ciclename)
    TextView ciclename;
    @BindView(R.id.attentionandjoin)
    Button attentionandjoin;
    @BindView(R.id.tv_context)
    TextView tv_context;
    @BindView(R.id.ciclejieshao)
    TextView ciclejieshao;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;
    @BindView(R.id.chat)
    TextView chat;
    @BindView(R.id.change)
    ImageView change;

    private String circle_id;
    private int is_identity;
    private String is_open;
    private String circle_name;
    private String circle_desc;
    private String group_id;
    private String group_name;

    private int curr_page = 1;//分页
    private int pagecount = 1;//分页总数

    private List<TalkAboutListModel.TracelogListEntity> lists;
    TrendsListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trendsofcircle;
    }

    @Override
    public void init() {
        circle_id = getIntent().getStringExtra("circle_id");
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
                    BaseToast.YToastS(TrendsOfCircleAct.this,"咦！已经到底啦");
                }else {
                    curr_page++;
                    loadshowlist(curr_page);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
    public void initdata() {
        dialog.show();
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("circle_id",circle_id);
        HttpUtils.circleDetail(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                dialog.dismiss();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    CircleInfoModel circleInfoModel = gson.fromJson(result, CircleInfoModel.class);
                    is_identity = circleInfoModel.getIs_identity();
                    if (is_identity == 0){
                        change.setVisibility(View.GONE);
                    }
                    if (circleInfoModel.getCircle().getIs_open().equals("0")){
                        tv_context.setText("人数："+circleInfoModel.getCircle().getCircle_mcount()+"      (私密)");
                    }else {
                        tv_context.setText("人数："+circleInfoModel.getCircle().getCircle_mcount()+"      (公开)");
                    }
                    ImgLoading.LoadCircle(TrendsOfCircleAct.this,circleInfoModel.getCircle().getCircle_img(),circle_avatar);
                    attentionandjoin.setVisibility(circleInfoModel.getIs_circle_member() == 1 ? View.GONE : View.VISIBLE);
                    circle_name = circleInfoModel.getCircle().getCircle_name();
                    circle_desc = circleInfoModel.getCircle().getCircle_desc();
                    group_id = circleInfoModel.getGroup().getGroup_id();
                    group_name = circleInfoModel.getGroup().getGroup_name();
                    ciclejieshao.setText(circle_desc);
                    ciclename.setText(circle_name);
                    chat.setText(circleInfoModel.getCircle().getCm_chat_online_count()+"");
                }else {
                    BaseToast.YToastS(TrendsOfCircleAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.YToastS(TrendsOfCircleAct.this,errormsg);
            }
        });

        loadshowlist(curr_page);
    }

    private void loadshowlist(int page) {
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("circle_id",circle_id);
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
                    BaseToast.YToastS(TrendsOfCircleAct.this,msg.getResultInfo());
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
                BaseToast.YToastS(TrendsOfCircleAct.this,errormsg);
            }
        });
    }

    @OnClick({R.id.back,R.id.change,R.id.chatongroup})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.change:

                break;
            case R.id.chatongroup:
                break;
        }
    }
}
