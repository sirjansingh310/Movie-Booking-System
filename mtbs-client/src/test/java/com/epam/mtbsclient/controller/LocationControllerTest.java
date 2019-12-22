package com.epam.mtbsclient.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.mtbsclient.dto.LocationDTO;
import com.epam.mtbsclient.service.RestClientService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mockpassword", roles = "USER")
class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc; 
	@MockBean
	private RestClientService restClientServiceMock;
	private String url = "/selectLocation";
	@Test
	void getAllLocationsTest() throws Exception{
		List<LocationDTO> locations = new ArrayList<>();
		locations.add(new LocationDTO());
		locations.add(new LocationDTO());
		ResponseEntity<List<LocationDTO>> responseEntity = new ResponseEntity<>(locations, HttpStatus.OK);
		when(restClientServiceMock.getAllLocations()).thenReturn(responseEntity);
		mockMvc.perform(get(url))
		.andExpect(status().isOk())
		.andExpect(view().name("locations"));
	}
	@Test
	void getInternalServerErrorTest() throws Exception{
		ResponseEntity<List<LocationDTO>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(restClientServiceMock.getAllLocations()).thenReturn(responseEntity);
		mockMvc.perform(get(url))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:error"));
	}
}
