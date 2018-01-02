package com.zoomtk.circle.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.CheckPartnerListAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseConfig;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.DistriButorModelBean;
import com.zoomtk.circle.dialog.HihtDialog;
import com.zoomtk.circle.dialog.TeamAddDialog;
import com.zoomtk.circle.dialog.TeamStoreDialog;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.LoadingLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/24.
 */

public class CheckPartnerAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.team_add)
    ImageView team_add;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.loadingview)
    LoadingLayout loadingview;

    private int curr_page = 1;
    private int total_page = 1;
    private String searchemsg = "";
    List<DistriButorModelBean.ContentBean> lists;
    CheckPartnerListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ckeckpartner;
    }

    @Override
    public void init() {
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
                    BaseToast.YToastS(CheckPartnerAct.this,"没有更多");
                }
            }
        });


        lists = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckPartnerAct.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        adapter = new CheckPartnerListAdapter(R.layout.listitem_checkpartnernew,lists);
        recycler.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DistriButorModelBean.ContentBean contentBean = lists.get(position);
                Intent intent = null;
                switch (view.getId()){
                    case R.id.callphone:
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contentBean.getMember_mobile()));
                        startactivity(intent);
                        break;
                    case R.id.prompting5:
                        HihtDialog dialog = new HihtDialog(CheckPartnerAct.this,"partner",token);//合伙人
                        dialog.show();
                        break;
                    case R.id.my_order:
                        intent = new Intent(CheckPartnerAct.this, CheckParterDetails.class);
                        intent.putExtra("sid", contentBean.getSid());
                        intent.putExtra("Member_truename",contentBean.getMember_truename());
                        startactivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.back,R.id.btn_search,R.id.team_add})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_search:
                searchemsg = et_search.getText().toString().trim();
                smart_refresh.autoRefresh();
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(view.getWindowToken(),0);
                break;
            case R.id.team_add:
                dialog1 = new TeamAddDialog(CheckPartnerAct.this,onClickListener);
                dialog1.show();
                break;
        }
    }

    TeamAddDialog dialog1;
    TeamStoreDialog dialog2;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sure:
                    if (dialog1.isNull())
                        return;
                    if (dialog1 != null){
                        dialog1.dismiss();
                        dialog2 = new TeamStoreDialog(CheckPartnerAct.this,dialog1.getEdnum(),token);
                        dialog2.show();
                    }
                    break;
                case R.id.cancle:
                    if (dialog1.isNull())
                        return;
                    if (dialog1 != null){
                        dialog1.dismiss();
                    }
                    break;
            }
        }
    };

    private void getData(int currpage){
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("curr_page", currpage + "");
        parms.put("info", searchemsg);
        parms.put("op",  "directly");
        HttpUtils.getDistributorsList(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadmore();
                smart_refresh.setVisibility(View.VISIBLE);
                loadingview.setVisibility(View.GONE);
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    DistriButorModelBean distriButorModelBean = gson.fromJson(result, DistriButorModelBean.class);
                    List<DistriButorModelBean.ContentBean> list = distriButorModelBean.getContent();
                    total_page = distriButorModelBean.getTotal_page();
                    lists.addAll(list);
                    adapter.setNewData(lists);
                    adapter.notifyDataSetChanged();
                }else {
                    BaseToast.YToastS(CheckPartnerAct.this,msg.getResultInfo());
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
                BaseToast.YToastS(CheckPartnerAct.this,errormsg);
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
