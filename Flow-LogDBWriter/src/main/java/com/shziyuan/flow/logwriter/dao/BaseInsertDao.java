package com.shziyuan.flow.logwriter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 公共LOG日志插入DAO
 * @author james.hu
 *
 * @param <T>
 */
public abstract class BaseInsertDao<T> {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;		// jdbc模板
	
	private String insertSql;
	
	/**
	 * 子类实现时,传递插入insert sql
	 * @param jdbcTemplate
	 * @param insertSql
	 */
	protected BaseInsertDao(String insertSql) {
		this.insertSql = insertSql;
	}
	
	
	// 默认插入操作
	protected void insert(String sql,T log) {
		// 自动反射类属性到sql参数
		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(log);
		// 插入数据库
		jdbcTemplate.update(sql, namedParameters);
	}
	
	public void insert(T log) {
		this.insert(insertSql, log);
	}
}
