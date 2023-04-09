package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

	// 계좌 목록 페이지
	@GetMapping("/list")
	public String list() {
		return "/account/list";
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



