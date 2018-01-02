package com.zoomtk.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wbq501 on 2017/12/21.
 */

public class ChatUserInfoBean implements Serializable{
    private int currpage;
    private int pagecount;
    private List<ListBean> list;
    public int getCurrpage() {
        return currpage;
    }
    public void setCurrpage(int currpage) {
        this.currpage = currpage;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String id;
        private String name;
        private Object phone;
        private String avatar;
        private String letter;

        public String getLetter() {
            return letter;
        }

        public void setLetter(String mLetter) {
            letter = mLetter;
        }

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

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
