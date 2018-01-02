package com.zoomtk.circle.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.sevenheaven.segmentcontrol.SegmentControl;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseActivity;
import com.zoomtk.circle.fragment.BankFragment;
import com.zoomtk.circle.fragment.WeiXinFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class NewWithdrawalsAct extends BaseActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.segment_control)
    SegmentControl segment_control;
    @BindView(R.id.vp)
    ViewPager vp;

    int type = 0;
    private List<Fragment> fragmentList = null;

    @Override
    public int getLayoutId() {
        type = getIntent().getIntExtra("type",1);
        if (type == 1){
            return R.layout.newwithdrawals;
        }else {
            return R.layout.newwithdrawals2;
        }
    }

    @Override
    public void init() {
        fragmentList = new ArrayList<>();
        if (type == 1){
            fragmentList.add(new WeiXinFragment());
            fragmentList.add(new BankFragment());
        }else {
            fragmentList.add(new BankFragment());
            fragmentList.add(new WeiXinFragment());
        }
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
}
