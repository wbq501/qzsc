package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wbq501 on 2017/12/5.
 */

public class CommissionDetailBean implements Serializable{

    private String curr_page;
    private int page_total;
    private List<BwListBean> bw_list;

    public String getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(String curr_page) {
        this.curr_page = curr_page;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public List<BwListBean> getBw_list() {
        return bw_list;
    }

    public void setBw_list(List<BwListBean> bw_list) {
        this.bw_list = bw_list;
    }

    public static class BwListBean implements Serializable{

        private String id;
        private String trade_no;
        private String total_amount;
        private String status;
        private String create_time;
        private String description;
        private String brokerage_desc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBrokerage_desc() {
            return brokerage_desc;
        }

        public void setBrokerage_desc(String brokerage_desc) {
            this.brokerage_desc = brokerage_desc;
        }
    }
}
