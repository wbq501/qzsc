package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/12/22.
 */

public class FindCicleModle implements Serializable{

    private int curr_page;
    private int count;
    private String total;
    private int page;
    private List<RecCirclBean> rec_circl;

    public int getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(int curr_page) {
        this.curr_page = curr_page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RecCirclBean> getRec_circl() {
        return rec_circl;
    }

    public void setRec_circl(List<RecCirclBean> rec_circl) {
        this.rec_circl = rec_circl;
    }

    public static class RecCirclBean {

        private String circle_id;
        private String circle_img;
        private String circle_name;
        private String circle_desc;
        private String circle_mcount;
        private String mapply_ml;
        private String circle_mastername;
        private int is_new;

        public String getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(String circle_id) {
            this.circle_id = circle_id;
        }

        public String getCircle_img() {
            return circle_img;
        }

        public void setCircle_img(String circle_img) {
            this.circle_img = circle_img;
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

        public String getCircle_mcount() {
            return circle_mcount;
        }

        public void setCircle_mcount(String circle_mcount) {
            this.circle_mcount = circle_mcount;
        }

        public String getMapply_ml() {
            return mapply_ml;
        }

        public void setMapply_ml(String mapply_ml) {
            this.mapply_ml = mapply_ml;
        }

        public String getCircle_mastername() {
            return circle_mastername;
        }

        public void setCircle_mastername(String circle_mastername) {
            this.circle_mastername = circle_mastername;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }
    }
}

