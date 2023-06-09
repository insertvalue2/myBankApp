package com.tenco.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.AccountDepositFormDto;
import com.tenco.bank.dto.AccountSaveFormDto;
import com.tenco.bank.dto.AccountTransferFormDto;
import com.tenco.bank.dto.AccountWithdrawFormDto;
import com.tenco.bank.dto.response.HistoryResponseDto;
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
		int resultRow = accountRepository.insert(account);
		if (resultRow != 1) {
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
	 * 
	 * @param accountWithdrawFormDto
	 * @return Account id 처리 해야 할 일 1. 계좌 존재 여부 확인 - 코드 생성,MyBatis생성(리턴 값은 Account)
	 *         2. 계좌 비번 확인 -> 1에 확인 된 Account 로 처리 가능 3. 잔액 확인 -> 1번에 확인 된 Account 로
	 *         처리 가능 4. 출금 처리 -> update query 처리 5. 거래 내역 처리 -> insert query 처리 6.
	 *         해당 계좌 id 값 리턴 --> 계좌 상세 보기 페이지로 이동 처리하기 위함
	 */
	@Transactional
	public int withdrawMoney(AccountWithdrawFormDto accountWithdrawFormDto) {

		// 1
		Account accountEntity = accountRepository.findByNumber(accountWithdrawFormDto.getWAccountNumber());
		if (accountEntity == null) {
			throw new CustomRestfullException("해당 계좌 없음", HttpStatus.BAD_REQUEST);
		}
		// 2
		if (!accountEntity.getPassword().equals(accountWithdrawFormDto.getWAccountPassword())) {
			throw new CustomRestfullException("출금 계좌 비번 오류", HttpStatus.BAD_REQUEST);
		}
		// 3
		if (accountEntity.getBalance() < accountWithdrawFormDto.getAmount()) {
			throw new CustomRestfullException("잔액 부족", HttpStatus.BAD_REQUEST);
		}
		// 4
		accountEntity.setBalance(accountEntity.getBalance() - accountWithdrawFormDto.getAmount());
		int resultRow = accountRepository.updateById(accountEntity);

		// 5
		if (resultRow == 1) {
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

	/**
	 * 1. 트랜 잭션 처리 2. 계좌 존재 여부 확인 3. 입금 처리 4. 히스트로 등록
	 * 
	 * @param accountDepositFormDto
	 */
	@Transactional // 1
	public void diposit(AccountDepositFormDto accountDepositFormDto) {
		Account accountEntity = accountRepository.findByNumber(accountDepositFormDto.getDAccountNumber());
		// 2
		if (accountEntity == null) {
			throw new CustomRestfullException("계좌가 존재 하지 않습니다", HttpStatus.BAD_REQUEST);
		}
		// 3
		// AccountEntity - 모델 클래스에
		// 현재 잔액에 + 주입 받은 금액 = 즉, 모델 상태 변경처리를 해야 함
		accountEntity.deposit(accountDepositFormDto.getAmount());
		accountRepository.updateById(accountEntity); // 입금

		// 4
		History history = new History();
		history.setAmount(accountDepositFormDto.getAmount());
		history.setWBalance(null);
		history.setDAccountId(accountEntity.getId());
		history.setWBalance(null);
		history.setDBalance(accountEntity.getBalance());

		historyRepository.insert(history); // 히스토리 등록
	}

	/**
	 * 이체 서비스 트랜 잭션 처리
	 * 1. 출금 계좌 확인
	 * 2. 출금 계좌 비밀번호 확인 
	 * 3. 입금 계좌 확인 
	 * 4. 출금 잔액 확인 
	 * 5. 계좌 소유자 확인 
	 * 6. 출금 처리 
	 * 7. 입금 처리 
	 * 8. 거래 내역 저장
	 * 
	 * @param accountTransferFormDto
	 * @param principalId (세션 유저 ID)
	 * @return 계좌 상세 보기 화면 (계좌 ID)
	 */
	@Transactional
	public void transfer(AccountTransferFormDto accountTransferFormDto, int principalId) {
		Account withdrawAccountEntity = accountRepository.findByNumber(accountTransferFormDto.getWAccountNumber());
		// 1
		if (withdrawAccountEntity == null) {
			throw new CustomRestfullException("출금 계좌번호를 확인 해주세요", HttpStatus.BAD_REQUEST);
		}
	
		//2  코드 수정 
		withdrawAccountEntity.checkPassword(accountTransferFormDto.getWAccountPassword());
		
		// 3
		if (accountRepository.findByNumber(accountTransferFormDto.getDAccountNumber()) == null) {
			throw new CustomRestfullException("입금 계좌번호를 확인 해주세요", HttpStatus.BAD_REQUEST);
		}
		
		// 4 코드 수정 
		withdrawAccountEntity.checkBalance(accountTransferFormDto.getAmount());
		
		// 5 코드 수정 
		withdrawAccountEntity.checkOwner(principalId);
		
		// 6
		// 모델 클래스 상태 변경
		withdrawAccountEntity.setBalance(withdrawAccountEntity.getBalance() - accountTransferFormDto.getAmount());
		// DB에 update 처리 (withdrawAccountEntity 에는 id값 및 현재 변경된 상태 데이터들이 들어 있음)
		accountRepository.updateById(withdrawAccountEntity);

		// 7
		Account depositAccountEntity = accountRepository.findByNumber(accountTransferFormDto.getDAccountNumber());

		depositAccountEntity.setBalance(depositAccountEntity.getBalance() + accountTransferFormDto.getAmount());
		// 이체 계좌 잔액 업데이트 처리
		accountRepository.updateById(depositAccountEntity);
		
		// 8. 히스토리 (거래내역)
		History history = new History();
		history.setAmount(accountTransferFormDto.getAmount());
		history.setWAccountId(withdrawAccountEntity.getId());
		history.setDAccountId(depositAccountEntity.getId());
		// 이체 전 출금,입금 계좌 잔액 기록   
		history.setWBalance(withdrawAccountEntity.getBalance());
		history.setDBalance(depositAccountEntity.getBalance());
		int resultRow = historyRepository.insert(history);
		if(resultRow != 1) {
			throw new CustomRestfullException("잘못된 방식 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 계좌 정보 찾기 
	 * @param accountId  
	 * @return
	 */
	public Account readAccountById(int accountId) {
		Account accountEntity = accountRepository.findById(accountId);
		if(accountEntity == null) {
			throw new CustomRestfullException("해당 계좌를 찾을 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return accountEntity; 
	}
	
	/**
	 * 1개 계좌에 대한 거래 (동적쿼리) 
	 * @param id (계좌 id) 
	 * @param type (all - 거래내역 전체, deposit - 입금 내역, withdraw - 출금 내역)
	 * @return 거래 내역 목록 
	 */
	public List<HistoryResponseDto> readHistoryByAccount(Integer id, String type) {
		// 동적 쿼리 실행 -> 동적 결과를 받을 DTO 사용해 보기 
		List<HistoryResponseDto>  historyList = historyRepository.findByIdAndDynamicType(id, type);
		return historyList;
	}

}
