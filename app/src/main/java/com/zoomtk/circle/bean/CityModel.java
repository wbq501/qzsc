package com.zoomtk.circle.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class CityModel implements IPickerViewData{
	private String name;
	private String id;
	private List<DistrictModel> districtList;
	
	public CityModel() {
		super();
	}

	public CityModel(String name, String id, List<DistrictModel> districtList) {
		super();

		this.name = name;
		this.id=id;
		this.districtList = districtList;
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

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String getPickerViewText() {
		return name;
	}
}
