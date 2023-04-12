package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

public class UnauthenticatedUser extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
	public UnauthenticatedUser(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
