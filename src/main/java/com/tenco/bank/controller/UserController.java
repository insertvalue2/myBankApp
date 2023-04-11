package com.tenco.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInDto;
import com.tenco.bank.dto.SignUpDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.repository.model.user.User;
import com.tenco.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;

	@GetMapping("/sign-up")
	public String signUp() {
		return "/user/signUp";
	}
	
	@GetMapping("/sign-in")
	public String signIn() {
		return "/user/signIn";
	}

	// @RequestBody 를 선언하지 않으면 application/x-www-form-urlencoded 타입
	@PostMapping("/sign-up")
	public String join(SignUpDto signUpDto) { // DTO로 받는 것이 좋다.

		// 1. 유효성 검사 (이것보다 우선되는 것이 인증 검사이다)
		if (signUpDto.getUsername() == null || signUpDto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		if (signUpDto.getPassword() == null || signUpDto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		if (signUpDto.getFullname() == null || signUpDto.getFullname().isEmpty()) {
			throw new CustomRestfullException("fullname을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		userService.signUp(signUpDto);
		return "redirect:/user/sign-in"; // URL(path)
	}

	// 생각해보기 : 자원에 요청은 일반적으로 GET Mapping 처리 이다.
	// 로그인 처리도 결과적으로 DB 데이터를 요청 하는 로직이지만
	// GET 방식은 브라우저에 히스토리에 남겨지기 때문에 예외 적으로
	// POST 방식으로 처리 하는 것이 안전하다.
	@PostMapping("/sign-in")
	public String login(SignInDto signInDto) {

		if (signInDto.getUsername() == null || signInDto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		if (signInDto.getPassword() == null || signInDto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		User principal = userService.singIn(signInDto);
		// 세션 메모리 영역에 object 타입으로 저장 
		session.setAttribute("principal", principal);
		return "redirect:/account/list"; 
	}

}
