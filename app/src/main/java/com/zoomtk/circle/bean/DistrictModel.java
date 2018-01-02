package com.zoomtk.circle.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

public class DistrictModel implements IPickerViewData{
	private String name;
	private String id;
	
	public DistrictModel() {
		super();
	}

	public DistrictModel(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getPickerViewText() {
		return name;
	}
}
