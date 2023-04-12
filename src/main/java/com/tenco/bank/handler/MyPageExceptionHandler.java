package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.CustomPageException;
import com.tenco.bank.handler.exception.UnauthenticatedUser;

/**
 * @ControllerAdvice
 * View 렌더링을 위해 ModelAndView
 * 객체를 반환하도록 기본 설정되어 있습니다
 * 예외 page를 리턴 하도록 활용 합니다.   
 */
@ControllerAdvice
public class MyPageExceptionHandler {

//  이 부분을 선언하면 Restfull 를 동작하지 않음 ! 	
//	@ExceptionHandler(Exception.class)
//	public void exception(Exception e) {
//		System.out.println(">>>>> " + e.getClass().getName());
//		System.out.println(">>>>> " + e.getMessage());
//	}
	

}
