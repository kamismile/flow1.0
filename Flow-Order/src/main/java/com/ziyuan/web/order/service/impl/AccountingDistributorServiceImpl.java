package com.ziyuan.web.order.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.ziyuan.web.order.dao.AccountingDistributorDao;
import com.ziyuan.web.order.service.AccountingDistributorService;

@Service
public class AccountingDistributorServiceImpl implements AccountingDistributorService{

	@Resource
	private AccountingDistributorDao accountingDistributorDao;
	
	@Override
	public ActionResponse pendingInsert(String user, int field, PendingAccountDistributor domain) {
		try {
			domain.setUpdate_user(user);
			domain.setField(field);
			int id = accountingDistributorDao.pendingInsert(domain);
			PendingAccountDistributor res = new PendingAccountDistributor();
			if(id != -1){
				res.setId(id);
				res.setDistributor_id(domain.getDistributor_id());
				res.setUpdate_user(user);
				res.setBanlance(domain.getBanlance());
				return new ActionResponse(true, res);
			}
			return new ActionResponse(false);
		} catch (Exception e) {
			// TODO: handle exception
			return new ActionResponse(false, e.getMessage());
		}
	}

}
