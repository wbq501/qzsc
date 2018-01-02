package com.zoomtk.circle.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TableLayout;

import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.MyFragmentPagerAdapter;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.fragment.HelpFragment;
import com.zoomtk.circle.fragment.SuggestionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/8.
 */

public class HelpAct extends BaseActivity{

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    List<Fragment> fragments;
    List<String> titles;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void init() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("帮助");
        titles.add("反馈");
        HelpFragment helpFragment = new HelpFragment();
        SuggestionFragment suggestionFragment = new SuggestionFragment();
        fragments.add(helpFragment);
        fragments.add(suggestionFragment);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < titles.size(); i++){
            tablayout.addTab(tablayout.newTab().setText(titles.get(i)));
        }

        HelpFragmentAdapter adapter = new HelpFragmentAdapter(getSupportFragmentManager(),fragments,titles);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tablayout.setupWithViewPager(viewpager);
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

    public class HelpFragmentAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;
        List<String> titles;

        public HelpFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles){
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
