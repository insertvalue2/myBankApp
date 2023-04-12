package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.AccountWithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.interfaces.HistoryRepository;
import com.tenco.bank.repository.model.account.Account;
import com.tenco.bank.repository.model.history.History;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
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
	
	/**
	 * 출금 처리 
	 * @param accountWithdrawFormDto
	 * @return Account id
	 * 처리 해야 할 일  
	  	 1. 계좌 존재 여부 확인 - 코드 생성,MyBatis생성(리턴 값은 Account)  
		 2. 계좌 비번 확인 -> 1에 확인 된 Account 로 처리 가능 
		 3. 잔액 확인 -> 1번에 확인 된 Account 로 처리 가능 
		 4. 출금 처리 -> update query 처리 
		 5. 거래 내역 처리 -> insert query 처리 
		 6. 해당 계좌 id 값 리턴 --> 계좌 상세 보기 페이지로 이동 처리하기 위함
	 */
	@Transactional
	public int withdrawMoney(AccountWithdrawFormDto accountWithdrawFormDto) {
		
		// 1 
		Account accountEntity = accountRepository
				.findByNumber(accountWithdrawFormDto.getWAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfullException("해당 계좌 없음", HttpStatus.BAD_REQUEST);
		}
		// 2 
		if(!accountEntity.getPassword().equals(accountWithdrawFormDto.getWAccountPassword()) ) {
			throw new CustomRestfullException("출금 계좌 비번 오류", HttpStatus.BAD_REQUEST);
		}
		// 3 
		if(accountEntity.getBalance() < accountWithdrawFormDto.getAmount()) {
			throw new CustomRestfullException("잔액 부족", HttpStatus.BAD_REQUEST);
		}
		// 4 
		accountEntity.setBalance(accountEntity.getBalance() - accountWithdrawFormDto.getAmount());
		int resultRow = accountRepository.updateById(accountEntity);
		
		// 5 
		if(resultRow == 1) {
			History history = new History();
			history.setAmount(accountWithdrawFormDto.getAmount());
			history.setWAccountId(accountEntity.getId());
			history.setDAccountId(null);
			history.setWBalance(accountEntity.getBalance());
			history.setDBalance(null);
			historyRepository.insert(history);
		}
		// 6
		return accountEntity.getId(); 
	}
	
}
