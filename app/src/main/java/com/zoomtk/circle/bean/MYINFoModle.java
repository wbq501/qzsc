package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/11/21.
 */

public class MYINFoModle implements Serializable{

    private String member_id;
    private String member_name;
    private String member_avatar;
    private String member_sex;
    private String member_truename;
    private String school;
    private String sid;
    private String percent;
    private String friend_num;
    private String attention;
    private String circle_num;

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

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }

    public String getMember_truename() {
        return member_truename;
    }

    public void setMember_truename(String member_truename) {
        this.member_truename = member_truename;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getFriend_num() {
        return friend_num;
    }

    public void setFriend_num(String friend_num) {
        this.friend_num = friend_num;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getCircle_num() {
        return circle_num;
    }

    public void setCircle_num(String circle_num) {
        this.circle_num = circle_num;
    }
}
