package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 커스텀 예외 클래스 만들어 보기 
 */
@Getter
public class CustomRestfullException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
	public CustomRestfullException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
