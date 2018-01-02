package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/12/28.
 */

public class DIanZanModle implements Serializable{
    private int is_like;
    private String member_id;
    private String member_nickname;
    private String member_avatar;

    public int getIs_like() {
        return is_like;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }
}
