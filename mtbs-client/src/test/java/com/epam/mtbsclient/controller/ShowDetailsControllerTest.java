package com.epam.mtbsclient.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.epam.mtbsclient.dto.ShowDetailsDTO;
import com.epam.mtbsclient.service.RestClientService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mockpassword", roles = "USER")
class ShowDetailsControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private RestClientService restClientServiceMock;
	private String url = "/showDetails";

	@Test
	void getAllShowDetailsTest() throws Exception {
		List<ShowDetailsDTO> showDetailsList = new ArrayList<>();
		showDetailsList.add(new ShowDetailsDTO());
		ResponseEntity<List<ShowDetailsDTO>> responseEntity = new ResponseEntity<>(showDetailsList, HttpStatus.OK);
		when(restClientServiceMock.getShowDetails(1, "joker")).thenReturn(responseEntity);
		mockMvc.perform(post(url).
				param("userTheaterId", "1").
				sessionAttr("userMovie", "joker"))
				.andExpect(status().isOk())
				.andExpect(view().name("showDetails"));
	}

	@Test
	void getEmptyShowDetailsTest() throws Exception {
		ResponseEntity<List<ShowDetailsDTO>> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		when(restClientServiceMock.getShowDetails(1, "joker")).thenReturn(responseEntity);
		mockMvc.perform(post(url).
				param("userTheaterId", "1").
				sessionAttr("userMovie", "joker"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:pageNotFound"));
	}

	@Test
	void getInternalServerErrorTest() throws Exception {
		ResponseEntity<List<ShowDetailsDTO>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(restClientServiceMock.getShowDetails(1, "joker")).thenReturn(responseEntity);
		mockMvc.perform(post(url)
				.param("userTheaterId", "1")
				.sessionAttr("userMovie", "joker"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:error"));
	}
}
