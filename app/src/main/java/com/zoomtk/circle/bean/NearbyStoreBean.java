package com.zoomtk.circle.bean;

import java.io.Serializable;

/**
 * Created by wbq501 on 2017/11/11.
 */

public class NearbyStoreBean implements Serializable{
    public String area_info;
    public String city;
    public String lat;
    public String lng;

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "NearbyStoreBean{" +
                "area_info='" + area_info + '\'' +
                ", city='" + city + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
