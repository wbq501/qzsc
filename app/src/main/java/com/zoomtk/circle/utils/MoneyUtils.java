package com.zoomtk.circle.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbq501 on 2017/11/29.
 */

public class MoneyUtils {
    private static List<Activity> lists = new ArrayList<>();

    public static void addActivity(Activity activity) {
        lists.add(activity);
    }
    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }
            lists.clear();
        }
    }
}
