package com.epam.mtbsclient.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mockpassword", roles = "USER")
class ErrorControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void errorViewTest() throws Exception{
		mockMvc.perform(get("/error"))
				.andExpect(view().name("error"))
				.andExpect(status().isOk());
	}
	@Test
	void pageNotFoundViewTest() throws Exception{
		mockMvc.perform(get("/pageNotFound"))
				.andExpect(view().name("handleNoContent"))
				.andExpect(status().isOk());
	}
}
