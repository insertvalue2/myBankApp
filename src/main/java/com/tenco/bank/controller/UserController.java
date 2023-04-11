package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignUpDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
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

}
