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

import com.epam.mtbsclient.dto.TheaterDTO;
import com.epam.mtbsclient.service.RestClientService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mockpassword", roles = "USER")
class TheaterControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private RestClientService restClientServiceMock;
	private String url = "/selectTheater";

	@Test
	void getAllTheatersTest() throws Exception {
		List<TheaterDTO> theaters = new ArrayList<>();
		theaters.add(new TheaterDTO());
		theaters.add(new TheaterDTO());
		ResponseEntity<List<TheaterDTO>> responseEntity = new ResponseEntity<>(theaters, HttpStatus.OK);
		when(restClientServiceMock.getTheatersList("hyderabad", "joker")).thenReturn(responseEntity);
		mockMvc.perform(get(url)
				.sessionAttr("userLocation", "hyderabad")
				.param("userMovie", "joker"))
				.andExpect(status().isOk())
				.andExpect(view().name("theaters"));
	}

	@Test
	void getEmptyTheatersTest() throws Exception {
		ResponseEntity<List<TheaterDTO>> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		when(restClientServiceMock.getTheatersList("hyderabad", "joker")).thenReturn(responseEntity);
		mockMvc.perform(get(url)
				.param("userMovie", "joker")
				.sessionAttr("userLocation", "hyderabad"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:pageNotFound"));
	}

	@Test
	void getInternalServerErrorTest() throws Exception {
		ResponseEntity<List<TheaterDTO>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(restClientServiceMock.getTheatersList("hyderabad", "joker")).thenReturn(responseEntity);
		mockMvc.perform(get(url)
				.param("userMovie", "joker")
				.sessionAttr("userLocation", "hyderabad"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:error"));
	}
}
