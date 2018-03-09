package com.shziyuan.flow.queue.reportPush.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.shziyuan.flow.mq.stream.producer.LogMessageProducer;
import com.shziyuan.flow.mq.stream.producer.OrderModuleMessageProducer;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;

@Service
public class OrderService {

	@Autowired
	private QueueMessageProducer queueMessageProducer;
	
	@Autowired
	private OrderModuleMessageProducer orderModuleMessageProducer;
	
	@Autowired
	private LogMessageProducer logMessageProducer;
	
	@Autowired
	private StatusCode statusCode;
		
	public void orderDistributorPushFaild(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus,ProcStatus supplierInterfaceStatus) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			LoggerUtil.sys.info("[{}]订单推送失败",wrap);
			if(wrap.getQueueOrder().getOrderState() != Order.STATE_PUSH_NORELAY) {
				orderModuleMessageProducer.send(wrap);
			} else
				wrap.getOrder().setState(wrap.getQueueOrder().getOrderState());
			
			if(wrap.getQueueOrder().getRetries() < 3) {
				wrap.getQueueOrder().addRetries();
				queueMessageProducer.sendDistributorPush(wrap, wrap.getQueueOrder().getRetries());
			} else {
				LoggerUtil.sys.info("[{}]订单推送失败超过3次,放弃此订单的推送",wrap);
			}
		} finally {
			logMessageProducer.sendLog(getPushLog(wrap, resp,supplierInterfaceStatus));
		}
	}

	public void orderDistributorPushSuccess(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus,ProcStatus supplierInterfaceStatus) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			LoggerUtil.sys.info("[{}]订单推送成功",wrap);
			if(wrap.getQueueOrder().getOrderState() != Order.STATE_PUSH_NORELAY) {
				orderModuleMessageProducer.send(wrap);
			} else
				wrap.getOrder().setState(wrap.getQueueOrder().getOrderState());
		} finally {
			logMessageProducer.sendLog(getPushLog(wrap, resp,supplierInterfaceStatus));
		}
	}
	
	private LogQueueReportPush getPushLog(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus supplierInterfaceStatus) {
		Order order = wrap.getOrder();
		QueueOrder queue = wrap.getQueueOrder();
		InfoSku sku = null;
		try {
			sku = wrap.getConfiguredBindBean().getBindDistributor().getSku();
		} catch (Exception e) {
		}
		LogQueueReportPush log = new LogQueueReportPush();
		
		
		log.setClient_order_no(order.getClient_order_no());
		log.setSku_mask(order.getSku_mask());
		log.setDistributor_code(order.getDistributor_code());
		log.setPhone(order.getPhone());
		log.setPkg_type(order.getPkg_type());
		log.setProvinceid(order.getProvinceid());
		log.setOrder_no(order.getOrder_no());
		log.setInsert_time(TimestampUtil.now());
		log.setSku_id(order.getSku_id());
		log.setSku(order.getSku());
		log.setSupplier_id(order.getSupplier_id());
		log.setSupplier_name(order.getSupplier_name());
		log.setSupplier_code_id(order.getSupplier_code_id());
		log.setSupplier_code_name(order.getSupplier_code_name());
		log.setStandard_price(order.getStandard_price());
		log.setSupplier_discount(order.getSupplier_discount());
		log.setSupplier_price(order.getSupplier_price());
		log.setDistributor_discount(order.getDistributor_discount());
		log.setDistributor_price(order.getDistributor_price());
		log.setDistributor_id(order.getDistributor_id());
		log.setDistributor_name(order.getDistributor_name());
		if(sku != null) {
			log.setScope_cid(sku.getScope_cid());
			log.setType_cid(sku.getType_cid());
		}
		log.setResult_code(resp.getResultCode());
		if(supplierInterfaceStatus != null) {
			log.setSupplier_result(supplierInterfaceStatus.getMsg());
		}
//		log.setSync_mode(order.getsy);
		log.setRetries(queue.getRetries());
		log.setHttp_status(Integer.toString(resp.getInterfaceStatusCode()));
		return log;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

}
