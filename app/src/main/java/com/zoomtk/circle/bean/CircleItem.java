package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wbq501 on 2017/12/21.
 */

public class CircleItem implements Serializable{

    private int page;
    private int curr_page;
    private int publish;
    private String reply;
    private List<JoinCircleBean> join_circle;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(int curr_page) {
        this.curr_page = curr_page;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public List<JoinCircleBean> getJoin_circle() {
        return join_circle;
    }

    public void setJoin_circle(List<JoinCircleBean> join_circle) {
        this.join_circle = join_circle;
    }

    public static class JoinCircleBean implements Serializable{

        private String member_id;
        private String circle_id;
        private String circle_name;
        private String member_name;
        private String cm_applycontent;
        private String cm_applytime;
        private String cm_state;
        private String cm_intro;
        private String cm_jointime;
        private String cm_level;
        private String cm_levelname;
        private String cm_exp;
        private String cm_nextexp;
        private String is_identity;
        private String is_allowspeak;
        private String is_star;
        private String cm_thcount;
        private String cm_comcount;
        private String cm_lastspeaktime;
        private String is_recommend;
        private String circle_img;
        private int circle_mcount;
        private String circle_desc;
        private int is_new;
        private String circle_mastername;
        private String mapply_ml;


        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

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

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getCm_applycontent() {
            return cm_applycontent;
        }

        public void setCm_applycontent(String cm_applycontent) {
            this.cm_applycontent = cm_applycontent;
        }

        public String getCm_applytime() {
            return cm_applytime;
        }

        public void setCm_applytime(String cm_applytime) {
            this.cm_applytime = cm_applytime;
        }

        public String getCm_state() {
            return cm_state;
        }

        public void setCm_state(String cm_state) {
            this.cm_state = cm_state;
        }

        public String getCm_intro() {
            return cm_intro;
        }

        public void setCm_intro(String cm_intro) {
            this.cm_intro = cm_intro;
        }

        public String getCm_jointime() {
            return cm_jointime;
        }

        public void setCm_jointime(String cm_jointime) {
            this.cm_jointime = cm_jointime;
        }

        public String getCm_level() {
            return cm_level;
        }

        public void setCm_level(String cm_level) {
            this.cm_level = cm_level;
        }

        public String getCm_levelname() {
            return cm_levelname;
        }

        public void setCm_levelname(String cm_levelname) {
            this.cm_levelname = cm_levelname;
        }

        public String getCm_exp() {
            return cm_exp;
        }

        public void setCm_exp(String cm_exp) {
            this.cm_exp = cm_exp;
        }

        public String getCm_nextexp() {
            return cm_nextexp;
        }

        public void setCm_nextexp(String cm_nextexp) {
            this.cm_nextexp = cm_nextexp;
        }

        public String getIs_identity() {
            return is_identity;
        }

        public void setIs_identity(String is_identity) {
            this.is_identity = is_identity;
        }

        public String getIs_allowspeak() {
            return is_allowspeak;
        }

        public void setIs_allowspeak(String is_allowspeak) {
            this.is_allowspeak = is_allowspeak;
        }

        public String getIs_star() {
            return is_star;
        }

        public void setIs_star(String is_star) {
            this.is_star = is_star;
        }

        public String getCm_thcount() {
            return cm_thcount;
        }

        public void setCm_thcount(String cm_thcount) {
            this.cm_thcount = cm_thcount;
        }

        public String getCm_comcount() {
            return cm_comcount;
        }

        public void setCm_comcount(String cm_comcount) {
            this.cm_comcount = cm_comcount;
        }

        public String getCm_lastspeaktime() {
            return cm_lastspeaktime;
        }

        public void setCm_lastspeaktime(String cm_lastspeaktime) {
            this.cm_lastspeaktime = cm_lastspeaktime;
        }

        public String getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(String is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getCircle_img() {
            return circle_img;
        }

        public void setCircle_img(String circle_img) {
            this.circle_img = circle_img;
        }

        public int getCircle_mcount() {
            return circle_mcount;
        }

        public void setCircle_mcount(int circle_mcount) {
            this.circle_mcount = circle_mcount;
        }

        public String getCircle_desc() {
            return circle_desc;
        }

        public void setCircle_desc(String circle_desc) {
            this.circle_desc = circle_desc;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public String getCircle_mastername() {
            return circle_mastername;
        }

        public void setCircle_mastername(String circle_mastername) {
            this.circle_mastername = circle_mastername;
        }

    }
}
