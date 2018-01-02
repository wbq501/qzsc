package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/11/28.
 */

public class StoreShareData implements Serializable{
    private String weixin_title;
    private String weixin_body;
    private String weixin_friend_title;
    private String microblog_title;

    public String getWeixin_title() {
        return weixin_title;
    }

    public void setWeixin_title(String weixin_title) {
        this.weixin_title = weixin_title;
    }

    public String getWeixin_body() {
        return weixin_body;
    }

    public void setWeixin_body(String weixin_body) {
        this.weixin_body = weixin_body;
    }

    public String getWeixin_friend_title() {
        return weixin_friend_title;
    }

    public void setWeixin_friend_title(String weixin_friend_title) {
        this.weixin_friend_title = weixin_friend_title;
    }

    public String getMicroblog_title() {
        return microblog_title;
    }

    public void setMicroblog_title(String microblog_title) {
        this.microblog_title = microblog_title;
    }
}
