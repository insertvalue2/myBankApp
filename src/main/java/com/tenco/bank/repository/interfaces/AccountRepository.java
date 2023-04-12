package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.model.account.Account;

@Mapper
public interface AccountRepository {
	public int insert(Account account);
	public int updateById(Account account);
	public int deleteById(int id);
	public List<Account> findAll();
	public Account findById(int id);
	
	public List<Account> findByUserId(Integer principalId);
	// 코드 추가 
	public Account findByNumber(String number); // 계좌 번호로 계좌 존재 여부 확인 
	
}
