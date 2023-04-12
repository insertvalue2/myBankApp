
CREATE TABLE user_tb(
	id int auto_increment primary key,
	username varchar(50)  not null unique,
	password varchar(30) not null,
	fullname varchar(50) not null,
	created_at timestamp not null default now()
);


-- 테이블 설계시 주석문도 포함 가능 합니다. 
CREATE TABLE account_tb(
	id int auto_increment primary key,
	number varchar(30) unique not null,
	password varchar(20) not null,
	balance bigint not null COMMENT '계좌 잔액' ,
	user_id int,
	created_at timestamp not null default now()
);

-- bigint 8바이트 정수형 
-- 조(10에12승) - 경(10에20승) - 해(10에24승) - 자(10에28승) - 양(10에32승)
CREATE TABLE history_tb(
	id int auto_increment primary key COMMENT '거래 내역 ID',
	amount bigint not null COMMENT '거래 금액',
	w_balance bigint COMMENT '출금 계좌 잔액',
	d_balance bigint COMMENT '입금 계좌 잔액',
	w_account_id int COMMENT '출금 계좌 ID',
	d_account_id int COMMENT '입금 계좌 ID',
	created_at timestamp not null
);