package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomPageException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
	public CustomPageException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
