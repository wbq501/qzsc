package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/11/21.
 */

public class KaiDianBean implements Serializable {
    private int order_total;
    private int goods_total;
    private int team_total;
    private int member_total;
    private int make_money_total;
    private int n_team_total;

    public int getOrder_total() {
        return order_total;
    }

    public void setOrder_total(int order_total) {
        this.order_total = order_total;
    }

    public int getGoods_total() {
        return goods_total;
    }

    public void setGoods_total(int goods_total) {
        this.goods_total = goods_total;
    }

    public int getTeam_total() {
        return team_total;
    }

    public void setTeam_total(int team_total) {
        this.team_total = team_total;
    }

    public int getMember_total() {
        return member_total;
    }

    public void setMember_total(int member_total) {
        this.member_total = member_total;
    }

    public int getMake_money_total() {
        return make_money_total;
    }

    public void setMake_money_total(int make_money_total) {
        this.make_money_total = make_money_total;
    }

    public int getN_team_total() {
        return n_team_total;
    }

    public void setN_team_total(int n_team_total) {
        this.n_team_total = n_team_total;
    }
}
