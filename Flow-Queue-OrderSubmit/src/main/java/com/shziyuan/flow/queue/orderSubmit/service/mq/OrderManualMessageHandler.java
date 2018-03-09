package com.shziyuan.flow.queue.orderSubmit.service.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.consumer.StreamOrderManualConsumer;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.supplier.event.MessageEvent.EVENT;
import com.shziyuan.flow.queue.orderSubmit.service.order.OrderService;

/**
 * 订单手动操作数据队列监听
 * @author james.hu
 *
 */
@Service
public class OrderManualMessageHandler extends StreamOrderManualConsumer{

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private QueueOrderRabbitMessageHandler messageHandler;
	
	@Override
	public String showQueueName() {
		return "[OrderSubmit]订单手动指定状态队列监听器";
	}
	
	@Override
	public void doInput(QueueOrderMQWrap message) {
		Order order = message.getOrder();
		QueueOrder queue = message.getQueueOrder();
		
		// 如果CMD_ORDER_PUSH_FAILD 作为失败推送
		if(queue.getManualCommand() == QueueOrder.CMD_ORDER_PUSH_FAILD) {
			LoggerUtil.sys.info("[{}]订单设置为手动推送失败",order.showSimpleLog());
			messageHandler.doEvent(EVENT.MessageOver,"订单设置为手动推送失败", message);
			orderService.manualOrderFaild(message);
		} else if(queue.getManualCommand() == QueueOrder.CMD_ORDER_PUSH_SUCCESS) {
			// 如果CMD_ORDER_PUSH_SUCESS 作为成功推送
			LoggerUtil.sys.info("[{}]订单设置为手动推送成功",order.showSimpleLog());
			messageHandler.doEvent(EVENT.MessageOver,"订单设置为手动推送成功", message);
			orderService.manualOrderSuccess(message);
		} else if(queue.getManualCommand() == QueueOrder.CMD_ORDER_CACHE_RESUBMIT) {
			// 如果CMD_ORDER_CACHE_RESUBMIT 作为新提交订单
			LoggerUtil.sys.info("[{}]订单设置为缓存手动提交",order.showSimpleLog());
			messageHandler.doEvent(EVENT.SupplierCacheResubmit,"订单设置为缓存手动提交", message);
			
			ConfiguredBindBean config = message.getConfiguredBindBean();
			BindDistributorBean dis = config.getBindDistributor();
			BindSupplierBean bindsup = (order.getFee_type() == FEE_TYPE.BANLANCE.val ? config.getBindSupplierNormal() : config.getBindSupplierPresent());
			InfoSupplier sup = bindsup != null ? bindsup.getCurrentCode().getSupplier() : null;
			InfoSupplierCodetable supcode = bindsup != null ? bindsup.getCurrentCode() : null;
			// 绕开基础流程检测,进行提交
			order.setState(Order.STATE_CACHE_RESUBMIT);
			BaseInterfaceRequestResponse resp = messageHandler.doSubmit(message,queue,order,dis,sup,supcode);
			
			messageHandler.doOver(message,resp);
			messageHandler.doEvent(EVENT.MessageOver, message);
		}
	}

}
