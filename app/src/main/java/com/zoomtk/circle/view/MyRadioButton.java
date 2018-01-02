package com.zoomtk.circle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.zoomtk.circle.R;

/**
 * Created by li on 2016/8/24.
 */
public class MyRadioButton extends RadioButton {
    private int mDrawableSize;
    public MyRadioButton(Context context) {
        this(context,null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Drawable drawableleft=null,drawabletop=null,drawableright=null,drawablebottom=null;
        TypedArray mTypedArray=context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
        int n=mTypedArray.getIndexCount();
        for (int i = 0; i <n ; i++) {
            int attr=mTypedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyRadioButton_drawablesize:
                    mDrawableSize =mTypedArray.getDimensionPixelSize(R.styleable.MyRadioButton_drawablesize,50);
                    break;
                case R.styleable.MyRadioButton_drawableleft:
                    drawableleft=mTypedArray.getDrawable(attr);
                    break;
                case R.styleable.MyRadioButton_drawabletop:
                    drawabletop=mTypedArray.getDrawable(attr);
                    break;
                case R.styleable.MyRadioButton_drawableright:
                    drawableright=mTypedArray.getDrawable(attr);
                    break;
                case R.styleable.MyRadioButton_drawablebuttom:
                    drawablebottom=mTypedArray.getDrawable(attr);
                    break;
                default:
                    break;
            }

        }
        mTypedArray.recycle();
        setCompoundDrawablesWithIntrinsicBounds(drawableleft,drawabletop,drawableright,drawablebottom);
    }
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
}
