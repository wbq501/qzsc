package com.zoomtk.circle.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created with IntelliJ IDEA.
 * Author:xiaxf
 * Date:2015/6/2.
 */
public class Global {
	public static Typeface APP_TYPEFACE;


	public static void init(Context context){
		APP_TYPEFACE = Typeface.createFromAsset(context.getAssets(), "fzltxh_gbk.ttf");
	}

}
