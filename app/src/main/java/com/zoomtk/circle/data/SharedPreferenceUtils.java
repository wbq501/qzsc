package com.zoomtk.circle.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SharedPreferenceUtils {

    public static final String DEFULT_PREFERENCE_FILE_NAME = "default_preference_file_name";

    /**
     * 保存信息到sharedprefrence
     *
     * @param context 上下文
     * @param xmlName 路径
     * @param map     信息的map对象
     * @return 是否保存成功
     */
    public static boolean saveShareprefrence(Context context, String xmlName, HashMap<String, String> map) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    xmlName.replaceAll("/", ""), context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> set = map.keySet();
            for (String key : set) {
                String value = map.get(key);
                editor.putString(key, value);
            }
            editor.commit();
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取信息
     *
     * @param context 上下文
     * @param xmlName 路径
     * @param key     关键字
     * @return 返回字符串
     */
    public static String getStringSharedPreferences(Context context,
                                                    String xmlName, String key) {
        String result = null;
        StringBuffer buffer = new StringBuffer();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                xmlName, context.MODE_PRIVATE);
        buffer.append(sharedPreferences.getString(key, ""));
        result = buffer.toString();
        return result;
    }

    /**
     * 读取信息
     *
     * @param context 上下文
     * @param xmlName 路径
     * @param key     关键字
     * @return 返回整形
     */
    public static int getSpByInt(Context context,
                                 String xmlName, String key) {
        int result = -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                xmlName, context.MODE_PRIVATE);
        result = sharedPreferences.getInt(key, -1);
        return result;
    }

    public static void saveInfo(Context context, String area_info, String city, String lat, String lng) {

        SharedPreferences spf = context.getSharedPreferences("storeInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("area_info", area_info);
        editor.putString("city", city);
        editor.putString("lat", lat);
        editor.putString("lng", lng);
        editor.apply();
    }

    public static boolean saveShareprefrence(Context context, String name, String value) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(DEFULT_PREFERENCE_FILE_NAME,
                    context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(name, value);
            editor.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getShareprefrence(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DEFULT_PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

    public static String getShareprefrence(Context mContext, String XmlName, String name) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(XmlName.replaceAll("/", ""),
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

    public static void clearShareprefrence(Context mContext, String xmlName) {
        SharedPreferences mPreferences = mContext.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }
}
