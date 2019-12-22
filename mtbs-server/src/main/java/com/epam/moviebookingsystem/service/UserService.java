package com.epam.moviebookingsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.repository.UserRepository;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	public Optional<User> getUser(String username) {
		return userRepository.findById(username);
	}
	public boolean isUserCreated(String username) {
		return userRepository.findById(username).isPresent();
	}
	public void createUser(User user) {
		userRepository.save(user);
	}
	public boolean isEmailPresent(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}
