package com.shziyuan.flow.cnfmanager.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.shziyuan.flow.cnfmanager.daoutil.BaseSqlProvider;

public interface BaseMapper<T> {
	@SelectProvider(type = BaseSqlProvider.class,method = "selectByExample")
	List<T> selectByExample(T domain);
	
	@SelectProvider(type = BaseSqlProvider.class,method = "selectOne")
	T selectOne(T domain);
	
	@InsertProvider(type = BaseSqlProvider.class,method = "insert")
	void insert(T domain);
	
	@UpdateProvider(type = BaseSqlProvider.class,method = "update")
	void update(T domain);
	
	@DeleteProvider(type = BaseSqlProvider.class,method = "delete")
	void delete(T domain);
}
