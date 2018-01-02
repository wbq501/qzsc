package com.zoomtk.circle.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wbq501 on 2017/12/13.
 */

public class ThreeViewPager extends ViewPager{
    public ThreeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager) {
            int currentItem = ((ViewPager) v).getCurrentItem();
            int countItem = ((ViewPager) v).getAdapter().getCount();
            if ((currentItem == (countItem - 1) && dx < 0)
                    || (currentItem == 0 && dx > 0)) {
                return false;
            }
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
