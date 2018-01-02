package com.zoomtk.circle.utils;

import android.support.annotation.Nullable;

import com.zoomtk.circle.bean.DownloadBean;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by wbq501 on 2017/12/7.
 * 文件下载
 */

public class DownFileResponseBody extends ResponseBody{

    private Response originalResponse;

    public DownFileResponseBody(Response originalResponse){
        this.originalResponse = originalResponse;
    }

    //返回内容类型
    @Nullable
    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    //返回长度，没有就是-1
    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {

            long bytesReaded = 0;

            //读取到的长度
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                // 通过RxBus发布进度信息
                RxBus.getDefault().send(new DownloadBean(contentLength(), bytesReaded));
                return bytesRead;
            }
        });
    }
}
