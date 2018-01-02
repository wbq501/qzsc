package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/12/6.
 */

public class BankmsgBean implements Serializable{
    private String cardholder_name;
    private String bank_phone;
    private String id_card_num;
    private String bank_province_id;
    private String bank_city_id;
    private String bank_name;
    private String bank_card;
    private String province_name;
    private String city_name;
    private String bank_branch_info;

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getBank_phone() {
        return bank_phone;
    }

    public void setBank_phone(String bank_phone) {
        this.bank_phone = bank_phone;
    }

    public String getId_card_num() {
        return id_card_num;
    }

    public void setId_card_num(String id_card_num) {
        this.id_card_num = id_card_num;
    }

    public String getBank_province_id() {
        return bank_province_id;
    }

    public void setBank_province_id(String bank_province_id) {
        this.bank_province_id = bank_province_id;
    }

    public String getBank_city_id() {
        return bank_city_id;
    }

    public void setBank_city_id(String bank_city_id) {
        this.bank_city_id = bank_city_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getBank_branch_info() {
        return bank_branch_info;
    }

    public void setBank_branch_info(String bank_branch_info) {
        this.bank_branch_info = bank_branch_info;
    }

    @Override
    public String toString() {
        return "BankmsgBean{" +
                "cardholder_name='" + cardholder_name + '\'' +
                ", bank_phone='" + bank_phone + '\'' +
                ", id_card_num='" + id_card_num + '\'' +
                ", bank_province_id='" + bank_province_id + '\'' +
                ", bank_city_id='" + bank_city_id + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_card='" + bank_card + '\'' +
                ", province_name='" + province_name + '\'' +
                ", city_name='" + city_name + '\'' +
                ", bank_branch_info='" + bank_branch_info + '\'' +
                '}';
    }
}
