package com.zoomtk.circle.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class DateUtils {
    public static String timeTodate(String time){
        long time1 = 0;
        if (time!=null&&time.length()==10){
            try {
                time1 = Long.parseLong(time) * 1000;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());
        String d=simpleDateFormat.format(time1);
        return d;
    }
    public static String timeToString(Date date){
       long time=date.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd HH:mm",Locale.getDefault());
        String result=simpleDateFormat.format(date);
        return result;
    }
    public static String getTimestamp(Date date){
       String time=date.getTime()+"";
        if (time.length()==13){
            time=Long.parseLong(time)/1000+"";
            return time;
        }
      return null;
    }
}
