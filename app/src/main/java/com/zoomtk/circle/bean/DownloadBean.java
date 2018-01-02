package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/12/7.
 */

public class DownloadBean implements Serializable{
    private long total;
    private long bytesReaded;

    public DownloadBean(long total, long bytesReaded) {
        this.total = total;
        this.bytesReaded = bytesReaded;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBytesReaded() {
        return bytesReaded;
    }

    public void setBytesReaded(long bytesReaded) {
        this.bytesReaded = bytesReaded;
    }
}
