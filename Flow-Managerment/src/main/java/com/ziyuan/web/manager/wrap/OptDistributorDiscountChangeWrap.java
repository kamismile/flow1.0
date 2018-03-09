package com.ziyuan.web.manager.wrap;

import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.OptDistributorDiscountChangeMapper;

@Repository
public class OptDistributorDiscountChangeWrap extends AbstractCRUDWrap<OptDistributorDiscountChange>{

	@Resource
	private OptDistributorDiscountChangeMapper optDistributorDiscountChangeMapper;
	
	@Override
	public ICRUDMapper<OptDistributorDiscountChange> getMapper() {
		return optDistributorDiscountChangeMapper;
	}

	@Transactional(readOnly=true)
	public JEasyuiData searchDistributor(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}
