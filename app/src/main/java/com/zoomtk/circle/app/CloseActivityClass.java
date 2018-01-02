package com.zoomtk.circle.app;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbq501 on 2017/10/28.
 */

public class CloseActivityClass {
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void exitClient(Context ctx) {   // 关闭所有Activity
        for (int i = 0; i < activityList.size(); i++) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
    }
}
