package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class LoginAuthCodeModel implements Serializable{
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
}
