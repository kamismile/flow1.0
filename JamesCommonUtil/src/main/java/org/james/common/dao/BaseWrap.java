package org.james.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

public class BaseWrap {
	@Autowired
	protected ApplicationContext ctx;
	
	protected <MAPPER extends BaseMapper<T>,T> MAPPER getMapper(Class<MAPPER> clazz) {
		return ctx.getBean(clazz);
	}
	@Transactional(readOnly = true)
	public <MAPPER extends BaseMapper<T>,T> List<T> selectAll(Class<MAPPER> clazz) {
		return getMapper(clazz).selectAll();
	}
	
	@Transactional(readOnly = true)
	public <MAPPER extends BaseMapper<T>,T> List<T> selectAll(Class<MAPPER> clazz,T condition) {
		return getMapper(clazz).selectAll(condition);
	}
	
	@Transactional(readOnly = true)
	public <MAPPER extends BaseMapper<T>,T> T selectOne(Class<MAPPER> clazz,T condition) {
		return getMapper(clazz).selectOne(condition);
	}
	
	@Transactional(readOnly = false)
	public <MAPPER extends BaseMapper<T>,T> int insert(Class<MAPPER> clazz,T condition) {
		return getMapper(clazz).insert(condition);
	}
	
	@Transactional(readOnly = false)
	public <MAPPER extends BaseMapper<T>,T> int update(Class<MAPPER> clazz,T condition) {
		return getMapper(clazz).update(condition);
	}
	
	@Transactional(readOnly = false)
	public <MAPPER extends BaseMapper<T>,T> int delete(Class<MAPPER> clazz,T condition) {
		return getMapper(clazz).delete(condition);
	}
}
