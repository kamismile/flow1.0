package org.james.common.dao;

import java.util.List;

public interface BaseMapper<T> {
	public List<T> selectAll();
	public List<T> selectAll(T condition);
	public T selectOne(T condition);
	public int insert(T condition);
	public int update(T condition);
	public int delete(T condition);
}
