package com.tenco.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.repository.model.user.User;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpSession session;
	
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
	
	// 계좌 생성 페이지
	@GetMapping("/save-form")
	public String saveForm() {
		return "/account/saveForm";
	}

}



