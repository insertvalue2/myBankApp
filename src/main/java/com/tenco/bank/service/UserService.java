package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignUpDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service // IoC 에 대상 
public class UserService {

	// DAO가 필요(mybatis 사용) 
	@Autowired // DI 
	private UserRepository userRepository;
	
	// 회원 가입
	// 메서드 호출이 시작될때, 트랜잭션 시작, 끝날때, 트랜잭션 종료 (commit)
	@Transactional  
	public void signUp(SignUpDto signUpDto) {
		System.out.println("11111");
		int result = userRepository.insert(signUpDto);
		System.out.println("22222");
		if(result != 1) {
			throw new CustomRestfullException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
