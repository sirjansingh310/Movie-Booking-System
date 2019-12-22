package com.epam.moviebookingsystem.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userServiceMock;
	@Test
	void getUserTest() throws Exception {
		User user = new User();
		user.setUsername("mock");
		Optional<User> optionalUser = Optional.of(user);
		when(userServiceMock.getUser("mock")).thenReturn(optionalUser);
		mockMvc.perform(get("/user/mock")).andExpect(status().isOk());
	}
	@Test
	void getUnknownUserTest() throws Exception {
		Optional<User> optionalUser = Optional.empty();
		when(userServiceMock.getUser("mock")).thenReturn(optionalUser);
		mockMvc.perform(get("/user/mock")).andExpect(status().isNoContent());
	}

	@Test
	void getUserTestWithException() throws Exception {
		when(userServiceMock.getUser("mock")).thenThrow(new RuntimeException());
		mockMvc.perform(get("/user/mock")).andExpect(status().isInternalServerError());
	}
	@Test
	void createNewUserTest() throws Exception{
		User user = new User();
		user.setUsername("mock");
		user.setPassword("mock");
		user.setEmail("mock@mock.com");
		user.setRole("USER");
		when(userServiceMock.isUserCreated("mock")).thenReturn(false);
		when(userServiceMock.isEmailPresent("mock@mock.com")).thenReturn(false);
		doNothing().when(userServiceMock).createUser(user);
		String jsonUser = "{\"username\":\"mock\",\"password\":\"mock\",\"role\":\"USER\",\"email\":\"mock@mock.com\"}";
		mockMvc.perform(post("/user").content(jsonUser).contentType("application/json")).andExpect(status().isCreated());
	}

	@Test
	void checkIfEmailExistsTest() throws Exception{
		String email = "mock@mock.com";
		String username = "mock";
		when(userServiceMock.isEmailPresent(email)).thenReturn(true);
		when(userServiceMock.isUserCreated(username)).thenReturn(false);
		mockMvc.perform(get("/user/mock/mock@mock.com"))
		.andExpect(status().isConflict());
	}
	@Test
	void checkIfUsernameExistsTest() throws Exception{
		String email = "mock@mock.com";
		String username = "mock";
		when(userServiceMock.isEmailPresent(email)).thenReturn(false);
		when(userServiceMock.isUserCreated(username)).thenReturn(true);
		mockMvc.perform(get("/user/mock/mock@mock.com"))
		.andExpect(status().isConflict());
	}
	
	
}
