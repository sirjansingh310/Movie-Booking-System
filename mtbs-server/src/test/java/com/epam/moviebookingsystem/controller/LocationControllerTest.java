package com.epam.moviebookingsystem.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.moviebookingsystem.entity.Location;
import com.epam.moviebookingsystem.service.LocationService;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {
	@Autowired 
	private MockMvc mockMvc;
	@MockBean
	private LocationService locationServiceMock;
	private String url = "/location";
	@Test
	void getAllLocationsTest() throws Exception{
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("hyderabad"));
		locations.add(new Location("banglore"));
		locations.add(new Location("chennai"));
		when(locationServiceMock.getAllLocations()).thenReturn(locations);
		mockMvc.perform(get(url))
		.andExpect(status().isOk());
	}
	@Test 
	void getEmptyLocationsTest() throws Exception{
		List<Location> locations = new ArrayList<>();
		when(locationServiceMock.getAllLocations()).thenReturn(locations);
		System.out.println(locationServiceMock.getAllLocations().size());
		mockMvc.perform(get(url))
		.andExpect(status().isNoContent());
	}
	@Test
	void exceptionOccuredInServiceLayerTest() throws Exception{
		when(locationServiceMock.getAllLocations()).thenThrow(new RuntimeException());
		mockMvc.perform(get(url))
		.andExpect(status().isInternalServerError());
	}
}
