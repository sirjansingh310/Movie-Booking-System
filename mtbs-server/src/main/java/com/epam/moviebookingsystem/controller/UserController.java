package com.epam.moviebookingsystem.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.moviebookingsystem.entity.User;
import com.epam.moviebookingsystem.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	private Logger logger = LogManager.getLogger(UserController.class);

	@PostMapping("user")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		ResponseEntity<String> responseEntity;
		try {
			userService.createUser(user);
			responseEntity = new ResponseEntity<>("User created!", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping("user/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		ResponseEntity<User> responseEntity;
		Optional<User> optionalUser;
		try {
			optionalUser = userService.getUser(username);
			if (optionalUser.isPresent()) {
				responseEntity = new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("user/{username}/{email}")
	public ResponseEntity<String> checkUserExists(@PathVariable String username, @PathVariable String email){
		ResponseEntity<String> responseEntity;
		try {
			if (userService.isUserCreated(username)) {
				responseEntity = new ResponseEntity<>("Username already exists",HttpStatus.CONFLICT);
			} 
			else if(userService.isEmailPresent(email)) {
				responseEntity = new ResponseEntity<>("This email is already taken", HttpStatus.CONFLICT);
			}
			else {
				responseEntity = new ResponseEntity<>("No user found", HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage());
		}
		return responseEntity;
	}
}
