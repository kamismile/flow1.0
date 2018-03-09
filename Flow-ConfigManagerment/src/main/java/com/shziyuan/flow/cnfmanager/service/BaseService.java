package com.shziyuan.flow.cnfmanager.service;

import java.util.List;

import com.shziyuan.flow.cnfmanager.wrap.BaseWrap;

public class BaseService<T> {
	private BaseWrap<T> baseWrap;
	
	protected BaseService(BaseWrap<T> baseWrap) {
		this.baseWrap = baseWrap;
	}
	
	public List<T> selectByExample(T domain) {
		return baseWrap.selectByExample(domain);
	}
	
	public T selectOne(T domain) {
		return baseWrap.selectOne(domain);
	}
	
	public void insert(T domain) {
		baseWrap.insert(domain);
	}
	
	public void update(T domain) {
		baseWrap.update(domain);
	}
	
	public void delete(T domain) {
		baseWrap.delete(domain);
	}
}
