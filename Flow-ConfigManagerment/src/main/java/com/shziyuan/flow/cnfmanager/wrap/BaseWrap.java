package com.shziyuan.flow.cnfmanager.wrap;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.cnfmanager.dao.BaseMapper;

@Transactional
public class BaseWrap<T> {
	private BaseMapper<T> baseMapper;
	
	protected BaseWrap(BaseMapper<T> baseMapper) {
		this.baseMapper = baseMapper;
	}
	
	@Transactional(readOnly = true)
	public List<T> selectByExample(T domain) {
		return baseMapper.selectByExample(domain);
	}
	
	@Transactional(readOnly = true)
	public T selectOne(T domain) {
		return baseMapper.selectOne(domain);
	}
	
	public void insert(T domain) {
		baseMapper.insert(domain);
	}
	
	public void update(T domain) {
		baseMapper.update(domain);
	}
	
	public void delete(T domain) {
		baseMapper.delete(domain);
	}
}
