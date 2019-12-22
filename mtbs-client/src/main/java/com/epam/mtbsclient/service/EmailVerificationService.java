package com.epam.mtbsclient.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
	@Autowired
	private JavaMailSender javaMailSender;
	private Map<String, Integer> generatedOTPs = new HashMap<>();
	private Random random = new Random();
	public void sendOTP(String email) {
		int otp = random.nextInt(10000);
		if(otp < 1000)
			otp += 1000;
		StringBuilder stringBuilder = new StringBuilder("Here is your verification OTP\n");
		stringBuilder.append(otp);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setSubject("Email verification for bookmymovie.com");
		simpleMailMessage.setText(stringBuilder.toString());
		generatedOTPs.put(email, otp);
		simpleMailMessage.setTo(email);
		javaMailSender.send(simpleMailMessage);
	}
	public boolean isEmailVerified(String email, int inputOTP) {
		return generatedOTPs.get(email) == inputOTP;
	}
}
