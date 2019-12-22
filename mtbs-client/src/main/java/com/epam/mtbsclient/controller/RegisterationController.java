package com.epam.mtbsclient.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.mtbsclient.dto.UserDTO;
import com.epam.mtbsclient.exception.InternalServerErrorException;
import com.epam.mtbsclient.exception.UserAlreadyExistsException;
import com.epam.mtbsclient.service.EmailVerificationService;
import com.epam.mtbsclient.service.RestClientService;

@Controller
public class RegisterationController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RestClientService restClientService;
	private Logger logger = LogManager.getLogger(RegisterationController.class);
	@Autowired
	private EmailVerificationService emailVerificationService;

	@GetMapping("/login")
	public String checkUserLoggedInForLoginPage(Principal principal) {
		String viewName = "";
		if (principal == null)
			viewName = "/login";
		else
			viewName = "redirect:selectLocation";
		return viewName;
	}

	@GetMapping(value = { "/", "register", "verify" })
	public String checkUserLoggedInForRegisterationPage(Principal principal) {
		String viewName;
		if (principal == null) {
			viewName = "index";
		} else {
			viewName = "redirect:selectLocation";
		}
		return viewName;
	}

	@PostMapping("register")
	public ModelAndView register(@RequestParam String username, @RequestParam String email,
			@RequestParam String password, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		String redirectToError = "redirect:error";
		try {
			UserDTO user = new UserDTO();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole("USER");
			ResponseEntity<String> responseEntity = restClientService.checkUserExists(username, email);
			if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
				modelAndView.setViewName("sendOTP");
				httpSession.setAttribute("userForVerification", user);
				emailVerificationService.sendOTP(email);
			} else if (responseEntity.getStatusCode() == HttpStatus.CONFLICT) {
				throw new UserAlreadyExistsException(responseEntity.getBody());
			} else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
				throw new InternalServerErrorException();
			}
		} catch (UserAlreadyExistsException e) {
			modelAndView.setViewName("index");
			modelAndView.addObject("UserAlreadyExistsMessage", e.getMessage());
			logger.error(e.getMessage());
		} catch (InternalServerErrorException e) {
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		} catch (Exception e) {
			modelAndView.setViewName(redirectToError);
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/resendOTP")
	public String resendOTP(HttpSession httpSession, Principal principal) {
		String viewName = "sendOTP";
		if (principal != null)
			viewName = "redirect:selectLocation";
		else {
			UserDTO user = (UserDTO) httpSession.getAttribute("userForVerification");
			emailVerificationService.sendOTP(user.getEmail());
		}
		return viewName;
	}

	@PostMapping("/verify")
	public ModelAndView verifyOTP(@RequestParam Integer OTP, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		UserDTO user = (UserDTO) httpSession.getAttribute("userForVerification");
		if (emailVerificationService.isEmailVerified(user.getEmail(), OTP)) {
			try {
				ResponseEntity<String> responseEntity = restClientService.registerUser(user);
				if (responseEntity.getStatusCode() == HttpStatus.CREATED)
					modelAndView.setViewName("register-success");
				else {
					throw new InternalServerErrorException();
				}
			} catch (InternalServerErrorException e) {
				logger.error(e.getMessage());
				modelAndView.setViewName("redirect:error");
			}
		} else {
			modelAndView.setViewName("sendOTP");
			modelAndView.addObject("invalidOTPMessage", "INVALID OTP ENTERED");
		}
		return modelAndView;
	}
}