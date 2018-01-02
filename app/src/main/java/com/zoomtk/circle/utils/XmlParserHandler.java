package com.zoomtk.circle.utils;
import com.zoomtk.circle.bean.CityModel;
import com.zoomtk.circle.bean.DistrictModel;
import com.zoomtk.circle.bean.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class XmlParserHandler extends DefaultHandler {

	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
	 	  
	public XmlParserHandler() {
		
	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {

	}

	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		switch (qName) {
			case "province":
				provinceModel = new ProvinceModel();
				provinceModel.setName(attributes.getValue(0));
				provinceModel.setId(attributes.getValue(1));
				provinceModel.setCityList(new ArrayList<CityModel>());
				break;
			case "city":
				cityModel = new CityModel();
				cityModel.setName(attributes.getValue(0));
				cityModel.setId(attributes.getValue(1));
				cityModel.setDistrictList(new ArrayList<DistrictModel>());
				break;
			case "district":
				districtModel = new DistrictModel();
				districtModel.setName(attributes.getValue(0));
				districtModel.setId(attributes.getValue(1));
				break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		switch (qName) {
			case "district":
				cityModel.getDistrictList().add(districtModel);
				break;
			case "city":
				provinceModel.getCityList().add(cityModel);
				break;
			case "province":
				provinceList.add(provinceModel);
				break;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
