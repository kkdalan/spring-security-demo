package com.example.demo.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.config.exception.VerificationCodeException;

@Component
public class VerificationCodeFilter extends OncePerRequestFilter{

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if(!"/auth/form".equals(request.getRequestURI())) {
			filterChain.doFilter(request, response);
		}else {
			try {
				verificationCode(request);
				filterChain.doFilter(request, response);
			}catch(VerificationCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
			}
		}
	}
	
	public void verificationCode(HttpServletRequest request) {
		String requestCode = request.getParameter("captcha");
		HttpSession session = request.getSession();
		String savedCode = (String) session.getAttribute("captcha");
		if(!StringUtils.isEmpty(savedCode)) {
			session.removeAttribute("captcha");
		}
		if (StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(savedCode) 
				|| !requestCode.equals(savedCode)) {
			throw new VerificationCodeException();
		}
		
	}

}
