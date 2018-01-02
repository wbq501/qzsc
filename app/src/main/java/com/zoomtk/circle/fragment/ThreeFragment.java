package com.zoomtk.circle.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.zoomtk.circle.Config.Config;
import com.zoomtk.circle.Interface.RequestBack;
import com.zoomtk.circle.R;
import com.zoomtk.circle.activity.HelpAct;
import com.zoomtk.circle.base.BaseFragment;
import com.zoomtk.circle.base.BaseJson;
import com.zoomtk.circle.base.BaseToast;
import com.zoomtk.circle.im.adapter.ConversationListAdapterEx;
import com.zoomtk.circle.im.ui.AddFriendsAct;
import com.zoomtk.circle.im.ui.CreateCircle;
import com.zoomtk.circle.im.ui.TongxunluAct;
import com.zoomtk.circle.utils.HttpUtils;
import com.zoomtk.circle.view.ThreeViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wbq501 on 2017/10/30.
 */

public class ThreeFragment extends BaseFragment {

    @BindView(R.id.ll_upload)
    LinearLayout ll_upload;
    @BindView(R.id.circle_menu)
    LinearLayout circle_menu;
    @BindView(R.id.circle_publish_trends)
    Button publish_trends;
    @BindView(R.id.circle_create)
    Button circle_create;
    @BindView(R.id.pager)
    ThreeViewPager pager;

    @BindView(R.id.circle_rb_message)
    RadioButton rb_message;
    @BindView(R.id.circle_rb_trends)
    RadioButton rb_trends;
    @BindView(R.id.circle_rb_circle)
    RadioButton rb_circle;

    private List<Fragment> fragments;
    private int currentitem = 0;

    private Conversation.ConversationType[] mConversationsTypes = null;


    PopupMenu popupMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void init() {
        fragments = new ArrayList<>();
        ConversationListFragment messagefragment = new ConversationListFragment();
        messagefragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
//        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
        Uri uri = Uri.parse("rong://com.zoomtk.circle").buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                .build();
        mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.PUBLIC_SERVICE,
                Conversation.ConversationType.APP_PUBLIC_SERVICE,
                Conversation.ConversationType.SYSTEM
        };
        messagefragment.setUri(uri);

        CircleCircleFrag circleCircleFrag = new CircleCircleFrag();
        CircleTrendFrag circleTrendFrag = new CircleTrendFrag();

        fragments.add(messagefragment);
        fragments.add(circleCircleFrag);
        fragments.add(circleTrendFrag);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rb_message.setChecked(true);
                        circle_menu.setVisibility(View.VISIBLE);
                        publish_trends.setVisibility(View.GONE);
                        circle_create.setVisibility(View.GONE);
                        break;
                    case 1:
                        rb_trends.setChecked(true);
                        circle_menu.setVisibility(View.GONE);
                        publish_trends.setVisibility(View.GONE);
                        circle_create.setVisibility(View.GONE);
                        break;
                    case 2:
                        rb_circle.setChecked(true);
                        circle_menu.setVisibility(View.GONE);
                        publish_trends.setVisibility(View.GONE);
                        circle_create.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (getActivity().getIntent().getBooleanExtra("systemconversation",false)){
            pager.setCurrentItem(0);
        }else {
            pager.setCurrentItem(1);
        }

        initPopupMenu();
    }

    @Override
    protected void initdata() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return getUserById(userId);
            }
        },true);
    }

    private UserInfo getUserById(String userId){
        Map<String,String> parms = new HashMap<>();
        parms.put("token",token);
        parms.put("id",userId);
        HttpUtils.getFriend(parms, new RequestBack() {
            @Override
            public void success(BaseJson msg) throws Exception {
                if (msg.getResultCode().equals(Config.SUCCESS_CODE)){
                    com.zoomtk.circle.bean.UserInfo userInfo1 = gson.fromJson(gson.toJson(msg.getResult()), com.zoomtk.circle.bean.UserInfo.class);
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(userInfo1.getId(),userInfo1.getReally_name(), Uri.parse(userInfo1.getAvatar())));
                }else {
                    BaseToast.ToastS(getActivity(),msg.getResultInfo());
                }
            }

            @Override
            public void error(String errormsg) {
                BaseToast.ToastS(getActivity(),errormsg);
            }
        });
        return null;
    }

    @OnClick({R.id.circle_rb_message,R.id.circle_rb_trends,R.id.circle_rb_circle,R.id.circle_menu,R.id.circle_create})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.circle_rb_message://消息页面
                pager.setCurrentItem(0);
                break;
            case R.id.circle_rb_trends://圈子秀
                pager.setCurrentItem(1);
                break;
            case R.id.circle_rb_circle://圈子
                pager.setCurrentItem(2);
                break;
            case R.id.circle_menu://加号
                if (popupMenu != null)
                    popupMenu.show();
                break;
            case R.id.circle_create:
                Intent intent = new Intent(getActivity(),CreateCircle.class);
                startactivity(intent);
                break;
        }
    }

    private void initPopupMenu(){
        popupMenu = new PopupMenu(getActivity(),view.findViewById(R.id.circle_menu));
        Menu menu = popupMenu.getMenu();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_index,menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()){
                    case R.id.pop_create://群聊
                        intent = new Intent(getActivity(),CreateCircle.class);
                        startactivity(intent);
                        break;
                    case R.id.pop_add://添加朋友
                        intent = new Intent(getActivity(),AddFriendsAct.class);
                        startactivity(intent);
                        break;
                    case R.id.pop_books://通讯录
                        intent = new Intent(getActivity(),TongxunluAct.class);
                        startactivity(intent);
                        break;
                    case R.id.pop_help://帮助反馈
                        intent = new Intent(getActivity(), HelpAct.class);
                        startactivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
