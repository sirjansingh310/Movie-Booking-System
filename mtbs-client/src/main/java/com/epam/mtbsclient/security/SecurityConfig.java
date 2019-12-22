package com.epam.mtbsclient.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

	@Override 
	public void configure(HttpSecurity http) throws Exception{
		 http.csrf().disable().authorizeRequests()
		  .antMatchers("/","/register","/error","/login","/verify","/sendOTP", "/resendOTP").permitAll()
		  .antMatchers("/admin/*").hasAuthority("ADMIN")
	      .anyRequest().authenticated()
	      .and().formLogin().loginPage("/login")
		  .defaultSuccessUrl("/selectLocation");
	}
}
