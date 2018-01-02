package com.zoomtk.circle.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by rubin on 2015-10-15.
 * 与转换相关的实体类
 */
public class ConvertUtils {

    public static Uri resourceToUri(Context cxt, int resourceId) {
        return Uri.parse("android.resource://" + cxt.getPackageName()
                + "/" + resourceId);
    }
    public static String longToStringDataFormat(long time, String format) {
        SimpleDateFormat dateFormat = null;
        if (TextUtils.isEmpty(format)) {
            format = "MM-dd HH:mm";
            dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            if (isToday(new Date(time))) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                String pam = "";
                if (calendar.get(Calendar.AM_PM) == 0) {
                    pam = "上午";
                } else {
                    pam = "下午";
                }
                format = "HH:mm";
                dateFormat = new SimpleDateFormat(format,Locale.getDefault());
                return pam + " " + dateFormat.format(new Date(time));
            } else {
                return dateFormat.format(new Date(time));
            }
        } else {
            dateFormat = new SimpleDateFormat(format,Locale.getDefault());
            return dateFormat.format(new Date(time));
        }

    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        return isTheDay(date, new Date());
    }

    /**
     * 是否是指定日期
     *
     * @param date
     * @param day
     * @return
     */
    private static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= dayBegin(day).getTime()
                && date.getTime() <= dayEnd(day).getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    private static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    private static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * list转string
     *
     * @param list
     * @param sep  分割符号
     * @return
     */
    public static String list2String(List<String> list, String sep) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : list) {
            stringBuffer.append(s + sep);
        }
        stringBuffer.delete(stringBuffer.length() - sep.length(), stringBuffer.length() - 1);

        return stringBuffer.toString();
    }
    public static String list2String(List<String> list ) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : list) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }
    public static int StringToInt(Object value, int defaultValue) {
        if (null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString()).intValue();
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (NumberFormatException e1) {
                return defaultValue;
            }
        }
    }
    public static float StringToFolat(Object value, float defaultValue) {
        if (null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Float.valueOf(value.toString()).intValue();
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (NumberFormatException e1) {
                return defaultValue;
            }
        }
    }
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static String EmptyUtil(String s){
        if (s==null){
            return "";
        }
        return s;
    }
}
