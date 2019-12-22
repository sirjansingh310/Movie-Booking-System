package com.epam.moviebookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.repository.LocationRepository;

class LocationServiceTest {

	@Mock
	private LocationRepository locationRepositoryMock;
	
	@InjectMocks
	private LocationService locationService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void getAllLocationsTest() {
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("hyderabad"));
		locations.add(new Location("chennai"));
		locations.add(new Location("banglore"));
		when(locationRepositoryMock.findAll()).thenReturn(locations);
		assertEquals(locationService.getAllLocations(), locations);
	}
}
