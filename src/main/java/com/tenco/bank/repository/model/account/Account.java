package com.tenco.bank.repository.model.account;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {
    private Integer id;
    private String number;
    private String password;
    private Long balance;
    private Integer userId;
    private Timestamp createdAt;

    public void withdraw(Long amount) {
        this.balance = this.balance - amount;
    }

    public void deposit(Long amount) {
        this.balance = this.balance + amount;
    }
    
//    todo 추가 예정 
//    public void checkPassword(String password) {
//        if (!this.password.equals(password)) {
//            throw new CustomException("출금계좌 비밀번호 틀렸는데?", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    public void checkBalance(Long amount) {
//        if (this.balance < amount) {
//            throw new CustomException("잔액이 부족한데?", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    public void checkOwner(Integer principalId) {
//        if (userId != principalId) {
//            throw new CustomException("계좌 소유자가 아닙니다", HttpStatus.FORBIDDEN);
//        }
//    }
}
