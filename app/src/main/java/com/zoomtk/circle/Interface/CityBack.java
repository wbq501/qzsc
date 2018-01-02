package com.zoomtk.circle.Interface;

/**
 * Created by wbq501 on 2017/11/16.
 */

public interface CityBack {
    void choose(String provinceName,String cityName,String DistrictName,String provinceId,String cityId,String districtId);
    void error(String msg);
}
