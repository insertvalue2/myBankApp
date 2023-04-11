package com.tenco.bank.repository.interfaces;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.dto.SignUpDto;
import com.tenco.bank.repository.model.user.User;

@Mapper // MyBatis 의존설 설정 함
public interface UserRepository {

	public int insert(SignUpDto signUpDto); 
	public int updateById();
	public int deleteById(int id);
	public User findById(); 
	public List<User> findAll(); 

}
