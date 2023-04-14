package com.tenco.bank.repository.model.account;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.tenco.bank.handler.exception.CustomRestfullException;

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

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new CustomRestfullException("계좌 비밀번호를 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkBalance(Long amount) {
        if (this.balance < amount) {
            throw new CustomRestfullException("출금 잔액이 부족 합니다", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkOwner(Integer principalId) {
        if (userId != principalId) {
            throw new CustomRestfullException("계좌 소유자가 아닙니다", HttpStatus.FORBIDDEN);
        }
    }
}
