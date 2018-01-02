package com.zoomtk.circle.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.LetAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.ChatUserInfoBean;
import com.zoomtk.circle.bean.Content;
import com.zoomtk.circle.utils.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 * Created by wbq501 on 2017/12/19.
 */

public class SearchFriendAct extends BaseActivity implements AbsListView.OnScrollListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_search_friend)
    EditText editText;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.listview_search_friend)
    ListView listView;

    private String searchKey;
    private LetAdapter letAdapter;
    int currpage = 1;// 页码
    int pagecount = 1;//总页数
    List<Content> mContentList;//存放全局的搜索结果、。

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_friends;
    }

    @Override
    public void init() {
        mContentList = new ArrayList<>();
        listView.setAdapter(letAdapter);
        letAdapter = new LetAdapter(this, new ArrayList<Content>());
        listView.setAdapter(letAdapter);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        searchKey = intent.getStringExtra("search_key");
        editText.setText(searchKey);
        editText.setSelection(searchKey.length());
        loadData(searchKey, 1);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH &&
                        !TextUtils.isEmpty(editText.getText().toString())) {
                    searchKey = editText.getText().toString();
                    currpage = 1;
                    mContentList.clear();
                    loadData(searchKey,currpage);
                    return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Content c = (Content) parent.getItemAtPosition(position);
                Intent i = new Intent(SearchFriendAct.this, ChatUserInfoAct.class);
                i.putExtra("show_type", 1);
                i.putExtra("userId", c.getId());
                startActivity(i);
            }
        });
        listView.setOnScrollListener(SearchFriendAct.this);
    }

    private void loadData(String key, int page){
        HashMap<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("currpage",page+"");
        parms.put("q",key);
        HttpUtils.searchPeople(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    String result = gson.toJson(msg.getResult());
                    ChatUserInfoBean chatUserInfoBean = gson.fromJson(result, ChatUserInfoBean.class);
                    pagecount = chatUserInfoBean.getPagecount();
                    mContentList.addAll(userInfo2Content(chatUserInfoBean.getList()));
                    if (mContentList.size() > 0){
                        letAdapter.setData(mContentList);
                    }else {
                        hint.setVisibility(View.VISIBLE);
                        hint.setText("没有搜索到用户");
                    }
                }else {
                    BaseToast.YToastS(SearchFriendAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.YToastS(SearchFriendAct.this,errormsg);
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

    private List<Content> userInfo2Content(List<ChatUserInfoBean.ListBean> infos) {
        List<Content> contents = new ArrayList<>();
        if (infos != null) {
            for (ChatUserInfoBean.ListBean info : infos) {
                Content c = new Content();
                c.setId(info.getId());
                c.setName(info.getName());
                c.setTouxiang(info.getAvatar());
                contents.add(c);
            }
        }
        return contents;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            if (currpage < pagecount) {
                currpage++;
                loadData(searchKey, currpage);
            }
        }
    }
}
