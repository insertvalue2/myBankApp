package com.tenco.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.UnauthenticatedUser;
import com.tenco.bank.repository.model.user.User;
import com.tenco.bank.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AccountService accountService;
	
	// 계좌 목록 페이지
	@GetMapping("/list")
	public String list(Model model) {
		
		// 세션 메모리에서 확인 : DB 접근 아님
		if(session.getAttribute("principal") == null) {
			return "redirect:/user/sign-in"; 
		}
		User principal = (User)session.getAttribute("principal");
		// view 쪽으로 값을 내려 주는 기술 
		// Model 객체 또는 ModelAndView 객체를 사용한다. 
		// 선택은 프로젝트 기술 및 개발자 선호 사항
		model.addAttribute(principal);
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
	
	// 계좌 생성 페이지 이동
	// MIME TYPE 
	@GetMapping("/save-form")
	public String saveForm(AccountSaveFormDto accountSaveFormDto) {
		// 로그인 되어 있지 않으면 signIn 페이지로 이동 처리 
		if(session.getAttribute("principal") == null) {
			throw new UnauthenticatedUser("인증되지 않은 사용자 입니다.", HttpStatus.UNAUTHORIZED);
		} 
		
		return "/account/saveForm";
	}
	
	/**
	 * 계좌 생성 처리 
	 */
	@PostMapping("/save-form")
	public String saveFormProc(AccountSaveFormDto accountSaveFormDto) {
		if(session.getAttribute("principal") == null) {
			throw new UnauthenticatedUser("인증되지 않은 사용자 입니다.", HttpStatus.UNAUTHORIZED);
		} 
		// AccountSaveFormDto 유효성 검사 생략..
		User principal = (User)session.getAttribute("principal"); 
		accountService.createAccount(accountSaveFormDto, principal.getId());
		System.out.println("11111");
		return "redirect:/account/list";
	}
}



