package com.tenco.bank.dto;

import com.tenco.bank.repository.model.account.Account;

import lombok.Data;

@Data
public class AccountSaveFormDto {
	
	private String number; 
	private String password;
		
	// insert, update 할 때 Account 모델이 필요하다. 
	public Account toModel(int principalId) {
		Account account = new Account();
		account.setUserId(principalId);
		account.setNumber(number);
		account.setPassword(password);
		account.setUserId(principalId);
		account.setBalance(0L);
		return account;
	}
}
