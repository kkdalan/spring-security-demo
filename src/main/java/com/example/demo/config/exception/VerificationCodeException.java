package com.example.demo.config.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationCodeException extends AuthenticationException{

	public VerificationCodeException() {
		super("圖形驗證碼驗證失敗");
	}

}
