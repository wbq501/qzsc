package com.zoomtk.circle.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zoomtk.circle.Interface.CityBack;
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

public class PickerCityUtils {
    private static boolean isFinish = false;

    public static void ChooseCity(Context context, final CityBack cityBack){
        setAdress();
        if (!isFinish){
            cityBack.error("数据加载中,请稍后重试..");
            return;
        }
        OptionsPickerView pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                cityBack.choose(provincelists.get(options1).getName(),citylists.get(options1).get(options2).getName(),districtlists.get(options1).get(options2).get(options3).getName()
                        ,provincelists.get(options1).getId(),citylists.get(options1).get(options2).getId(),districtlists.get(options1).get(options2).get(options3).getId());
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("城市选择")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setContentTextSize(18)
                .setSelectOptions(0,0,0)
                .setCyclic(false,false,false)
                .setOutSideCancelable(false)
                .build();
        pickerView.setPicker(provincelists,citylists,districtlists);
        pickerView.show();
    }

    public static void ChooseCity2(Context context, final CityBack cityBack){
        setAdress();
        if (!isFinish){
            cityBack.error("数据加载中,请稍后重试..");
            return;
        }
        OptionsPickerView pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                cityBack.choose(provincelists.get(options1).getName(),citylists.get(options1).get(options2).getName(),null
                        ,provincelists.get(options1).getId(),citylists.get(options1).get(options2).getId(),null);
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("城市选择")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setContentTextSize(18)
                .setSelectOptions(0,0,0)
                .setCyclic(false,false,false)
                .setOutSideCancelable(false)
                .build();
        pickerView.setPicker(provincelists,citylists);
        pickerView.show();
    }

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
            isFinish = true;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

}
