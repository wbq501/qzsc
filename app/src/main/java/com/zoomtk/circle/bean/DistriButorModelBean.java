package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wbq501 on 2017/11/24.
 */

public class DistriButorModelBean implements Serializable{

    private int total_page;
    private String curr_page;
    private List<ContentBean> content;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public String getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(String curr_page) {
        this.curr_page = curr_page;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private String order_total;
        private String w_storename;
        private String wshop_level_desc;
        private String wshop_level_icon;
        private String sid;
        private String create_time;
        private String contribution;
        private String distributors_num;
        private String member_truename;
        private String member_mobile;
        private String member_login_time;
        private String member_id;
        private String avatar;

        public String getOrder_total() {
            return order_total;
        }

        public void setOrder_total(String order_total) {
            this.order_total = order_total;
        }

        public String getW_storename() {
            return w_storename;
        }

        public void setW_storename(String w_storename) {
            this.w_storename = w_storename;
        }

        public String getWshop_level_desc() {
            return wshop_level_desc;
        }

        public void setWshop_level_desc(String wshop_level_desc) {
            this.wshop_level_desc = wshop_level_desc;
        }

        public String getWshop_level_icon() {
            return wshop_level_icon;
        }

        public void setWshop_level_icon(String wshop_level_icon) {
            this.wshop_level_icon = wshop_level_icon;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContribution() {
            return contribution;
        }

        public void setContribution(String contribution) {
            this.contribution = contribution;
        }

        public String getDistributors_num() {
            return distributors_num;
        }

        public void setDistributors_num(String distributors_num) {
            this.distributors_num = distributors_num;
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

        public String getMember_login_time() {
            return member_login_time;
        }

        public void setMember_login_time(String member_login_time) {
            this.member_login_time = member_login_time;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
