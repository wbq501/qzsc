package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/11/21.
 */

public class LoginInfoModel implements Serializable{
    private String sid;
    private String w_storename;
    private String today_PV;
    private double total_income;
    private double curr_month_income;
    private int curr_month_ordercount;
    private int partner_new;
    private String order_new;
    private String wshop_url;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getW_storename() {
        return w_storename;
    }

    public void setW_storename(String w_storename) {
        this.w_storename = w_storename;
    }

    public String getToday_PV() {
        return today_PV;
    }

    public void setToday_PV(String today_PV) {
        this.today_PV = today_PV;
    }

    public double getTotal_income() {
        return total_income;
    }

    public void setTotal_income(double total_income) {
        this.total_income = total_income;
    }

    public double getCurr_month_income() {
        return curr_month_income;
    }

    public void setCurr_month_income(double curr_month_income) {
        this.curr_month_income = curr_month_income;
    }

    public int getCurr_month_ordercount() {
        return curr_month_ordercount;
    }

    public void setCurr_month_ordercount(int curr_month_ordercount) {
        this.curr_month_ordercount = curr_month_ordercount;
    }

    public int getPartner_new() {
        return partner_new;
    }

    public void setPartner_new(int partner_new) {
        this.partner_new = partner_new;
    }

    public String getOrder_new() {
        return order_new;
    }

    public void setOrder_new(String order_new) {
        this.order_new = order_new;
    }

    public String getWshop_url() {
        return wshop_url;
    }

    public void setWshop_url(String wshop_url) {
        this.wshop_url = wshop_url;
    }
}
