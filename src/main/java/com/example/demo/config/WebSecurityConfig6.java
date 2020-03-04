package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@EnableWebSecurity
public class WebSecurityConfig6 extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/api/**").hasAuthority("ROLE_ADMIN")
				.antMatchers("/user/api/**").hasRole("USER")
				.antMatchers("/app/api/**", "/captcha.jpg").permitAll()
				.anyRequest().authenticated()
				.and().csrf().disable()
				.formLogin()
				.authenticationDetailsSource(authenticationDetailsSource)
				.loginPage("/myLogin.html")
				.loginProcessingUrl("/auth/form").permitAll()
				.failureHandler(authenticationFailureHandler)
				.and().csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
//		return NoOpPasswordEncoder.getInstance();
	}
	
//	public static void main(String[] args) {
//		String rawPassword = "blurooo";
//		System.out.println(new BCryptPasswordEncoder(12).encode(rawPassword));
//	}
	
}
