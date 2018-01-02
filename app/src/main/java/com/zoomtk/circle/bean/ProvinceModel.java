package com.zoomtk.circle.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class ProvinceModel implements IPickerViewData{
	private String name;
	private String id;
	private List<CityModel> cityList;
	
	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name,String id ,List<CityModel> cityList) {
		super();
		this.name = name;
		this.id=id;
		this.cityList = cityList;
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

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String getPickerViewText() {
		return name;
	}
}
