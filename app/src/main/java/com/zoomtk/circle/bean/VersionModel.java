package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/12/7.
 */

public class VersionModel implements Serializable{
    private String app_type;
    private String app_name;
    private String app_version;
    private String app_path;
    private String forced_update;

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_path() {
        return app_path;
    }

    public void setApp_path(String app_path) {
        this.app_path = app_path;
    }

    public String getForced_update() {
        return forced_update;
    }

    public void setForced_update(String forced_update) {
        this.forced_update = forced_update;
    }
}
