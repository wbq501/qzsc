package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/11/23.
 */

public class DistriButorModels implements Serializable{
    private String resultCode;
    private String resultInfo;
    private DistriButorModel first;
    private DistriButorModel second;
    private String total_num;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public DistriButorModel getFirst() {
        return first;
    }

    public void setFirst(DistriButorModel first) {
        this.first = first;
    }

    public DistriButorModel getSecond() {
        return second;
    }

    public void setSecond(DistriButorModel second) {
        this.second = second;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public static class DistriButorModel implements Serializable{
        private String sale_amount;
        private String sale_order;
        private String member_num;

        public String getSale_amount() {
            return sale_amount;
        }

        public void setSale_amount(String sale_amount) {
            this.sale_amount = sale_amount;
        }

        public String getSale_order() {
            return sale_order;
        }

        public void setSale_order(String sale_order) {
            this.sale_order = sale_order;
        }

        public String getMember_num() {
            return member_num;
        }

        public void setMember_num(String member_num) {
            this.member_num = member_num;
        }
    }
}
