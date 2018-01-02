package com.zoomtk.circle.dialog;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zoomtk.circle.R;
import com.zoomtk.circle.bean.DownloadBean;
import com.zoomtk.circle.down.UpdateManager;
import com.zoomtk.circle.utils.RxBus;
import com.zoomtk.circle.utils.StringUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wbq501 on 2017/12/8.
 */

public class DownDialog extends AlertDialog{

    ProgressBar mProgressBar;
    private TextView down_cale,down_size,down_size2;
    private boolean isCacle = true;
    String apk_file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jr_down/qzsc.apk";
    String downurl;
    private CompositeDisposable cd = new CompositeDisposable();

    public DownDialog(@NonNull Context context,boolean isCacle,String downurl) {
        super(context);
        this.isCacle = isCacle;
        this.downurl = downurl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.down_dialog);
        init();
        initdata();
    }

    private void initdata() {
        subscribeEvent();
        UpdateManager.downloadApk(getContext(), downurl, apk_file, cd);
    }

    private void subscribeEvent() {
        RxBus.getDefault().toObservable(DownloadBean.class).subscribe(
                new Observer<DownloadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(DownloadBean downloadBean) {
                        int progress = (int) Math.round(downloadBean.getBytesReaded() / (double) downloadBean.getTotal() * 100);
                        mProgressBar.setProgress(100);
                        mProgressBar.setSecondaryProgress(progress);
                        Message message = new Message();
                        message.what = progress;
                        message.arg1 = (int) downloadBean.getBytesReaded();
                        message.arg2 = (int) downloadBean.getTotal();
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscribeEvent();
                    }

                    @Override
                    public void onComplete() {
                        subscribeEvent();
                    }
                }
        );
    }

    private void init() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        down_cale = (TextView) findViewById(R.id.down_cale);
        down_size = (TextView) findViewById(R.id.down_size);
        down_size2 = (TextView) findViewById(R.id.down_size2);
        setCancelable(isCacle);
        setCanceledOnTouchOutside(isCacle);
        if (!isCacle){
            down_cale.setVisibility(View.GONE);
        }
        down_cale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateManager.unSubscribe(cd);
                dismiss();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int arg1 = msg.arg1;
            int arg2 = msg.arg2;
            down_size.setText(msg.what+"%");
            down_size2.setText(StringUtils.getDataSize(arg1)+"/"+StringUtils.getDataSize(arg2));
        }
    };
}
