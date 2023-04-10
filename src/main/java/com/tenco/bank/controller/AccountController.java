package com.tenco.bank.controller;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.handler.exception.CustomPageException;
import com.tenco.bank.handler.exception.CustomRestfullException;

@Controller
@RequestMapping("/account")
public class AccountController {

	// 계좌 목록 페이지
	@GetMapping("/list")
	public String list() {
		// 에러 테스트 
		// 만약 인증되지 않는 사용자 라면 예외로 던지기
		// 1. 데이터 기반 예외처리  
		// 2. page 기반 예외 처리 
		// throw new CustomRestfullException("인증되지 않았습니다.",HttpStatus.UNAUTHORIZED);
		 throw new CustomPageException("찾을 수 없는 페이지 입니다.", HttpStatus.NOT_FOUND);
		// return "/account/list";
	}

	// 출금페이지
	@GetMapping("/withdraw-form")
	public String withdrawForm() {
		
		return "/account/withdrawForm";
	}

	// 입금페이지
	@GetMapping("/diposit-form")
	public String dipositForm() {
		return "/account/dipositForm";
	}
	
	// 이체페이지
	@GetMapping("/transfer-form")
	public String transferForm() {
		return "/account/transferForm";
	}
	
	// 계좌 상세보기 페이지
	@GetMapping("/detail")
	public String detail() {
		return "/account/detail";
	}
	
	// 계좌 생성 페이지
	@GetMapping("/save-form")
	public String saveForm() {
		return "/account/saveForm";
	}

}



