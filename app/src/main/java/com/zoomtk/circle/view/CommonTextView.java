package com.zoomtk.circle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zoomtk.circle.utils.Global;

import java.lang.reflect.Field;

public class CommonTextView extends android.support.v7.widget.AppCompatTextView {
	public CommonTextView(Context paramContext) {
		super(paramContext);
		init(paramContext);
	}

	public CommonTextView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext);
	}

	public CommonTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init(paramContext);
	}

	private float getTextLineSpacing() {
		try {
			Field localField = android.widget.TextView.class.getDeclaredField("mSpacingAdd");
			localField.setAccessible(true);
			float f = localField.getFloat(this);
			return f;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return 0.0F;
	}

	private void init(Context context) {
		if ((!isInEditMode()) && (Global.APP_TYPEFACE != null)) {
			setTypeface(Global.APP_TYPEFACE);
		}
	}

	protected void onDraw(Canvas canvas) {
		if (Build.VERSION.SDK_INT >= 21) {
			super.onDraw(canvas);
			return;
		}
		canvas.translate(0.0F, getTextLineSpacing() / 2.0F);
		super.onDraw(canvas);
	}
}