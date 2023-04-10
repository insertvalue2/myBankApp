package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController1 {
	
	@GetMapping("/main")
	public String mainTest() {
		//   yml 파일 설정
		//   prefix: /WEB-INF/view/
	    //   suffix: .jsp 
		
		// ViewResolver 동작 
		// /WEB-INF/view/layout/main.jsp
		System.out.println("코드 실행 확인");
		return "/layout/main";
	}
	
	
	
}
