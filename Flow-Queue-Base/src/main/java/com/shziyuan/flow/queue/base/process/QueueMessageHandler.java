package com.shziyuan.flow.queue.base.process;

import java.util.Map;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.consumer.AbstractMessageConsumer;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.supplier.event.AbstractSupplierEventListener;
import com.shziyuan.flow.queue.base.supplier.event.MessageEvent;
import com.shziyuan.flow.queue.base.supplier.event.SupplierEventChain;
import com.shziyuan.flow.queue.base.supplier.event.MessageEvent.EVENT;

/**
 * 订单数据队列基础包装
 * @author james.hu
 *
 */
public abstract class QueueMessageHandler extends AbstractMessageConsumer<QueueOrderMQWrap> implements ApplicationContextAware,InitializingBean{
	private final int HANDLER_STATE;
	
	private SupplierEventChain supplierEventChain; 	// 扩展用,供应商处理事件通知
	
	private ApplicationContext applicationContext;
	
	public QueueMessageHandler(int state) {
		this.HANDLER_STATE = state;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			Map<String,AbstractSupplierEventListener> listenerMap = applicationContext.getBeansOfType(AbstractSupplierEventListener.class);
			this.supplierEventChain = new SupplierEventChain(listenerMap.values());
		} catch (Exception e) {
			this.supplierEventChain = new SupplierEventChain();
		}
	}
	
	public void doInput(QueueOrderMQWrap wrap) {
		QueueOrder queue = wrap.getQueueOrder();
		Order order = wrap.getOrder();
		ConfiguredBindBean config = wrap.getConfiguredBindBean();
		BindDistributorBean dis;
		InfoSupplier sup;
		InfoSupplierCodetable supcode;
		try {
			dis = config.getBindDistributor();
			BindSupplierBean bindsup = config.getBindSupplier();
			sup = bindsup.getCurrentCode().getSupplier();
			supcode = bindsup.getCurrentCode();
		} catch (Exception e) {
			try {
				LoggerUtil.error.error("[{}]解析配置信息失败,异常:{} 数据:{}",showQueueName(),e.getMessage(),JsonUtil.toJson(wrap));
			} catch (JsonProcessingException e1) {
				LoggerUtil.error.error(e1.getMessage(),e1);
			}
			throw new AmqpRejectAndDontRequeueException(e);
		}
		
		// 标记当前订单状态
		queue.setOrderState(order.getState());
		order.setState(HANDLER_STATE);
		doEvent(EVENT.MessageIncome, wrap);
		
		// 订单数据初始判断
		boolean isContinue = doIncome(wrap,queue,order,dis,sup,supcode);
		
		if(!isContinue) {
			doEvent(EVENT.MessageOver, wrap);
			return;
		}
		
		// 提交到实际供应商接口
		BaseInterfaceRequestResponse resp = doSubmit(wrap,queue,order,dis,sup,supcode);
		
		// 订单完成处理
		doOver(wrap,resp);
		doEvent(EVENT.MessageOver, wrap);
	}
	
	/**
	 * 由子类实现,订单数据初始判断
	 * @return 返回是否继续处理订单数据
	 */
	protected abstract boolean doIncome(QueueOrderMQWrap wrap,QueueOrder queue,Order order,BindDistributorBean dis,InfoSupplier sup,InfoSupplierCodetable supcode);
	
	/**
	 * 由子类实现,实际提交供应商的逻辑
	 * @return 供应商接口响应
	 */
	protected abstract BaseInterfaceRequestResponse doSubmit(QueueOrderMQWrap wrap,QueueOrder queue,Order order,BindDistributorBean dis,InfoSupplier sup,InfoSupplierCodetable supcode);
	
	/**
	 * 由子类实现,提交结果的处理
	 */
	protected abstract void doOver(QueueOrderMQWrap wrap,BaseInterfaceRequestResponse resp);
	
	public void doEvent(MessageEvent.EVENT event,QueueOrderMQWrap wrap) {
		supplierEventChain.on(new MessageEvent(event), wrap);
	}
	public void doEvent(MessageEvent.EVENT event,Object param,QueueOrderMQWrap wrap) {
		supplierEventChain.on(new MessageEvent(event,param), wrap);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
