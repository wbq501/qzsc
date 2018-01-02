package com.zoomtk.circle.base;

import android.util.Log;

/**
 * Created by win 10 on 2017/10/12.
 */

public class BaseLog {
    public static void LogD(String TAG,String msg){
        if (BaseConfig.isLog)
            Log.d(TAG,msg);
    }
    public static void LogE(String TAG,String msg){
        if (BaseConfig.isLog)
            Log.e(TAG,msg);
    }
    public static void YLogD(String TAG,String msg){
        Log.d(TAG,msg);
    }
}
