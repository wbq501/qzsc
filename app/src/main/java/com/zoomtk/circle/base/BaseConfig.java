package com.zoomtk.circle.base;

/**
 * Created by win 10 on 2017/10/12.
 */

public class BaseConfig {
    public static boolean isLog = true;
    public static boolean isToast = true;
    public static boolean isError = true;

    public static String TOEAST = "数据错误";
    public static String User = "请输入正确的手机号或验证码";

    public static final int DOWN_REFRESH_TIME = 2000;//下拉时间
    public static final int UP_REFRESH_TIME = 2000;//上拉时间

    public static final int LOADINGFINISH = 0;//加载完成
    public static final int LOADING = 1;//加载中
    public static final int LOADING_ERROR = 2;//加载错误
    public static final int LOADING_NO = 3;//无数据
}
