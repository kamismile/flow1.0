package com.ziyuan.web.order.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.common.Constant.ORDER_STATE;
import com.shziyuan.flow.global.domain.action.AccountResponse;
import com.shziyuan.flow.global.domain.action.OrderResponse;
import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.exception.BaseException;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.producer.LogMessageProducer;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;
import com.ziyuan.web.order.dao.DistinctDistributorOrderDao;
import com.ziyuan.web.order.dao.OrderDistributorDao;
import com.ziyuan.web.order.dao.OrderInfoDao;
import com.ziyuan.web.order.dao.OrderSkuDao;
import com.ziyuan.web.order.dao.OrderStatusDao;
import com.ziyuan.web.order.dao.OrderSupplierDao;
import com.ziyuan.web.order.service.AccountService;
import com.ziyuan.web.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
		
	@Autowired
	private OrderInfoDao orderInfoDao;
	
	@Autowired
	private OrderSkuDao orderSkuDao;
	
	@Autowired
	private OrderDistributorDao orderDistributorDao;
	
	@Autowired
	private OrderStatusDao orderStatusDao;
	
	@Autowired
	private OrderSupplierDao orderSupplierDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DistinctDistributorOrderDao distinctDistributorOrderDao;
	
	@Autowired
	private QueueMessageProducer queueMessageProducer;
	
	@Override
	@Transactional(readOnly=true)
	public OrderResponse getOrderByOrderNo(String order_no) {
		Order order = null;
		try {
			order =  orderInfoDao.getOrderByOrderNo(order_no);
		} catch (Exception e) {
			return new OrderResponse(false, e.getMessage());
		}
		return new OrderResponse(true, order);
	}

	@Override
	public void saveOrder(QueueOrderMQWrap queueOrderMQWrap) throws BaseException{
		
		
		//验证订单唯一性
		OrderResponse  res =  distinctDistributorOrderDao.insertDistinctDistributorOrder(queueOrderMQWrap.getOrder().getClient_order_no());
		if (!res.isSuccess()) {
			LoggerUtil.sys.info("渠道订单号已存在,订单创建失败:{}-{}", queueOrderMQWrap.getOrder().getClient_order_no(), queueOrderMQWrap.getOrder().getOrder_no());
			queueOrderMQWrap.getConfiguredBindBean().setBindSupplier(queueOrderMQWrap.getConfiguredBindBean().getBindSupplierNormal());
			queueOrderMQWrap.getOrder().setState(Order.STATE_PUSH_NORELAY);
			ProcStatus status = new ProcStatus();
			status.setSuccess(false);
			status.setMsg("渠道订单号冲突");
			queueOrderMQWrap.setPlatformStatus(status);
			queueMessageProducer.sendDistributorPush(queueOrderMQWrap, 0);
			return;
		}
		
		AccountResponse response = accountService.procOrderCharging(queueOrderMQWrap);
		if (!response.isSuccess()) {
			LoggerUtil.sys.info("订单计费失败,订单创建失败-订单号:{},失败原因:{}", queueOrderMQWrap.getOrder().getOrder_no(), response.getStatus().getMsg());
			queueOrderMQWrap.getConfiguredBindBean().setBindSupplier(queueOrderMQWrap.getConfiguredBindBean().getBindSupplierNormal());
			queueMessageProducer.sendDistributorPush(queueOrderMQWrap, 0);
			return;
		}
		Order returnOrder = (Order) response.getData();
		//保存订单
		try {
			addOrder(returnOrder);
		} catch (Exception e) {
			LoggerUtil.error.error("订单创建失败-{}", queueOrderMQWrap.getOrder().getOrder_no(), e);
			queueOrderMQWrap.getConfiguredBindBean().setBindSupplier((returnOrder.getFee_type() == FEE_TYPE.BANLANCE.val ? queueOrderMQWrap.getConfiguredBindBean().getBindSupplierNormal() : queueOrderMQWrap.getConfiguredBindBean().getBindSupplierPresent()));
			queueMessageProducer.sendDistributorPush(queueOrderMQWrap, 0);
			return;
		}
		//塞入新数据
		queueOrderMQWrap.setOrder(returnOrder);
		queueOrderMQWrap.getConfiguredBindBean().setBindSupplier((returnOrder.getFee_type() == FEE_TYPE.BANLANCE.val ? queueOrderMQWrap.getConfiguredBindBean().getBindSupplierNormal() : queueOrderMQWrap.getConfiguredBindBean().getBindSupplierPresent()));
		queueMessageProducer.sendQueueOrder(queueOrderMQWrap);
	}

	@Transactional(readOnly=false)
	private void addOrder(Order order) {
		orderInfoDao.saveOrder(order);
		orderSkuDao.saveOrder(order);
		orderDistributorDao.saveOrder(order);
		orderStatusDao.saveOrder(order);
		orderSupplierDao.saveOrder(order);
	}

	@Override
	@Transactional(readOnly=false)
	public void cache(QueueOrderMQWrap queueOrder) throws Exception{
		Order order  = queueOrder.getOrder();
		order.setStatus_submit_code(queueOrder.getInterfaceStatus().getCode());
		order.setStatus_submit_message(queueOrder.getInterfaceStatus().getMsg());
		order.setSupplier_success(ORDER_STATE.BEFORESUBMIT_CACHE.val);
		order.setSupplier_report_success(ORDER_STATE.BEFORESUBMIT_CACHE.val);
		
		//更新订单最终状态
		orderStatusDao.result(order);
		orderSupplierDao.orderSubmit(order);
		
	}

	@Override
	@Transactional(readOnly=false)
	public void orderSubmit(QueueOrderMQWrap queueOrder) throws Exception{
		Order order  = queueOrder.getOrder();
		order.setStatus_submit_code(queueOrder.getInterfaceStatus().getCode());
		String statusMsg = queueOrder.getInterfaceStatus().getMsg();
		if (StringUtils.isNotEmpty(statusMsg)) {
			order.setStatus_submit_message(statusMsg.length() > 30 ? statusMsg.substring(0, 30) : statusMsg);
		}
		//状态更新
		if (!queueOrder.getPlatformStatus().isSuccess()) {
			order.setSupplier_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_FAILD.val);
		}
		orderSupplierDao.orderSubmit(order);
	}

	@Override
	@Transactional(readOnly=false)
	public void manualOrder(QueueOrderMQWrap queueOrder) throws Exception{
		Order order  = queueOrder.getOrder();
		order.setStatus_report_code(queueOrder.getInterfaceStatus().getCode());
		order.setStatus_report_message(queueOrder.getInterfaceStatus().getMsg());
		order.setStatus_push_code(queueOrder.getInterfaceStatus().getCode());
		order.setStatus_push_message(queueOrder.getInterfaceStatus().getMsg());
		//状态更新
		if (queueOrder.getPlatformStatus().isSuccess()) {
			order.setSupplier_report_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_SUCCESS.val);
			order.setSupplier_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_SUCCESS.val);
		} else {
			order.setSupplier_report_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_FAILD.val);
			order.setSupplier_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_FAILD.val);
		}
		//更新订单渠道推送状态
		orderDistributorDao.orderPush(order);
		//更新订单最终状态
		orderStatusDao.result(order);
		//更新订单供应商状态
		orderSupplierDao.statusOrder(order);
	}

	@Override
	@Transactional(readOnly=false)
	public void statusOrder(QueueOrderMQWrap queueOrder) throws Exception{
		//卡单
		if (queueOrder.getPlatformStatus().isSuccess() && queueOrder.getInterfaceStatus().isHold()) return;
		//非卡单
		Order order  = queueOrder.getOrder();
		order.setStatus_report_code(queueOrder.getInterfaceStatus().getCode());
		String statusMsg = queueOrder.getInterfaceStatus().getMsg().length() > 30 ? queueOrder.getInterfaceStatus().getMsg().substring(0, 30) : queueOrder.getInterfaceStatus().getMsg();
		order.setStatus_report_message(statusMsg);
		if (queueOrder.getPlatformStatus().isSuccess()) {
			order.setSupplier_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_SUCCESS.val);
		} else {
			order.setSupplier_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_FAILD.val);
		}
		orderSupplierDao.statusOrder(order);
	}

	@Override
	@Transactional(readOnly=false)
	public void result(QueueOrderMQWrap queueOrder) throws Exception{
		Order order  = queueOrder.getOrder();
		if (queueOrder.getPlatformStatus().isSuccess()) {
			order.setSupplier_report_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_SUCCESS.val);
		} else {
			order.setSupplier_report_success(ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_FAILD.val);
			//订单失败，这儿做最终的扣费返还
			LoggerUtil.sys.info("渠道：{}订单号:{}订单失败,预扣费用返还", queueOrder.getOrder().getDistributor_name(), queueOrder.getOrder().getOrder_no());
			accountService.updateChargingBack(queueOrder.getOrder());
		}
		orderStatusDao.result(order);
		
	}

	@Override
	@Transactional(readOnly=false)
	public void orderPush(QueueOrderMQWrap message) throws Exception {
		//状态更新
		Order order  = message.getOrder();
		order.setStatus_push_code(message.getInterfaceStatus().getCode());
		String statusMsg = message.getInterfaceStatus().getMsg();
		if (StringUtils.isNotEmpty(statusMsg)) {
			order.setStatus_push_message(statusMsg.length() > 30 ? statusMsg.substring(0, 30) : statusMsg);
		}
		//更新订单供应商和渠道商表
		orderDistributorDao.orderPush(order);
	}
	

}
