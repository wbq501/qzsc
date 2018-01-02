package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/12/19.
 */

public class MemberInfo implements Serializable{
    private String member_name;
    private String code_image;
    private String member_avatar;
    private String member_truename;

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getCode_image() {
        return code_image;
    }

    public void setCode_image(String code_image) {
        this.code_image = code_image;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }
}
