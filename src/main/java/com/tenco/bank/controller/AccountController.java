package com.tenco.bank.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.AccountDepositFormDto;
import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.AccountTransferFormDto;
import com.tenco.bank.dto.AccountWithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.handler.exception.UnauthenticatedUser;
import com.tenco.bank.repository.model.account.Account;
import com.tenco.bank.repository.model.user.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.utils.Define;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private HttpSession session;

	@Autowired
	private AccountService accountService;

	// 계좌 목록 페이지
	@GetMapping({ "/list", "/" })
	public String list(Model model) {

		// 세션 메모리에서 확인 : DB 접근 아님
		if (session.getAttribute(Define.PRINCIPAL) == null) {
			return "redirect:/user/sign-in";
		}
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		// view 쪽으로 값을 내려 주는 기술
		// Model 객체 또는 ModelAndView 객체를 사용한다.
		// 선택은 프로젝트 기술 및 개발자 선호 사항
		model.addAttribute(principal);
		List<Account> accountList = accountService.findUserAccount(principal.getId());
		if (accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("accountList", accountList);
		}
		return "/account/list";
	}

	// 출금페이지
	@GetMapping("/withdraw-form")
	public String withdrawForm() {
		// 인증 확인
		if (session.getAttribute("principal") == null) {
			return "redirect:/user/sign-in";
		}
		return "/account/withdrawForm";
	}

	
	@PostMapping("/withdraw-form")
	public String withdrawProc(AccountWithdrawFormDto accountWithdrawFormDto) {
		// 인증 확인
		if (session.getAttribute(Define.PRINCIPAL) == null) {
			return "redirect:/user/sign-in";
		}

		if (accountWithdrawFormDto.getAmount() == null) {
			throw new CustomRestfullException("amount를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		if (accountWithdrawFormDto.getAmount().longValue() <= 0) {
			throw new CustomRestfullException("출금액이 0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
		if (accountWithdrawFormDto.getWAccountNumber() == null
				|| accountWithdrawFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfullException("계좌번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		if (accountWithdrawFormDto.getWAccountPassword() == null
				|| accountWithdrawFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfullException("계좌비밀번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		accountService.withdrawMoney(accountWithdrawFormDto);
		return "redirect:/account/list";
	}

	// 입금페이지
	@GetMapping("/diposit-form")
	public String dipositForm() {
		return "/account/dipositForm";
	}
	
	/**
	 * 입금 처리 
	 * @param accountDepositFormDto
	 * @return / 
	 */
	@PostMapping("/diposit-proc")
	public String dipositProc(AccountDepositFormDto accountDepositFormDto) {
		if(accountDepositFormDto.getAmount() == null) {
			throw new CustomRestfullException("금액을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		if(accountDepositFormDto.getAmount().longValue() <= 0) {
			throw new CustomRestfullException("입금금액이 0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
		
		if(accountDepositFormDto.getDAccountNumber() == null || accountDepositFormDto.getDAccountNumber().isEmpty() ) {
			throw new CustomRestfullException("계좌번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		accountService.diposit(accountDepositFormDto);
		
		return "redirect:/account/list";
	}

	// 이체 페이지
	@GetMapping("/transfer-form")
	public String transferForm() {
		if(session.getAttribute(Define.PRINCIPAL) == null) {
			return "redirect:/user/sign-in";
		}
		return "/account/transferForm";
	}
	
	/**
	 * 
	 * 1. 인증 검사 
	 * 2. 유효성 검사 
	 * 3. 서비스 로직 호출
	 * 4. 화면 이동  
	 * @param accountTransferFormDto
	 * @return list 화면 이동
	 */
	@PostMapping("/transfer-proc")
	public String transferProc(AccountTransferFormDto accountTransferFormDto) {
		
		if(session.getAttribute(Define.PRINCIPAL) == null) {
			throw new CustomRestfullException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
		}
		
		// 유효성 검사 
		//1. 출금계좌번호 입력 여부 확인
		if(accountTransferFormDto.getWAccountNumber() == null 
				|| accountTransferFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfullException("출금계좌 입력해주세요", HttpStatus.BAD_REQUEST);
		} 
		//2. 입금계좌번호 입력 여부 확인 
		if(accountTransferFormDto.getDAccountNumber() == null 
				|| accountTransferFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfullException("입금계좌 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		//3. 출금계좌 비밀번호 입력 여부 확인 
		if(accountTransferFormDto.getWAccountPassword() == null 
				|| accountTransferFormDto.getWAccountPassword().isEmpty()){
			throw new CustomRestfullException("출금계좌 비밀번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		//4. 출금계좌 입금계좌 동일 여부
		if(accountTransferFormDto.getDAccountNumber() == accountTransferFormDto.getWAccountNumber()){
			throw new CustomRestfullException("출금계좌와 입금계좌가 동일 합니다", HttpStatus.BAD_REQUEST);
		}
		//5. 이체금액 0원 확인 
		if(accountTransferFormDto.getAmount() <= 0){
			throw new CustomRestfullException("이체 금액이 0원 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		
		// todo 코드 추가 예정
		// 서비스 호출 
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		accountService.transfer(accountTransferFormDto, principal.getId());
		return "/account/list";
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
		if (session.getAttribute(Define.PRINCIPAL) == null) {
			throw new UnauthenticatedUser("인증되지 않은 사용자 입니다.", HttpStatus.UNAUTHORIZED);
		}

		return "/account/saveForm";
	}

	/**
	 * 계좌 생성 처리
	 */
	@PostMapping("/save-form")
	public String saveFormProc(AccountSaveFormDto accountSaveFormDto) {
		if (session.getAttribute(Define.PRINCIPAL) == null) {
			throw new UnauthenticatedUser("인증되지 않은 사용자 입니다.", HttpStatus.UNAUTHORIZED);
		}
		// AccountSaveFormDto 유효성 검사 생략.. 계좌 생성 규칙 고민
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		accountService.createAccount(accountSaveFormDto, principal.getId());
		return "redirect:/account/list";
	}
}
