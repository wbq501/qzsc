package com.zoomtk.circle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.sevenheaven.segmentcontrol.SegmentControl;
import com.zoomtk.circle.Interface.FragmentListenr;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.bean.NearbyStoreBean;
import com.zoomtk.circle.fragment.AddrFragment;
import com.zoomtk.circle.fragment.NearbyStoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/11/10.
 */

public class AddrActivity extends BaseActivity implements FragmentListenr{

    @BindView(R.id.segment_control)
    SegmentControl segment_control;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<Fragment> fragmentList = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_addr;
    }

    @Override
    public void init() {
        fragmentList = new ArrayList<>();
        NearbyStoreFragment nearbyStoreFragment = new NearbyStoreFragment();
        AddrFragment addrFragment = new AddrFragment();
        fragmentList.add(nearbyStoreFragment);
        fragmentList.add(addrFragment);

        segment_control.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                vp.setCurrentItem(index);
            }
        });
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                segment_control.setSelectedIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initdata() {

    }

    @OnClick({R.id.iv_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void adressmsg(NearbyStoreBean nearbyStoreBean,Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra("nearbyStoreBean",nearbyStoreBean);
        if (bundle != null)
            intent.putExtra("bundle",bundle);
        setResult(500,intent);
        finish();
    }
}
