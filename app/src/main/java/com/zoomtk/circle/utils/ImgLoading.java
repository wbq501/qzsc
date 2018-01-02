package com.zoomtk.circle.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zoomtk.circle.R;

/**
 * Created by wbq501 on 2017/10/28.
 */

public class ImgLoading {
    public static void LoadCache(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.logo36).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.logo36);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    public static void Loadimg(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.logo36).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.mipmap.logo36);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void LoadRoundCache(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.logo36)
                .transform(new RoundedCorners(10))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.logo36);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void LoadRound(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.logo36)
                .transform(new RoundedCorners(10))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.logo36);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void LoadCircleCache(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.logo36)
                .transform(new CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.logo36);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void LoadCircle(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.logo36)
                .transform(new CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.logo36);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
