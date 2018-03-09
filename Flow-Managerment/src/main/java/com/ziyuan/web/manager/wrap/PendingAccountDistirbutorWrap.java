package com.ziyuan.web.manager.wrap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.PendingAccountDistirbutorMapper;

@Repository
public class PendingAccountDistirbutorWrap extends AbstractCRUDWrap<PendingAccountDistributor>{

	@Resource
	private PendingAccountDistirbutorMapper pendingAccountDistirbutorMapper;
	
	@Override
	public ICRUDMapper<PendingAccountDistributor> getMapper() {
		return pendingAccountDistirbutorMapper;
	}
	
}
