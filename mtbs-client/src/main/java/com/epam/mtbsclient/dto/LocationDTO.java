package com.epam.mtbsclient.dto;

public class LocationDTO {
	private Integer id;
	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LocationDTO [id=" + id + ", cityName=" + cityName + "]";
	}
}
