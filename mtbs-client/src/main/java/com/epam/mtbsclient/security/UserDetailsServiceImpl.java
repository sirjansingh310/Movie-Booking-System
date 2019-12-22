package com.epam.mtbsclient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.mtbsclient.dto.UserDTO;
import com.epam.mtbsclient.service.RestClientService;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private RestClientService restClient;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ResponseEntity<UserDTO> responseEntity = restClient.getUser(username);
		UserDetails userDetails = new UserDetailsImpl();
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			UserDTO user = responseEntity.getBody();
			userDetails = new UserDetailsImpl(user);
		}
		else if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
			throw new UsernameNotFoundException("No user found with username ".concat(username));
		}
		return userDetails;
	}
}
