package com.zoomtk.circle.im.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.LetAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.bean.Content;
import com.zoomtk.circle.bean.TongXunLuModel;
import com.zoomtk.circle.im.utils.PinyinComparator;
import com.zoomtk.circle.im.utils.PinyinUtils;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.NmView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * 通讯录
 * Created by wbq501 on 2017/12/18.
 */

public class TongxunluAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.my_friends_num)
    TextView friends_num;
    @BindView(R.id.tv_add_new_friend)
    TextView add_friends;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.nmView)
    NmView nmView;

    View head;
    private Button btnsousuo;
    private EditText et_sousuo;
    private LinearLayout ll_addfriends;
    private LinearLayout mStartGroupChat;

    private TextView mDialogText;
    private WindowManager mWindowManager;

    private LetAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.tongxunlu;
    }

    @Override
    public void init() {
        head = LayoutInflater.from(this).inflate(R.layout.head,null);
        btnsousuo = (Button) head.findViewById(R.id.btnsousuo);
        et_sousuo = (EditText) head.findViewById(R.id.et_sousuo);

        mStartGroupChat = head.findViewById(R.id.layout_start_group);
        ll_addfriends = head.findViewById(R.id.layout_add_friend);
        mStartGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TongxunluAct.this,CreateCircle.class);
                startactivity(intent);
            }
        });
        ll_addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TongxunluAct.this,AddFriendsAct.class);
                startactivity(intent);
            }
        });

        btnsousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
                adapter.setData(getSeachData(et_sousuo.getText().toString()));
            }
        });
        et_sousuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    adapter.setData(getSeachData(et_sousuo.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDialogText = (TextView) LayoutInflater.from(this).inflate(R.layout.list_position, null);//中间出现的只是字母
        lv.addHeaderView(head);
        mDialogText.setVisibility(View.INVISIBLE);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        try {
            mWindowManager.addView(mDialogText, lp);
        } catch (Exception mE) {
            mE.printStackTrace();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Content content = (Content) parent.getItemAtPosition(position);
                RongIM.getInstance().startPrivateChat((Context) TongxunluAct.this,
                        content.getId(), content.getName());

            }
        });

        nmView.setTextView(mDialogText);
        adapter = new LetAdapter(this, new ArrayList<Content>());
        lv.setAdapter(adapter);
        nmView.setListView(lv);

    }

    @Override
    public void initdata() {
        dialog.show();
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        HttpUtils.getFriends(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                dialog.dismiss();
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    TongXunLuModel tongXunLuModel = gson.fromJson(gson.toJson(msg.getResult()), TongXunLuModel.class);
                    ArrayList<Content> list=new ArrayList<Content>();
                    friends_num.setText("我的好友("+tongXunLuModel.getCount()+")");
                    for (TongXunLuModel.ListBean mBean: tongXunLuModel.getList()) {
                        Content mContent=new Content();
                        mContent.setId(mBean.getId());
                        mContent.setName(mBean.getReally_name());
                        mContent.setTouxiang(mBean.getAvatar());
                        list.add(mContent);
                    }
                    String name = "";
                    ArrayList<Content> removeData = new ArrayList<Content>();
                    for (int i = 0; i < list.size(); i++) {
                        name = list.get(i).getName();
                        if (TextUtils.isEmpty(name)) {
                            removeData.add(list.get(i));
                            continue;
                        }
                        String pinyinString = PinyinUtils.getPinYin(name);
                        char first = pinyinString.charAt(0);
                        if (first < 'A' || first > 'Z') {
                            first = '#';
                        }
                        list.get(i).setLetter(first + "");
                    }
                    //删除名字是空的用户
                    if (removeData.size() > 0) {
                        for (Content i : removeData) {
                            list.remove(i);
                        }
                    }

                    //根据a-z进行排序
                    Collections.sort(list, new PinyinComparator());
                    content.addAll(list);
                    adapter.setData(list);
                }else {
                    BaseToast.YToastS(TongxunluAct.this,msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                dialog.dismiss();
                BaseToast.YToastS(TongxunluAct.this,errormsg);
            }
        });
    }

    @OnClick({R.id.tv_add_new_friend,R.id.back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_add_new_friend:
                Intent intent = new Intent(TongxunluAct.this,AddFriendsAct.class);
                startactivity(intent);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    //    搜索
    List<Content> content=new ArrayList<>();

    private List<Content> getSeachData(String keyword) {
        List<Content> result = new ArrayList<>();
        for (Content c : content) {
            if (c.getName().contains(keyword)) {
                result.add(c);
            }
        }
        return result;
    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (c == 'à') {
            return "A";

        } else if (c == 'ĕ') {
            return "E";
        } else {
            return "#";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mDialogText);
    }
}
