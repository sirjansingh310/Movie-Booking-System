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

import com.epam.moviebookingsystem.entity.ShowDetails;
import com.epam.moviebookingsystem.service.ShowDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
class ShowDetailsControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ShowDetailsService showDetailsService;
	private String url = "/showdetails/joker";
	@Test
	void getShowDetailsListTest() throws Exception{
		List<ShowDetails> showDetailsList = new ArrayList<>();
		showDetailsList.add(new ShowDetails());
		when(showDetailsService.getShowDetailsList(1, "joker")).thenReturn(showDetailsList);
		mockMvc.perform(get(url)
				.param("theaterId", "1"))
		.andExpect(status().isOk());
	}
	
	@Test
	void getEmptyShowDetailsTest() throws Exception{
		List<ShowDetails> showDetailsList = new ArrayList<>();
		when(showDetailsService.getShowDetailsList(1, "joker")).thenReturn(showDetailsList);
		mockMvc.perform(get(url)
				.param("theaterId", "1"))
		.andExpect(status().isNoContent());
	}
	@Test
	void exceptionOccuredInServiceLayerTest() throws Exception{
		when(showDetailsService.getShowDetailsList(1, "joker")).thenThrow(new RuntimeException());
		mockMvc.perform(get(url)
				.param("theaterId", "1"))
		.andExpect(status().isInternalServerError());
	}	
}
