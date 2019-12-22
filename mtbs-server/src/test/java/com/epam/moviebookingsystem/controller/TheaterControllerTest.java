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

import com.epam.moviebookingsystem.entity.Theater;
import com.epam.moviebookingsystem.service.TheaterService;
@SpringBootTest
@AutoConfigureMockMvc
class TheaterControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TheaterService theaterServiceMock;
	private String url = "/theater/hyderabad/joker";
	@Test
	void getAllTheatersTest() throws Exception{
		List<Theater> theaters = new ArrayList<>();
		theaters.add(new Theater("pvr"));
		theaters.add(new Theater("prasads imax"));
		when(theaterServiceMock.getTheatersList("hyderabad", "joker")).thenReturn(theaters);
		mockMvc.perform(get(url))
		.andExpect(status().isOk());
	}
	
	@Test
	void getEmptyTheatersTest() throws Exception{
		List<Theater> theaters = new ArrayList<>();
		when(theaterServiceMock.getTheatersList("hyderabad", "joker")).thenReturn(theaters);
		mockMvc.perform(get(url))
		.andExpect(status().isNoContent());
	}
	@Test
	void exceptionOccuredInServiceLayerTest() throws Exception{
		when(theaterServiceMock.getTheatersList("hyderabad", "joker")).thenThrow(new RuntimeException());
		mockMvc.perform(get(url))
		.andExpect(status().isInternalServerError());
	}

}
