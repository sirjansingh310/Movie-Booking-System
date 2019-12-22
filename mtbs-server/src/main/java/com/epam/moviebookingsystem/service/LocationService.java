package com.epam.moviebookingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Transactional(readOnly = true)
	public List<Location> getAllLocations() {
		return (List<Location>) locationRepository.findAll();
	}
	@Transactional
	public void addLocation(String cityName) {
		Location location = new Location();
		location.setCityName(cityName);
		locationRepository.save(location);
	}
	public void updateLocationById(Integer locationId, String newCityName) {
		Location location = new Location();
		location.setId(locationId);
		location.setCityName(newCityName);
		locationRepository.save(location);
	}
	public void deleteLocation(Integer locationId) {
		Location location = new Location();
		location.setId(locationId);
		locationRepository.delete(location);
	}
}
