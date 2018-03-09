package com.shziyuan.flow.queue.supplier.report.service.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.wraped.PassiveSupplierReportMQWrap;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse.Processing;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.consumer.AbstractMessageConsumer;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.interactive.SupplierInterfaceManager;
import com.shziyuan.flow.queue.supplier.report.service.order.OrderService;
import com.shziyuan.flow.redis.passiveReport.service.RedisPassiveReportService;

@Service
@RabbitListener(queues = "#{rabbitConfiguration.getQueueName().getSupplierPassiveReport()}")
public class PassiveSupplierReportMessageHandler extends AbstractMessageConsumer<PassiveSupplierReportMQWrap>{

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RedisPassiveReportService redisPassiveReportService;
			
	@Autowired
	private SupplierInterfaceManager supplierInterfaceManager;
	
	@Override
	public String showQueueName() {
		return "[SupplierReport]被动供应商状态队列监听器";
	}

	@Override
	public void doInput(PassiveSupplierReportMQWrap wrap) {
		String redisKey = supplierInterfaceManager.passiveReportKey(wrap);
		QueueOrderMQWrap redisWrap = redisPassiveReportService.getPassiveReportWrap(redisKey);
		
		if(redisWrap == null) {
			LoggerUtil.sys.info("无效的供应商推送状态或消息已过期:key:{} - supplier_id:{} - source:{}",redisKey,wrap.getSupplierId(),wrap.getReportSource());
			return;
		}
		
		Order order = redisWrap.getOrder();
		
		// 调用供应商接口
		BaseInterfaceRequestResponse resp;
		try {
			resp = supplierInterfaceManager.passiveReport(redisWrap,wrap.getReportSource());
			resp.setQueueSource(redisWrap);
		} catch (Exception e) {
			resp = new BaseInterfaceRequestResponse(redisWrap);
			resp.setProcessing(Processing.FAILD);
			resp.setResultCode(orderService.getStatusCode().getPlatform().getSupplierSubmitError().getCode());
			resp.setResultMsg(e.getMessage());
		}
		LoggerUtil.request.info("[{}]供应商状态结果 P:{} CODE:{} MSG:{} RAW:{}",order.showSimpleLog(),resp.getProcessing().name(),resp.getResultCode(),resp.getResultMsg(),resp.getResultRaw());
		
		// 提交完成的结果处理
		if(resp.isSuccess()) {
			ProcStatus interfaceStatus = new ProcStatus(true,resp.getResultCode(),resp.getResultMsg(),resp.getRemark());
			ProcStatus platformStatus = new ProcStatus(true,orderService.getStatusCode().getPlatform().getSupplierReportSuccess()); 
			orderService.supplierReportSuccess(redisWrap,resp,platformStatus,interfaceStatus);
			// 删除redis缓存
			redisPassiveReportService.deletePassiveReportWrap(redisKey);
		} else if(resp.isHold()) {
			ProcStatus interfaceStatus = new ProcStatus();
			interfaceStatus.hold(resp.getResultCode(), resp.getResultMsg());
			ProcStatus platformStatus = new ProcStatus(true,orderService.getStatusCode().getPlatform().getSupplierReportHold());
			orderService.supplierReportHold(redisWrap,resp,platformStatus,interfaceStatus,false);
		} else {
			ProcStatus interfaceStatus = new ProcStatus(false,resp.getResultCode(),resp.getResultMsg(),resp.getRemark());
			ProcStatus platformStatus = new ProcStatus(false,orderService.getStatusCode().getPlatform().getSupplierReportFaild());
			orderService.supplierReportFaild(redisWrap,resp,platformStatus,interfaceStatus);
			// 删除redis缓存
			redisPassiveReportService.deletePassiveReportWrap(redisKey);
		}
	}

}
