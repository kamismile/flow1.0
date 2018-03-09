package com.ziyuan.web.manager.wrap;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributor;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributorPresent;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.shziyuan.flow.global.util.SerializableUtil;
import com.ziyuan.web.manager.dao.AccountDistributorMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;

@Repository
public class AccountDistributorWrap extends AbstractCRUDWrap<AccountDistributor> {

	@Resource
	private AccountDistributorMapper accountDistributorMapper;
	
	@Resource
	private PendingAccountDistirbutorWrap pendingAccountDistirbutorWrap;
	
	@Resource
	private LogAccountDistributorWrap logAccountDistributorWrap;
	
	@Transactional(readOnly=false)
	public void addAccountDistributor(Integer id) {
		accountDistributorMapper.addAccountDistributor(id);
	}

	@Override
	public ICRUDMapper<AccountDistributor> getMapper() {
		return accountDistributorMapper;
	}
	
	@Transactional(readOnly = false)
	public AccountDistributor addFunds(String update_user,PendingAccountDistributor pending,boolean ispass) {
		accountDistributorMapper.lockByAccounting();
		
		AccountDistributor before = selectOne(pending.getDistributor_id());
		AccountDistributor after = SerializableUtil.clone(before);
		
		String action = "加款审核";
		if(ispass) {
			after.setBanlance(new BigDecimal(before.getBanlance()).add(new BigDecimal(pending.getBanlance())).doubleValue());
			action += "通过";
		} else
			action += "失败";
			
		LogAccountDistributor log = new LogAccountDistributor(update_user, pending, before, after,action);
		
		if(ispass) {
			accountDistributorMapper.update(after);
		}
		pendingAccountDistirbutorWrap.delete(pending.getId());
		accountDistributorMapper.unlockByAccounting();
		logAccountDistributorWrap.insert(log);
		
		return after;
	}
	@Transactional(readOnly = false)
	public AccountDistributor addCredit(String update_user,PendingAccountDistributor pending,boolean ispass) {
		accountDistributorMapper.lockByAccounting();
		
		AccountDistributor before = selectOne(pending.getDistributor_id());
		AccountDistributor after = SerializableUtil.clone(before);
		
		String action = "授信审核";
		if(ispass) {
			after.setCredit(new BigDecimal(before.getCredit()).add(new BigDecimal(pending.getBanlance())).doubleValue());
			after.setReceivable(new BigDecimal(before.getReceivable()).add(new BigDecimal(pending.getBanlance())).doubleValue());
			action += "通过";
		} else
			action += "失败";
		
		LogAccountDistributor log = new LogAccountDistributor(update_user, pending, before, after,action);
		
		if(ispass) {
			accountDistributorMapper.update(after);
		}
		pendingAccountDistirbutorWrap.delete(pending.getId());
		accountDistributorMapper.unlockByAccounting();
		logAccountDistributorWrap.insert(log);
		
		return after;
	}
	@Transactional(readOnly = false)
	public AccountDistributor addDonation(String update_user,PendingAccountDistributor pending,boolean ispass) {
		accountDistributorMapper.lockByAccounting();
		
		AccountDistributor before = selectOne(pending.getDistributor_id());
		AccountDistributor after = SerializableUtil.clone(before);
		
		String action = "赠送审核";
		if(ispass) {
			after.setPresent_banlance(new BigDecimal(before.getPresent_banlance()).add(new BigDecimal(pending.getBanlance())).doubleValue());
			action += "通过";
		} else
			action += "失败";
		
		LogAccountDistributor log = new LogAccountDistributor(update_user, pending, before, after,action);
		
		if(ispass) {
			accountDistributorMapper.update(after);
		}
		pendingAccountDistirbutorWrap.delete(pending.getId());
		accountDistributorMapper.unlockByAccounting();
		logAccountDistributorWrap.insert(log);
		
		return after;
	}
	
	@Transactional(readOnly = false)
	public void autoPresent(List<LogAccountDistributorPresent> list) {
		super.batchRun(session -> {
			list.forEach(present -> {
				session.update("com.ziyuan.web.manager.dao.AccountDistributorMapper.udpatePresent",present);
				session.insert("com.ziyuan.web.manager.dao.LogAccountDistributorPresentMapper.insert",present);
			});			
		});
	}
}
