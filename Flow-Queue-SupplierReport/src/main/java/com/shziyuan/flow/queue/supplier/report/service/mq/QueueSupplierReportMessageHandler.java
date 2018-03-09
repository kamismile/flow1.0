package com.shziyuan.flow.queue.supplier.report.service.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.command.ManualCommand;
import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse.Processing;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.interactive.SupplierInterfaceManager;
import com.shziyuan.flow.queue.base.process.QueueMessageHandler;
import com.shziyuan.flow.queue.base.supplier.event.MessageEvent.EVENT;
import com.shziyuan.flow.queue.supplier.report.service.order.OrderService;

/**
 * 提交订单数据队列监听
 * @author james.hu
 *
 */
@Service
@RabbitListener(queues = "#{rabbitConfiguration.getQueueName().getSupplierReport()}")
public class QueueSupplierReportMessageHandler extends QueueMessageHandler{
	
	@Autowired
	private OrderService orderService;
			
	@Autowired
	private SupplierInterfaceManager supplierInterfaceManager;
	
	public QueueSupplierReportMessageHandler() {
		super(Order.STATE_SUPPLIER_REPORT);
	}
	
	@Override
	protected boolean doIncome(QueueOrderMQWrap wrap,QueueOrder queue,Order order,BindDistributorBean dis,InfoSupplier sup,InfoSupplierCodetable supcode) {
		LoggerUtil.request.info("MQ获取到[供应商状态]数据 O:{}|SORT:{}|SKU:{}|DIS:{}|{}|{}|{}|{}|SUP:{}|{}|{}|{}|{}|{}|{}",
				order.getOrder_no(),order.getSupplier_sort(),order.getSku(),
				dis.getDistributor().getId(),dis.getDistributor().getCode(),dis.getPrice(),dis.getDiscount(),
				sup.getId(),sup.getName(),supcode.getId(),supcode.getName(),supcode.getCode(),supcode.getPrice(),supcode.getDiscount());
		
		// 获取缓存中是否存在此订单号,存在说明已被手动操作,放弃此订单
		Integer orderStatus = orderService.redisOrderStatus(order.getOrder_no());
		// 如果retry == -1 作为失败推送
		if(orderStatus.intValue() != ManualCommand.CMD_NORMALIZING
				&& orderStatus.intValue() == ManualCommand.CMD_ORDER_PUSH_FAILD) {
			LoggerUtil.sys.info("[{}]订单设置已手动推送失败,移除无效消息",order.showSimpleLog());
			return false;
		} else if(orderStatus.intValue() == ManualCommand.CMD_ORDER_PUSH_FAILD) {
			LoggerUtil.sys.info("[{}]订单设置已手动推送成功,移除无效消息",order.showSimpleLog());
			return false;
		}
		
		return true;
	}
	
	@Override
	protected BaseInterfaceRequestResponse doSubmit(QueueOrderMQWrap wrap,QueueOrder queue,Order order,BindDistributorBean dis,InfoSupplier sup,InfoSupplierCodetable supcode) {
		// 提交供应商
		BaseInterfaceRequestResponse resp;
		try {
			resp = supplierInterfaceManager.report(wrap);
			resp.setQueueSource(wrap);
		} catch (Exception e) {
			resp = new BaseInterfaceRequestResponse(wrap);
			resp.setProcessing(Processing.HOLD);
			resp.setResultCode(orderService.getStatusCode().getPlatform().getSupplierSubmitError().getCode());
			resp.setResultMsg(e.getMessage());
		}
		LoggerUtil.request.info("[{}]供应商状态结果 P:{} CODE:{} MSG:{} RAW:{}",order.showSimpleLog(),resp.getProcessing().name(),resp.getResultCode(),resp.getResultMsg(),resp.getResultRaw());
		doEvent(EVENT.SupplierResponse,resp, wrap);
		
		return resp;
	}
	
	@Override
	protected void doOver(QueueOrderMQWrap wrap, BaseInterfaceRequestResponse resp) {
		// 提交完成的结果处理
		if(resp.isSuccess()) {
			ProcStatus interfaceStatus = new ProcStatus(true,resp.getResultCode(),resp.getResultMsg(),resp.getRemark());
			ProcStatus platformStatus = new ProcStatus(true,orderService.getStatusCode().getPlatform().getSupplierReportSuccess()); 
			orderService.supplierReportSuccess(wrap,resp,platformStatus,interfaceStatus);
		} else if(resp.isHold()) {
			ProcStatus interfaceStatus = new ProcStatus();
			interfaceStatus.hold(resp.getResultCode(), resp.getResultMsg());
			ProcStatus platformStatus = new ProcStatus(false,orderService.getStatusCode().getPlatform().getSupplierReportHold());
			orderService.supplierReportHold(wrap,resp,platformStatus,interfaceStatus,true);
		} else {
			ProcStatus interfaceStatus = new ProcStatus(false,resp.getResultCode(),resp.getResultMsg(),resp.getRemark());
			ProcStatus platformStatus = new ProcStatus(false,orderService.getStatusCode().getPlatform().getSupplierReportFaild());
			orderService.supplierReportFaild(wrap,resp,platformStatus,interfaceStatus);
		}
	}
	
	@Override
	public String showQueueName() {
		return "[SupplierReport]供应商状态队列监听器";
	}
}
