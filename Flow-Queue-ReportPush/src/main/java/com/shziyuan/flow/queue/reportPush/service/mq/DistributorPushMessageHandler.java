package com.shziyuan.flow.queue.reportPush.service.mq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse.Processing;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.process.QueueMessageHandler;
import com.shziyuan.flow.queue.base.supplier.event.MessageEvent.EVENT;
import com.shziyuan.flow.queue.reportPush.domain.DistributorPush;
import com.shziyuan.flow.queue.reportPush.service.distributor.IDistributorIntegerface;
import com.shziyuan.flow.queue.reportPush.service.order.OrderService;

@Service
@RabbitListener(queues = "#{rabbitConfiguration.getQueueName().getDistributorPush()}")
public class DistributorPushMessageHandler extends QueueMessageHandler{
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private IDistributorIntegerface distributorIntegerface;
	
	private Status pushSuccessStatus;
	private Status pushFaildStatus;
	
	public DistributorPushMessageHandler() {
		super(Order.STATE_DISTRIBUTOR_PUSH);
	}
	
	@PostConstruct
	public void init() {
		this.pushSuccessStatus = orderService.getStatusCode().getDistributor().getReportSuccess();
		this.pushFaildStatus = orderService.getStatusCode().getDistributor().getReportFaild();
	}
	
	@Override
	protected boolean doIncome(QueueOrderMQWrap wrap,QueueOrder queue,Order order,BindDistributorBean dis,InfoSupplier sup,InfoSupplierCodetable supcode) {
		LoggerUtil.request.info("MQ获取到[渠道推送]数据 O:{}|SORT:{}|SKU:{}|DIS:{}|{}|{}|{}",
				order.getOrder_no(),order.getSupplier_sort(),order.getSku(),
				dis.getDistributor().getId(),dis.getDistributor().getCode(),dis.getPrice(),dis.getDiscount());
		
		return true;
	}
	
	@Override
	protected BaseInterfaceRequestResponse doSubmit(QueueOrderMQWrap wrap,QueueOrder queue,Order order,BindDistributorBean dis,InfoSupplier sup,InfoSupplierCodetable supcode) {
		// 提交供应商
		
		// 推送渠道
		BaseInterfaceRequestResponse resp;
		try {
			// 根据平台订单状态决定推送状态
			Status pushStatus = wrap.getPlatformStatus().isSuccess() ? pushSuccessStatus : pushFaildStatus;
			DistributorPush pushData = new DistributorPush();
			pushData.setCstmOrderNo(order.getClient_order_no());
			pushData.setOrderNo(order.getOrder_no());
			pushData.setTimeStamp();
			pushData.setStatus(pushStatus);
			// 附加参数
			if(wrap.getInterfaceStatus() != null)
				pushData.addData("nativeMsg", wrap.getInterfaceStatus().getMsg());
			
			resp = distributorIntegerface.push(order,pushData,dis.getDistributor());
			resp.setQueueSource(wrap);
		} catch (Exception e) {
			resp = new BaseInterfaceRequestResponse(wrap);
			resp.setProcessing(Processing.FAILD);
			resp.setResultCode(orderService.getStatusCode().getPlatform().getSupplierSubmitError().getCode());
			resp.setResultMsg(e.getMessage());
		}
		LoggerUtil.request.info("[{}]渠道推送->{} 第{}次 结果 RAW:{}",order.showSimpleLog(),order.getNotify_url(),queue.getRetries(),resp.getResultRaw());
		doEvent(EVENT.DistributorResponse,resp, wrap);
		
		return resp;
	}
	
	@Override
	protected void doOver(QueueOrderMQWrap wrap, BaseInterfaceRequestResponse resp) {
		// 提交完成的结果处理
		ProcStatus supplierInterfaceStatus = wrap.getInterfaceStatus();
		ProcStatus interfaceStatus = new ProcStatus(resp.isSuccess(),resp.getResultCode(),resp.getResultMsg(),resp.getRemark());
		Status status = resp.isSuccess() ? orderService.getStatusCode().getPlatform().getDisPushSuccess() 
				: orderService.getStatusCode().getPlatform().getDisPushFaild();
		ProcStatus platformStatus = new ProcStatus(resp.isSuccess(),status);

		if(resp.isSuccess()) {
			orderService.orderDistributorPushSuccess(wrap,resp,platformStatus,interfaceStatus,supplierInterfaceStatus);
		} else {
			orderService.orderDistributorPushFaild(wrap,resp,platformStatus,interfaceStatus,supplierInterfaceStatus);
		}
	}
	
	@Override
	public String showQueueName() {
		return "[QUEUE]供应商发送队列监听器";
	}
}
