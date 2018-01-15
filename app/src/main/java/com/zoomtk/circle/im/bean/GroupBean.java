package com.zoomtk.circle.im.bean;

import java.io.Serializable;

/**
 * Created by admin on 2018/1/10.
 */

public class GroupBean implements Serializable{
    private String member_id;
    private String group_id;
    private String group_name;
    private String create_date;
    private String group_num;
    private String groups_img;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public String getGroups_img() {
        return groups_img;
    }

    public void setGroups_img(String groups_img) {
        this.groups_img = groups_img;
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "member_id='" + member_id + '\'' +
                ", group_id='" + group_id + '\'' +
                ", group_name='" + group_name + '\'' +
                ", create_date='" + create_date + '\'' +
                ", group_num='" + group_num + '\'' +
                ", groups_img='" + groups_img + '\'' +
                '}';
    }
}
