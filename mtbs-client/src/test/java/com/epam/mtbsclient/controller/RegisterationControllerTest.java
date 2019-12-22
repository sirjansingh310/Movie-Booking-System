package com.epam.mtbsclient.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.mtbsclient.dto.UserDTO;
import com.epam.mtbsclient.service.EmailVerificationService;
import com.epam.mtbsclient.service.RestClientService;
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mockpassword", roles = "USER")
class RegisterationControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private RestClientService restClientServiceMock;
	@MockBean
	private Principal principal;
	@MockBean
	private EmailVerificationService emailVerificationServiceMock;
	@Test
	void checkUserLoggedInForLoginPageTest() throws Exception{
		mockMvc.perform(get("/login").principal(principal))
		.andExpect(view().name("redirect:selectLocation"));
	}
	@Test
	void checkUserLoggedInForRegisterationPageTest() throws Exception{
		mockMvc.perform(get("/").principal(principal))
		.andExpect(view().name("redirect:selectLocation"));
	}
	@Test
	void newUserTest() throws Exception{
		ResponseEntity<String> responseEntity = new ResponseEntity<>("No user", HttpStatus.NO_CONTENT);
		when(restClientServiceMock.checkUserExists("newmockuser", "mock@mock.com")).thenReturn(responseEntity);
		doNothing().when(emailVerificationServiceMock).sendOTP("mock@mock.com");
		mockMvc.perform(post("/register").param("username","newmockuser").param("email", "mock@mock.com").param("password", "password"))
		.andExpect(view().name("sendOTP"));
	}
	@Test
	void verifyCorrectOTPandRegisterUserTest() throws Exception{
		UserDTO user = new UserDTO();
		user.setEmail("mock@mock.com");
		ResponseEntity<String> responseEntity = new ResponseEntity<>("User created!",HttpStatus.CREATED);
		when(restClientServiceMock.registerUser(user)).thenReturn(responseEntity);
		when(emailVerificationServiceMock.isEmailVerified("mock@mock.com", 1234)).thenReturn(true);
		mockMvc.perform(post("/verify").param("OTP", "1234").sessionAttr("userForVerification", user)).andExpect(status().isOk())
		.andExpect(view().name("register-success"));
	}
	@Test
	void verifyWrongOTPandRegisterUserTest() throws Exception{
		UserDTO user = new UserDTO();
		user.setEmail("mock@mock.com");
		ResponseEntity<String> responseEntity = new ResponseEntity<>("User created!",HttpStatus.CREATED);
		when(restClientServiceMock.registerUser(user)).thenReturn(responseEntity);
		when(emailVerificationServiceMock.isEmailVerified("mock@mock.com", 1234)).thenReturn(false);
		mockMvc.perform(post("/verify").param("OTP", "1234").sessionAttr("userForVerification", user)).andExpect(status().isOk())
		.andExpect(view().name("sendOTP"));
	}
	@Test
	void registerUserTestForExistingUser() throws Exception{
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Username exists", HttpStatus.CONFLICT);
		when(restClientServiceMock.checkUserExists("newmockuser","newmockuser@mock.com")).thenReturn(responseEntity);
		mockMvc.perform(post("/register").param("username","newmockuser").param("email", "newmockuser@mock.com").param("password", "password"))
		.andExpect(view().name("index"))
		.andExpect(status().isOk());
	}
	
	@Test
	void registerUserTestForException() throws Exception{
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Username exists", HttpStatus.INTERNAL_SERVER_ERROR);
		when(restClientServiceMock.registerUser((UserDTO) any(UserDTO.class))).thenReturn(responseEntity);
		mockMvc.perform(post("/register").param("username","newmockuser").param("email", "newmockuser@mock.com").param("password", "password"))
		.andExpect(view().name("redirect:error"))
		.andExpect(status().is3xxRedirection());
	}
}
