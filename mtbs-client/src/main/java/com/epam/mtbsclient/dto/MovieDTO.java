package com.epam.mtbsclient.dto;

import java.util.List;

public class MovieDTO {

	private Integer id;
	private String name;
	private List<LocationDTO> locationList;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LocationDTO> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationDTO> locationList) {
		this.locationList = locationList;
	}

	@Override
	public String toString() {
		return "MovieDTO [id=" + id + ", name=" + name + ", locationList=" + locationList + "]";
	}
   
}
