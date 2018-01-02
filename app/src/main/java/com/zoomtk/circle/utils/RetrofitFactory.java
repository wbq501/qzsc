package com.zoomtk.circle.utils;

import com.zoomtk.circle.Config.URLConst;
import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**RXjava封装数据请求
 * Created by win 10 on 2017/10/11.
 */

public class RetrofitFactory {

    public static int TIMEOUT = 1000;
    private static String BASEURL = "http://"+ AppApplication.getContext().getResources().getString(R.string.app_domain)+"/";
//    private static String BASEURL = "https://query.yahooapis.com";


    private static RetrofitFactory mRetrofitFactory;
    private static  RetrofitService retrofitService;
    private RetrofitFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        retrofitService=mRetrofit.create(RetrofitService.class);

    }

    public static RetrofitFactory getInstence(){
        if (mRetrofitFactory==null){
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }

        }
        return mRetrofitFactory;
    }
    public  RetrofitService API(){
        return retrofitService;
    }
}
