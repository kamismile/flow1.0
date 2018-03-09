package com.shziyuan.flow.queue.orderSubmit.service.order;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.shziyuan.flow.mq.stream.producer.LogMessageProducer;
import com.shziyuan.flow.mq.stream.producer.OrderModuleMessageProducer;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.redis.base.service.RedisBindConfigService;
import com.shziyuan.flow.redis.base.service.RedisOrderStatusService;
import com.shziyuan.flow.redis.passiveReport.service.RedisPassiveReportService;

@Service
public class OrderService {

	@Autowired
	private RedisBindConfigService redisBindConfigService;
	
	@Autowired
	private RedisOrderStatusService redisOrderStatusService;
	
	@Autowired
	private RedisPassiveReportService redisPassiveReportService;
		
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
	
	public void manualOrderFaild(QueueOrderMQWrap wrap) {
		try {
			wrap.getOrder().setState(Order.STATE_ACTION_MANUAL_PROCESS);
			wrap.setPlatformStatus(new ProcStatus(false, statusCode.getPlatform().getDisPushFailManual()));
			wrap.setInterfaceStatus(wrap.getPlatformStatus());
			LoggerUtil.sys.info("[{}]订单设置为手动失败",wrap);
			orderModuleMessageProducer.send(wrap);
			try {
				// 附加推送一条计费确认消息
				QueueOrderMQWrap feeWrap = JsonUtil.clone(wrap);
				feeWrap.getOrder().setState(Order.STATE_PROCESS_OVER);
				// platform status 已设
				orderModuleMessageProducer.send(feeWrap);
			} catch (IOException e) {
				LoggerUtil.error.error(e.getMessage(),e);
			}
		} finally {
			queueMessageProducer.sendDistributorPush(wrap);
		}
	}

	public void manualOrderSuccess(QueueOrderMQWrap wrap) {
		try {
			wrap.getOrder().setState(Order.STATE_ACTION_MANUAL_PROCESS);
			wrap.setPlatformStatus(new ProcStatus(true, statusCode.getPlatform().getDisPushSuccessManual()));
			wrap.setInterfaceStatus(wrap.getPlatformStatus());
			LoggerUtil.sys.info("[{}]订单设置为手动成功",wrap);
			orderModuleMessageProducer.send(wrap);
			try {
				// 附加推送一条计费确认消息
				QueueOrderMQWrap feeWrap = JsonUtil.clone(wrap);
				feeWrap.getOrder().setState(Order.STATE_PROCESS_OVER);
				// platform status 已设
				orderModuleMessageProducer.send(feeWrap);
			} catch (IOException e) {
				LoggerUtil.error.error(e.getMessage(),e);
			}
		} finally {
			queueMessageProducer.sendDistributorPush(wrap);
		}
	}

	public void orderSubmitFaild(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			LoggerUtil.http.debug("[{}]订单失败,发送消息到OrderModule",wrap);
			orderModuleMessageProducer.send(wrap);
			
			Order order = wrap.getOrder();
			order.addSupplier_sort();
			ConfiguredBindBean config = redisBindConfigService.getBind(
					order.getPhone(), order.getDistributor_code(), order.getSku(), order.getSupplier_sort());
			config.setBindSupplier((order.getFee_type() == FEE_TYPE.BANLANCE.val ? config.getBindSupplierNormal() : config.getBindSupplierPresent()));
			wrap.setConfiguredBindBean(config);
			if(config.isSuccess()) {
				LoggerUtil.sys.info("[{}]订单失败,重新尝试下一个供应商",wrap);
				wrap.setQueueOrder(new QueueOrder());
				queueMessageProducer.sendQueueOrder(wrap);
			} else {
				LoggerUtil.sys.info("[{}#{}]订单没有下一个供应商或获取配置失败,{}-{}",
						order.showSimpleIdLog(),order.getSupplier_sort(),
						config.getError().getMessage());
				queueMessageProducer.sendDistributorPush(wrap);
			}
		} finally {
			logMessageProducer.sendLog(getSupplierSubmitLog(wrap, resp));
		}
	}

	public void orderSubmitSuccess(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp,ProcStatus platformStatus,ProcStatus interfaceStatus) {
		try {
			wrap.setPlatformStatus(platformStatus);
			wrap.setInterfaceStatus(interfaceStatus);
			orderModuleMessageProducer.send(wrap);
			
			if(wrap.getConfiguredBindBean().getBindSupplier().getCurrentCode().getSupplier().getReport_mode() == InfoSupplier.REPORT_MODE_ACTIVE) {
				wrap.setQueueOrder(new QueueOrder());
				queueMessageProducer.sendQueueSupplierReport(wrap,0);
			} else {
				LoggerUtil.http.debug("[{}]订单 供应商配置为被动推送,等待推送信息",wrap);
				redisPassiveReportService.newPassiveReportOrder(resp.getPassiveRedisKey(), wrap);
			}
		} finally {
			logMessageProducer.sendLog(getSupplierSubmitLog(wrap, resp));
		}
	}

	public void cache(QueueOrderMQWrap wrap) {
		wrap.getOrder().setState(Order.STATE_CACHE);
		wrap.setPlatformStatus(new ProcStatus(false,statusCode.getPlatform().getCacheOrder()));
		wrap.setInterfaceStatus(new ProcStatus(false,statusCode.getDistributor().getReportHold()));
		
		LoggerUtil.http.debug("[{}]订单设置为缓存,发送消息到OrderModule",wrap);
		orderModuleMessageProducer.send(wrap);
	}

	private LogQueueSupplierSubmit getSupplierSubmitLog(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp) {
		Order order = wrap.getOrder();
		QueueOrder queue = wrap.getQueueOrder();
		InfoSku sku = null;
		try {
			sku = wrap.getConfiguredBindBean().getBindDistributor().getSku();
		} catch (Exception e) {
		}
		LogQueueSupplierSubmit log = new LogQueueSupplierSubmit();
		
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
