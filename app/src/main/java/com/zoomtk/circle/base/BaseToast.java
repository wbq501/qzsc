package com.zoomtk.circle.base;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by win 10 on 2017/10/12.
 */

public class BaseToast {
    public static void ToastL(Context context,String msg){
        if (BaseConfig.isToast)
            Toast.makeText(context,msg+"",Toast.LENGTH_LONG).show();
    }
    public static void ToastS(Context context,String msg){
        if (BaseConfig.isToast)
            Toast.makeText(context,msg+"",Toast.LENGTH_SHORT).show();
    }

    public static void YToastL(Context context,String msg){
        Toast.makeText(context,msg+"",Toast.LENGTH_LONG).show();
    }

    public static void YToastS(Context context,String msg){
        Toast.makeText(context,msg+"",Toast.LENGTH_SHORT).show();
    }
}
