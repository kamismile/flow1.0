package com.ziyuan.web.order.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.domain.action.AccountResponse;
import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributorBinded;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.shziyuan.flow.global.domain.flow.LogOrderCharging;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.producer.LogMessageProducer;
import com.ziyuan.web.order.dao.AccountDao;
import com.ziyuan.web.order.domain.ChargingBean;
import com.ziyuan.web.order.service.AccountService;
import com.ziyuan.web.order.service.QueueOrderProducer;

@Service
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountDao accountDao;

	@Autowired
	private StatusCode statusCode;
	
	@Autowired
	private LogMessageProducer logMessageProducer; 
	
	@Override
	public AccountDistributor getAccountDistributorById(int id) {
		return accountDao.getDistributorAccountById(id);
	}

	@Override
	public List<InfoSupplierBinded> getSupplierBanlanceBySku(int bind_sku_id) {
		// TODO Auto-generated method stub
		return accountDao.getSupplierBanlanceBySku(bind_sku_id);
	}

	@Override
	public List<InfoSupplierBinded> getSupplierPresentBySku(int bind_sku_id) {
		// TODO Auto-generated method stub
		return accountDao.getSupplierPresentBySku(bind_sku_id);
	}

	@Override
	public void update(int distributorId, int supplierId, double distributorPrice, double supplierPrice) {
		accountDao.update(distributorId, supplierId, distributorPrice, supplierPrice);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateChargingBack(Order order) {
		if (FEE_TYPE.PRESENT.val == order.getFee_type()) {
			accountDao.updateChargingPresent(order.getDistributor_id(), order.getDistributor_price());
		} else {
			accountDao.updateCharging(order.getDistributor_id(), order.getDistributor_price());
		}
		accountDao.updateStatisticsSupplier(order.getSupplier_id(), order.getSupplier_price());
		addLogOrderCharging(order, true);
	}

	@Override
	public void updateStatisticsSupplier(int supplier_id, double supplier_price) {
		accountDao.updateStatisticsSupplier(supplier_id, supplier_price);
		
	}

	@Override
	public AccountResponse procOrderCharging(QueueOrderMQWrap queueOrderMQWrap) {
//		ConfiguredBindBean bean, String order_no, String client_order_no, String phone, int provinceid
		ConfiguredBindBean bean = queueOrderMQWrap.getConfiguredBindBean();
		Order order = queueOrderMQWrap.getOrder();
		LoggerUtil.sys.info("订单计费-订单号:{}-渠道id:{}-", order.getOrder_no(), bean.getBindDistributor().getDistributor_id());
		//预定义计费类型
		FEE_TYPE fee = FEE_TYPE.PRESENT;
		Order returnOrder = null;
		//扣费bean
		ChargingBean cbean = null;
		AccountDistributor account = accountDao.getDistributorAccountById(bean.getBindDistributor().getDistributor_id());
		if (account.getPresent_banlance() <=0) {
			if(new BigDecimal(account.getBanlance()).add(new BigDecimal(account.getCredit())).doubleValue() <= 0) {
				//余额不足
				return new AccountResponse(statusCode.getDwi().getNotEnoughBanlance());
			} else {
				fee = FEE_TYPE.BANLANCE;
				returnOrder = new Order(bean, fee, order.getOrder_no(), order.getClient_order_no(), order.getPhone(), order.getProvinceid(),order.getNotify_url(), bean.getBindSupplierNormal());
				//预扣费
				cbean = new ChargingBean(returnOrder);
			}
		} else {
			returnOrder = new Order(bean, fee, order.getOrder_no(), order.getClient_order_no(), order.getPhone(), order.getProvinceid(),order.getNotify_url(), bean.getBindSupplierPresent());
			//预扣费
			cbean = new ChargingBean(returnOrder);	
		}
		
		try {
			procCharging(fee, cbean);
		} catch (Exception e) {
			LoggerUtil.error.error("订单计费失败-订单号:{}", order.getOrder_no(), e);
			return new AccountResponse(statusCode.getDwi().getPlatformError());
		}
		//扔入计费日志队列
		addLogOrderCharging(returnOrder, false);
		return new AccountResponse(true, returnOrder);
	}
	
	@Transactional(readOnly=false)
	private void procCharging(FEE_TYPE fee, ChargingBean cbean) {
		if (fee == FEE_TYPE.BANLANCE) {
			accountDao.updateChargingBanlance(cbean);
		} else {
			accountDao.updateChargingPresent(cbean);
		}
		//更新统计
		accountDao.updateStatisticsSupplier(cbean);
	}
	
	private void addLogOrderCharging(Order order, boolean back) {
		LogOrderCharging logOrderCharging = new LogOrderCharging();
		logOrderCharging.setOrder_no(order.getOrder_no());
		logOrderCharging.setFee(back == false ? order.getDistributor_price() : 0 - order.getDistributor_price());
		logOrderCharging.setDistributor_id(order.getDistributor_id());
		logOrderCharging.setFee_type(order.getFee_type());
		logMessageProducer.sendLog(logOrderCharging);
		
	}

}
