package com.epam.mtbsclient.dto;

public class TheaterDTO {
	private Integer id;
	private String name;
	private LocationDTO location;
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
	public LocationDTO getLocation() {
		return location;
	}
	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "TheaterDTO [id=" + id + ", name=" + name + ", location=" + location + "]";
	}
}
