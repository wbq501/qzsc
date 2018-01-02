package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wbq501 on 2017/12/18.
 */

public class TongXunLuModel implements Serializable{
    private int count;


    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        private String id;
        private String name;
        private String avatar;
        private String really_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getReally_name() {
            return really_name==null?name:really_name;
        }

        public void setReally_name(String really_name) {
            this.really_name = really_name;
        }
    }
}
