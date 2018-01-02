package com.zoomtk.circle.utils;

import android.content.Context;

/**
 * Created by admin on 2017/12/26.
 */

public class SharePreferencesUtils {
    public static String getToken(Context context){
        String token = context.getSharedPreferences("passwordFile", Context.MODE_PRIVATE).getString("token", "");
        return token;
    }
}
