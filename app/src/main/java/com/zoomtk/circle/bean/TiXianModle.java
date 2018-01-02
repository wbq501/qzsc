package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class TiXianModle implements Serializable{
    private String openid;
    private String member_truename;
    private String tishi;
    private String tishi1;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getTishi() {
        return tishi;
    }

    public void setTishi(String tishi) {
        this.tishi = tishi;
    }

    public String getTishi1() {
        return tishi1;
    }

    public void setTishi1(String tishi1) {
        this.tishi1 = tishi1;
    }
}
