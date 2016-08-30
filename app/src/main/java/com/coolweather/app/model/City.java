package com.coolweather.app.model;

public class City {

	private int id;

	private String cityName;

	private String cityCode;

	private int provinceId;

	private String temp1;

	private String temp2;

	private String weatherDesp;

	private String publishTime;

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public void setWeatherDesp(String weatherDesp) {
		this.weatherDesp = weatherDesp;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getTemp1() {
		return temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public String getWeatherDesp() {
		return weatherDesp;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

}
