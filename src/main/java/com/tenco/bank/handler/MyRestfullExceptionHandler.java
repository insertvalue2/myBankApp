package com.tenco.bank.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.bank.handler.exception.CustomRestfullException;

@RestControllerAdvice // IoC 대상 + aop 
public class MyRestfullExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
	}
	
	// 자바 스크립트로 응답 처리 
	@ExceptionHandler(CustomRestfullException.class)
	public String basicException(CustomRestfullException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		// 반드시 ; 콜론 붙어서 사용 하자! 
		sb.append("alert('" + e.getMessage() + "');");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}
}
