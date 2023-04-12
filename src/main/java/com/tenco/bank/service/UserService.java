package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInDto;
import com.tenco.bank.dto.SignUpDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.repository.interfaces.UserRepository;
import com.tenco.bank.repository.model.user.User;

@Service // IoC 에 대상 
public class UserService {

	// DAO가 필요(mybatis 사용) 
	@Autowired // DI 
	private UserRepository userRepository;
	
	// 회원 가입
	// 메서드 호출이 시작될때, 트랜잭션 시작, 끝날때, 트랜잭션 종료 (commit)
	@Transactional  
	public void signUp(SignUpDto signUpDto) {
		int result = userRepository.insert(signUpDto);
		if(result != 1) {
			throw new CustomRestfullException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional  
	public User singIn(SignInDto signInDto) {
		// 접근 주체 
		User principal = userRepository.findByUsernameAndPassword(signInDto);
		if(principal == null) {
			throw new CustomRestfullException("아이디 혹인 비번이 틀렸습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println(principal);
		return principal;
	}
}

