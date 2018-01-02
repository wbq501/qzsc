package com.zoomtk.circle.down;

/**
 * Created by wbq501 on 2017/12/8.
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
