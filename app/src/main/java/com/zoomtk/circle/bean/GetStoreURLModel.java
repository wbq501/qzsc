package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/10/30.
 */

public class GetStoreURLModel implements Serializable {
    private String url;
    private AuthCodeBean authCode;
    private String sid;
    private String wshop_name;
    private String avatar;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AuthCodeBean getAuthCode() {
        return authCode;
    }

    public void setAuthCode(AuthCodeBean authCode) {
        this.authCode = authCode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getWshop_name() {
        return wshop_name;
    }

    public void setWshop_name(String wshop_name) {
        this.wshop_name = wshop_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static class AuthCodeBean {
        private String member_id;
        private String auth_code;
        private int add_time;

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getAuth_code() {
            return auth_code;
        }

        public void setAuth_code(String auth_code) {
            this.auth_code = auth_code;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        @Override
        public String toString() {
            return "AuthCodeBean{" +
                    "member_id='" + member_id + '\'' +
                    ", auth_code='" + auth_code + '\'' +
                    ", add_time=" + add_time +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetStoreURLModel{" +
                "url='" + url + '\'' +
                ", authCode=" + authCode +
                ", sid='" + sid + '\'' +
                ", wshop_name='" + wshop_name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
