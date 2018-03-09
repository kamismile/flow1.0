package com.shziyuan.flow.queue.supplier.report.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierReport;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.shziyuan.flow.mq.stream.producer.LogMessageProducer;
import com.shziyuan.flow.mq.stream.producer.OrderModuleMessageProducer;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.redis.base.service.RedisBindConfigService;
import com.shziyuan.flow.redis.base.service.RedisOrderStatusService;

@Service
public class OrderService {

	@Autowired
	private RedisBindConfigService redisBindConfigService;
	
	@Autowired
	private RedisOrderStatusService redisOrderStatusService;
	
	@Autowired
	private QueueMessageProducer queueMessageProducer;
	
	@Autowired
	private OrderModuleMessageProducer orderModuleMessageProducer;
	
	@Autowired
	private LogMessageProducer logMessageProducer;
	
	@Autowired
	private StatusCode statusCode;
	
	
	public Integer redisOrderStatus(String order_no) {
		return redisOrderStatusService.getOrderStatus(order_no);
	}
	
	public void supplierReportFaild(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			
			reportFaildProcess(wrap);
		} finally {
			queueMessageProducer.sendDistributorPush(wrap);
			logMessageProducer.sendLog(getSupplierReportLog(wrap, resp));
		}
	}
	
	public void supplierReportHold(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus,boolean needRetry) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			LoggerUtil.sys.info("[{}]订单状态处理中",wrap);
			orderModuleMessageProducer.send(wrap);
			
			QueueOrder queue = wrap.getQueueOrder();
			// 订单重试
			if(needRetry && queue.getRetries() < queueMessageProducer.RETRY_TIMES) {
				queue.addRetries();
				LoggerUtil.sys.info("[{}]订单状态处理中,继续重试获取状态,第{}次重试",wrap,queue.getRetries());
				queueMessageProducer.sendQueueSupplierReport(wrap, queue.getRetries());
			} else {
				// 超过重试次数
				LoggerUtil.http.debug("[{}]订单获取状态报告超过重试次数,尝试下一个供应商");
				reportFaildProcess(wrap);
			}
		} finally {
			logMessageProducer.sendLog(getSupplierReportLog(wrap, resp));
		}
	}
	
	private void reportFaildProcess(QueueOrderMQWrap wrap) {
		Order order = wrap.getOrder();
		order.addSupplier_sort();
		ConfiguredBindBean config = redisBindConfigService.getBind(
				order.getPhone(), order.getDistributor_code(), order.getSku(), order.getSupplier_sort());
		config.setBindSupplier((order.getFee_type() == FEE_TYPE.BANLANCE.val ? config.getBindSupplierNormal() : config.getBindSupplierPresent()));
		wrap.setConfiguredBindBean(config);
		if(config.isSuccess()) {
			LoggerUtil.sys.info("[{}]订单状态失败,重新尝试下一个供应商",wrap);
			wrap.setQueueOrder(new QueueOrder());
			queueMessageProducer.sendQueueOrder(wrap);
		} else {
			LoggerUtil.sys.info("[{}#{}]订单没有下一个供应商或获取配置失败,{}-{}",
					order.showSimpleIdLog(),order.getSupplier_sort(),
					config.getError().getMessage());
			queueMessageProducer.sendDistributorPush(wrap);
			// 发送订单结束消息
			wrap.getOrder().setState(Order.STATE_PROCESS_OVER);
			orderModuleMessageProducer.send(wrap);
		}
	}

	public void supplierReportSuccess(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			LoggerUtil.sys.info("[{}]订单状态成功",wrap);
			orderModuleMessageProducer.send(wrap);
			// 发送订单结束消息
			wrap.getOrder().setState(Order.STATE_PROCESS_OVER);
			orderModuleMessageProducer.send(wrap);
		} finally {
			queueMessageProducer.sendDistributorPush(wrap);
			logMessageProducer.sendLog(getSupplierReportLog(wrap, resp));
		}
	}

	private LogQueueSupplierReport getSupplierReportLog(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp) {
		Order order = wrap.getOrder();
		QueueOrder queue = wrap.getQueueOrder();
		InfoSku sku = null;
		try {
			sku = wrap.getConfiguredBindBean().getBindDistributor().getSku();
		} catch (Exception e) {
		}
		LogQueueSupplierReport log = new LogQueueSupplierReport();
		
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
		log.setRetries(queue.getRetries());
		log.setHttp_status_code(Integer.toString(resp.getInterfaceStatusCode()));
		log.setHttp_if_code(resp.getResultCode());
		log.setHttp_if_message(resp.getResultMsg());
		return log;
	}
	
	public StatusCode getStatusCode() {
		return statusCode;
	}

}
