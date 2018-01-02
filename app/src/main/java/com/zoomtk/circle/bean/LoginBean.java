package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * 登录返回参数
 * Created by wbq501 on 2017/10/28.
 */

public class LoginBean implements Serializable{
    private String token;
    private String member_id;
    private String member_name;
    private String member_truename;
    private String member_mobile;
    private String member_apploginnum;
    private int spid;
    private int open_wshop;
    private String open_wshop_errmsg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getMember_apploginnum() {
        return member_apploginnum;
    }

    public void setMember_apploginnum(String member_apploginnum) {
        this.member_apploginnum = member_apploginnum;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public int getOpen_wshop() {
        return open_wshop;
    }

    public void setOpen_wshop(int open_wshop) {
        this.open_wshop = open_wshop;
    }

    public String getOpen_wshop_errmsg() {
        return open_wshop_errmsg;
    }

    public void setOpen_wshop_errmsg(String open_wshop_errmsg) {
        this.open_wshop_errmsg = open_wshop_errmsg;
    }
}
