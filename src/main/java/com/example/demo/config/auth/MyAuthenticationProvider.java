package com.example.demo.config.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.config.exception.VerificationCodeException;

@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider{

	public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.setUserDetailsService(userDetailsService);
		this.setPasswordEncoder(passwordEncoder);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) usernamePasswordAuthenticationToken.getDetails();
		String imageCode = details.getImageCode();
		String savedImageCode = details.getSavedImageCode();
		if(StringUtils.isEmpty(imageCode) || StringUtils.isEmpty(savedImageCode)
				|| !imageCode.equals(savedImageCode)) {
			throw new VerificationCodeException();
		}
		super.additionalAuthenticationChecks(userDetails, usernamePasswordAuthenticationToken);
	}

}
