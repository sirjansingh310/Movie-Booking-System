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

import com.epam.moviebookingsystem.entity.Movie;
import com.epam.moviebookingsystem.service.MovieService;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MovieService movieServiceMock;
	private String url = "/movie/hyderabad";
	@Test
	void getAllMoviesTest() throws Exception{
		List<Movie> movies = new ArrayList<>();
		movies.add(new Movie("joker"));
		movies.add(new Movie("spider man"));
		when(movieServiceMock.getMoviesByLocation("hyderabad")).thenReturn(movies);
		mockMvc.perform(get(url))
		.andExpect(status().isOk());
	}
	
	@Test
	void getEmptyMoviesTest() throws Exception{
		List<Movie> movies = new ArrayList<>();
		when(movieServiceMock.getMoviesByLocation("hyderabad")).thenReturn(movies);
		mockMvc.perform(get(url))
		.andExpect(status().isNoContent());
	}
	@Test
	void exceptionOccuredInServiceLayerTest() throws Exception{
		when(movieServiceMock.getMoviesByLocation("hyderabad")).thenThrow(new RuntimeException());
		mockMvc.perform(get(url))
		.andExpect(status().isInternalServerError());
	}
}
