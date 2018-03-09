package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.LogWebDistributorDiscountChange;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.LogWebDistributorDiscountChangeMapper;

@Repository
public class LogWebDistributorDiscountChangeWrap extends AbstractCRUDWrap<LogWebDistributorDiscountChange>{

	@Resource
	private LogWebDistributorDiscountChangeMapper logWebDistributorDiscountChangeMapper;
	
	@Override
	public ICRUDMapper<LogWebDistributorDiscountChange> getMapper() {
		return logWebDistributorDiscountChangeMapper;
	}

	@Transactional(readOnly=true)
	public List<LogWebDistributorDiscountChange> export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return logWebDistributorDiscountChangeMapper.export(domain.getParam());
	}

}
