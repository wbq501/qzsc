package com.zoomtk.circle.Config;

import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;

/**
 * Created by wbq501 on 2017/10/27.
 */

public class URLConst {
    public static final String SERVER_app_key = "10002";
    public static final String SERVER_app_secret = "64f022edeec768861944727d25c9bf63";
    public static final String URL = "http://"+ AppApplication.getContext().getResources().getString(R.string.app_domain)+"/index.php?s="; //API的前半部分url,测试服
    public static final String BASE_URL = URL+"/api/"; //API的前半部分url,测试服

    public static final String allUrl = "."+AppApplication.getContext().getResources().getString(R.string.app_domain)+"/";

    public static final String adressurl = AppApplication.getContext().getResources().getString(R.string.app_domain)+"/";

    public static final String showurl = adressurl.replace("v","");
}
