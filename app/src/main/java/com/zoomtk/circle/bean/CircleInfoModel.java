package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/12/25.
 */

public class CircleInfoModel implements Serializable{
    private GroupBean group;
    private int is_identity;
    private CircleBean circle;
    private MemberBean member;
    private int is_circle_member;

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public int getIs_identity() {
        return is_identity;
    }

    public void setIs_identity(int is_identity) {
        this.is_identity = is_identity;
    }

    public CircleBean getCircle() {
        return circle;
    }

    public void setCircle(CircleBean circle) {
        this.circle = circle;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public int getIs_circle_member() {
        return is_circle_member;
    }

    public void setIs_circle_member(int is_circle_member) {
        this.is_circle_member = is_circle_member;
    }

    public static class GroupBean {
        private String id;
        private String member_id;
        private String group_id;
        private String group_name;
        private String circle_id;
        private String create_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(String circle_id) {
            this.circle_id = circle_id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }
    }

    public static class CircleBean {

        private String circle_id;
        private String circle_name;
        private String circle_desc;
        private int circle_mcount;
        private String circle_masterid;
        private String circle_img;
        private String is_open;
        private int cm_online_count;
        private int cm_chat_online_count;
        private List<?> image_list;

        public String getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(String circle_id) {
            this.circle_id = circle_id;
        }

        public String getCircle_name() {
            return circle_name;
        }

        public void setCircle_name(String circle_name) {
            this.circle_name = circle_name;
        }

        public String getCircle_desc() {
            return circle_desc;
        }

        public void setCircle_desc(String circle_desc) {
            this.circle_desc = circle_desc;
        }

        public int getCircle_mcount() {
            return circle_mcount;
        }

        public void setCircle_mcount(int circle_mcount) {
            this.circle_mcount = circle_mcount;
        }

        public String getCircle_masterid() {
            return circle_masterid;
        }

        public void setCircle_masterid(String circle_masterid) {
            this.circle_masterid = circle_masterid;
        }

        public String getCircle_img() {
            return circle_img;
        }

        public void setCircle_img(String circle_img) {
            this.circle_img = circle_img;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public int getCm_online_count() {
            return cm_online_count;
        }

        public void setCm_online_count(int cm_online_count) {
            this.cm_online_count = cm_online_count;
        }

        public int getCm_chat_online_count() {
            return cm_chat_online_count;
        }

        public void setCm_chat_online_count(int cm_chat_online_count) {
            this.cm_chat_online_count = cm_chat_online_count;
        }

        public List<?> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<?> image_list) {
            this.image_list = image_list;
        }
    }

    public static class MemberBean {
        /**
         * member_id : 97303
         * nickname : 测试小短腿
         */

        private String member_id;
        private String nickname;

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
