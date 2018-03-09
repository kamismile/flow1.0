package com.ziyuan.web.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.ziyuan.web.manager.dao.OptSupplierCodetableDiscountChangeMapper;
import com.ziyuan.web.manager.wrap.OptSupplierCodetableDiscountChangeWrap;



@Service
public class OptSupplierCodetableDiscountChangeService {

	@Resource
	private OptSupplierCodetableDiscountChangeWrap optSupplierCodetableDiscountChangeWrap;
	
	public JEasyuiData batchInsert(List<OptSupplierCodetableDiscountChange> domains) {
		
		optSupplierCodetableDiscountChangeWrap.batchInsert(
				OptSupplierCodetableDiscountChangeMapper.class.getName(),domains);
		return new JEasyuiData(true, "");
	}
}
