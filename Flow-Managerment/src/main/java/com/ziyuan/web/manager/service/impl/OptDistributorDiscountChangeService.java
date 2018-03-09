package com.ziyuan.web.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.ziyuan.web.manager.dao.OptDistributorDiscountChangeMapper;
import com.ziyuan.web.manager.wrap.OptDistributorDiscountChangeWrap;


@Service
public class OptDistributorDiscountChangeService {

	@Resource
	private OptDistributorDiscountChangeWrap optDistributorDiscountChangeWrap;
	
	public JEasyuiData batchInsert(List<OptDistributorDiscountChange> domains) {
		
		optDistributorDiscountChangeWrap.batchInsert(
				OptDistributorDiscountChangeMapper.class.getName(),domains);
		return new JEasyuiData(true, "");
	}
}
