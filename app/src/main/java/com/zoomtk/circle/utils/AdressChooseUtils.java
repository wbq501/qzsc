package com.zoomtk.circle.utils;

import com.zoomtk.circle.R;
import com.zoomtk.circle.app.AppApplication;
import com.zoomtk.circle.bean.CityModel;
import com.zoomtk.circle.bean.DistrictModel;
import com.zoomtk.circle.bean.ProvinceModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by wbq501 on 2017/11/16.
 */

public class AdressChooseUtils {
    private static ArrayList<ProvinceModel> provincelists=new ArrayList<>();
    private static ArrayList<ArrayList<CityModel>> citylists=new ArrayList<>();
    private static ArrayList<ArrayList<ArrayList<DistrictModel>>> districtlists=new ArrayList<>();

    private static void setAdress() {
        List<ProvinceModel> provinceList = null;
        try {
            InputStream input = AppApplication.getContext().getResources().openRawResource(R.raw.province_data);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();

            for (int i = 0; i < provinceList.size(); i++){
                ProvinceModel provinceModel = provinceList.get(i);
                provincelists.add(provinceModel);
                ArrayList<CityModel> list = new ArrayList<>();
                ArrayList<ArrayList<DistrictModel>> lists = new ArrayList<>();
                for (int j = 0; j < provinceModel.getCityList().size(); j++){
                    CityModel cityModel = provinceModel.getCityList().get(j);
                    list.add(cityModel);
                    ArrayList<DistrictModel> listd = new ArrayList<>();
                    for (int k = 0; k < cityModel.getDistrictList().size(); k++){
                        DistrictModel districtModel = cityModel.getDistrictList().get(k);
                        listd.add(districtModel);
                    }
                    lists.add(listd);
                }
                citylists.add(list);
                districtlists.add(lists);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static ArrayList<ProvinceModel> getProvincelists(){
        setAdress();
        return provincelists;
    }

    public static ArrayList<ArrayList<CityModel>> getCitylists(){
        setAdress();
        return citylists;
    }

    public static ArrayList<ArrayList<ArrayList<DistrictModel>>> getDistrictlist(){
        setAdress();
        return districtlists;
    }
}
