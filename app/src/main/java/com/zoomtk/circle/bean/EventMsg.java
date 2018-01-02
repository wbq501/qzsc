package com.zoomtk.circle.bean;


import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by wbq501 on 2017/10/30.
 */

public class EventMsg {
    private BaseResp resp;

    public BaseResp getBaseReq() {
        return resp;
    }

    public EventMsg(BaseResp resp) {
        this.resp = resp;
    }
}
