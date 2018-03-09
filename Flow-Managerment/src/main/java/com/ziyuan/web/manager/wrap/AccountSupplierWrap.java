package com.ziyuan.web.manager.wrap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.ziyuan.web.manager.dao.AccountSupplierMapper;
import com.ziyuan.web.manager.dao.StatisticsSupplierMapper;

@Repository
public class AccountSupplierWrap {

	@Resource
	AccountSupplierMapper accountSupplierMapper;
	
	@Transactional(readOnly=false)
	public void addAccountSupplier(Integer id) {
		accountSupplierMapper.addAccountSupplier(id);
	}
}
