INSERT INTO user_tb(username, password, fullname, created_at) values('길동', '1234',
'고', now());
INSERT INTO user_tb(username, password, fullname, created_at) values('둘리', '1234',
'애기공룡', now());
INSERT INTO user_tb(username, password, fullname, created_at) values('콜', '1234',
'마이', now());


INSERT INTO account_tb(number, password, balance, user_id, created_at)
values('1111', '1234', 900, 1, now());
INSERT INTO account_tb(number, password, balance, user_id, created_at)
values('2222', '1234', 1100, 2, now());
INSERT INTO account_tb(number, password, balance, user_id, created_at)
values('333', '1234', 0, 3, now());


-- 이체 내역 ( 1번이 2번 100원 이체) 1번 잔액 900원 2번 잔액 1100원 
INSERT INTO history_tb(amount, w_balance, d_balance, w_account_id, d_account_id,
created_at) values(100, 900, 1100, 1, 2, now());

-- 출금 내역 (1번이 100원 출금, 잔액 : 800원) 
INSERT INTO history_tb(amount, w_balance, d_balance, w_account_id, d_account_id,
created_at) values(100, 800, null, 1, null, now());

-- 입금 내역 (1번이 100원 입금, 잔액 : 900원) 
INSERT INTO history_tb(amount, w_balance, d_balance, w_account_id, d_account_id,
created_at) values(100, null, 900, null, 1, now());

-- 출금 내역 (3번이 1000원 출금, 잔약 : 0원) 
INSERT INTO history_tb(amount, w_balance, d_balance, w_account_id, d_account_id,
created_at) values(1000, 1000, null, 3, null, now());

commit;