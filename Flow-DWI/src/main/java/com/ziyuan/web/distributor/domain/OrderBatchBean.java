package com.ziyuan.web.distributor.domain;

import com.shziyuan.flow.global.domain.flow.LogOrderCharging;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;

public class OrderBatchBean {
	private QueueOrder queueOrder;
	private Order order;
	private ChargingBean cbean;
	private LogOrderCharging log;
	
	public OrderBatchBean(QueueOrder queueOrder, Order order, ChargingBean cbean, LogOrderCharging log) {
		this.queueOrder = queueOrder;
		this.order = order;
		this.cbean = cbean;
		this.log = log;
	}
	public QueueOrder getQueueOrder() {
		return queueOrder;
	}
	public void setQueueOrder(QueueOrder queueOrder) {
		this.queueOrder = queueOrder;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public ChargingBean getCbean() {
		return cbean;
	}
	public void setCbean(ChargingBean cbean) {
		this.cbean = cbean;
	}
	public LogOrderCharging getLog() {
		return log;
	}
	public void setLog(LogOrderCharging log) {
		this.log = log;
	}
}
