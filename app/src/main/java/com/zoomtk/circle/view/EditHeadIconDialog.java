package com.zoomtk.circle.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zoomtk.circle.R;

/**
 * Created with Android Studio.
 * <p/>
 * Author:Lw
 * <p/>
 * Data:2015/10/21.
 */

public class EditHeadIconDialog extends AlertDialog implements View.OnClickListener {

    private CallBack callBack;

    public EditHeadIconDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_select_header_view);


        findViewById(R.id.btnCamera).setOnClickListener(this);
        findViewById(R.id.btnPhoto).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);

    }

    public void setTitle(String title) {
        TextView view = (TextView) findViewById(R.id.title);
        if (title != null && !title.isEmpty())
            view.setText(title);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                if (callBack != null) {
                    callBack.camera();
                }
                break;
            case R.id.btnPhoto:
                if (callBack != null) {
                    callBack.photo();
                }
                break;
            case R.id.btnCancel:
                if (callBack != null) {
                    cancel();
                }
                break;

        }
    }

    public interface CallBack {
        void camera();

        void photo();
    }
}
