package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.model.account.Account;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Transactional // 메서드 시작시 트랜잭션 시작, 완료시 -> commit 
	public void createAccount(AccountSaveFormDto accountSaveFormDto, int principalId) {
		Account account = accountSaveFormDto.toModel(principalId);
		int resultRow =  accountRepository.insert(account);
		if(resultRow != 1) {
			throw new CustomRestfullException("계좌 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// SELECT 쿼리가 간단한 경우에는 @Transactional을 사용하지 않아도 되지만, 복잡한 경우에는 사용하는 것이 좋습니다.
	@Transactional  
	public List<Account> findUserAccount(Integer principalId) {
		List<Account> accountEntityList = accountRepository.findByUserId(principalId);
		return accountEntityList;
	}
	
}
