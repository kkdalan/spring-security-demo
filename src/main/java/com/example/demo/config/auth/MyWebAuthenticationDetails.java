package com.example.demo.config.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = 1L;
	
	private String imageCode;
	private String savedImageCode;

	public String getImageCode() {
		return imageCode;
	}

	public String getSavedImageCode() {
		return savedImageCode;
	}

	public MyWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		this.imageCode = request.getParameter("captcha");
		HttpSession session = request.getSession();
		this.savedImageCode = (String) session.getAttribute("captcha");
		if(!StringUtils.isEmpty(this.savedImageCode)) {
			session.removeAttribute("captcha");
		}
	}

}
