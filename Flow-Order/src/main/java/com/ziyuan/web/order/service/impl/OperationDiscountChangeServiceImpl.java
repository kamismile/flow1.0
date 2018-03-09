package com.ziyuan.web.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.ziyuan.web.order.dao.OperationDiscountChangeDao;
import com.ziyuan.web.order.domain.BindDistributorChangeBean;
import com.ziyuan.web.order.service.OperationDiscountChangeService;

@Service
public class OperationDiscountChangeServiceImpl implements OperationDiscountChangeService {

	@Resource
	private OperationDiscountChangeDao operationDiscountChangeDao;

	/**
	 * 供应商折扣调整
	 */
	@Override
	public ActionResponse SupplierDiscount(List<OptSupplierCodetableDiscountChange> domains) {
		try {
			operationDiscountChangeDao.SupplierDiscount(domains);
			return new ActionResponse(true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ActionResponse(false, e.getMessage());
		}
	}

	/**
	 * 渠道商折扣调整
	 */
	@Override
	public ActionResponse DistributorDiscount(List<OptDistributorDiscountChange> domains) {
		try {
			operationDiscountChangeDao.DistributorDiscount(domains);
			return new ActionResponse(true);
		} catch (Exception e) {
			// TODO: handle exception
			return new ActionResponse(false, e.getMessage());
		}
	}

	/**
	 * 绑定关系和修改折扣
	 */
	@Override
	public ActionResponse bindDiscount(BindDistributorChangeBean data) {
		try {
			data.getNewBind().forEach(bind -> bind.setEnabled(true));
			if(data.getRemoveBind().size() > 0)
				operationDiscountChangeDao.batchDelete(data.getRemoveBind());
			if(data.getEditBind().size() > 0)
				operationDiscountChangeDao.batchUpdate(data.getEditBind());
			if(data.getNewBind().size() > 0)
				operationDiscountChangeDao.batchInsert(data.getNewBind());
			
			return new ActionResponse(true);
		} catch (Exception e) {
			return new ActionResponse(false, e.getMessage());
		}
	}
	
	
}
