package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.model.history.History;

@Mapper
public interface HistoryRepository {
	public int insert(History history);
	public int updateById(History history);
	public int deleteById(int id);
	public List<History> findAll(); 
	public History findById(); 
}
