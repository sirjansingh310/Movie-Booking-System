package com.epam.moviebookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.repository.UserRepository;

class UserServiceTest {
	@Mock
	private UserRepository userRepoMock;
	@InjectMocks
	private UserService userService;
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void getUserTest() {
		User user = new User();
		Optional<User> optionalUser = Optional.of(user);
		when(userRepoMock.findById("mock")).thenReturn(optionalUser);
		assertEquals(userService.getUser("mock"), optionalUser);
	}
	@Test
	void isEmailPresentTest() {
		User user = new User();
		user.setEmail("mock@mock.com");
		Optional<User> optionalUser = Optional.of(user);
		when(userRepoMock.findByEmail("mock@mock.com")).thenReturn(optionalUser);
		assertTrue(userService.isEmailPresent("mock@mock.com"));
	}

}
