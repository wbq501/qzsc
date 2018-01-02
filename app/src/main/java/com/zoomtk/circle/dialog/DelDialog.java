package com.zoomtk.circle.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.zoomtk.circle.R;
import com.zoomtk.circle.base.BaseToast;

/**
 * Created by wbq501 on 2017/11/13.
 */

public class DelDialog extends Dialog{
    private String content;
    private int is_self;
    private Del delComment;
    public DelDialog(@NonNull Context context, String content, int is_self, Del del) {
        super(context);
        this.content = content;
        this.is_self = is_self;
        this.delComment = del;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.del_dialog);
        Button copy = (Button) findViewById(R.id.copy);
        final Button del = (Button) findViewById(R.id.del);
        View line = findViewById(R.id.line);
        if (is_self == 1){
            del.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }else {
            del.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(content);
                BaseToast.YToastS(getContext(),"复制成功");
                dismiss();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delComment.del();
                dismiss();
            }
        });
    }

    public interface Del{
        void del();
    }
}
