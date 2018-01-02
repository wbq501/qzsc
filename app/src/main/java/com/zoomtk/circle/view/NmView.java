package com.zoomtk.circle.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.adapter.LetAdapter;
import com.zoomtk.circle.utils.ConvertUtils;

/**
 * Created by Administrator on 2015/9/5.
 */
public class NmView extends View {
    private char[] sidebar;
    private SectionIndexer sectionIndexter = null;
    private ListView list;
    private TextView mDialogText;
    Bitmap mbitmap;

    public NmView(Context context) {
        super(context);
        init();
    }
    public NmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public NmView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //,'☆'
        sidebar = new char[] { '↑','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z','#'};
//        mbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scroll_bar_search_icon);
    }

    public void setListView(ListView _list) {
        list = _list;
        HeaderViewListAdapter ha = (HeaderViewListAdapter) _list.getAdapter();
        LetAdapter ad = (LetAdapter)ha.getWrappedAdapter();
        sectionIndexter = (SectionIndexer) ad;

    }

    public void setTextView(TextView mDialogText) {
        this.mDialogText = mDialogText;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int i = (int) event.getY();
        int idx = i / (getMeasuredHeight() / sidebar.length);
        if (idx >= sidebar.length) {
            idx = sidebar.length - 1;
        } else if (idx < 0) {
            idx = 0;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
//            setBackgroundResource(R.drawable.scrollbar_bg);
            setBackgroundResource(R.drawable.tongxunlu_zimubeijing);
            mDialogText.setVisibility(View.VISIBLE);
            if (idx == 0) {
                mDialogText.setText("Search");
                mDialogText.setTextSize(16);
            } else {
                mDialogText.setText(String.valueOf(sidebar[idx]));
                mDialogText.setTextSize(34);
            }
            if (sectionIndexter == null) {
                sectionIndexter = (SectionIndexer) list.getAdapter();
            }
            int position = sectionIndexter.getPositionForSection(sidebar[idx]);
            if (position == -1) {
                return true;
            }
            list.setSelection(position);
        } else {
            mDialogText.setVisibility(View.INVISIBLE);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundDrawable(new ColorDrawable(0x00000000));
        }
        return true;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xff858cff);
        paint.setStrokeWidth(6);
        paint.setTextSize(ConvertUtils.sp2px(getContext(),12));
        float widthCenter = getMeasuredWidth() / 2 - paint.measureText("A") / 2;
        float height = getMeasuredHeight() / sidebar.length;
        for (int i = 0; i < sidebar.length; i++) {
//            if (i == 0) {
//                canvas.drawBitmap(mbitmap, widthCenter - 7, (i + 1) * height - height / 2, paint);
//            } else
                canvas.drawText(String.valueOf(sidebar[i]), widthCenter, (i + 1) * height, paint);
        }
        this.invalidate();
        super.onDraw(canvas);
    }
}
