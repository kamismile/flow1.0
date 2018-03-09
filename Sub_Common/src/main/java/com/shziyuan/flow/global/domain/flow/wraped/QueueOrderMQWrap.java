package com.shziyuan.flow.global.domain.flow.wraped;

import com.shziyuan.flow.global.domain.common.ProcStatus;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;

public class QueueOrderMQWrap {
	
	private ConfiguredBindBean configuredBindBean;		// 记录配置信息
	private QueueOrder queueOrder;			// 处理队列情况
	private ProcStatus platformStatus;			// 当前平台处理结果
	private ProcStatus interfaceStatus;			// 当前接口处理结果
	private Order order;					// 订单信息
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public ConfiguredBindBean getConfiguredBindBean() {
		return configuredBindBean;
	}
	public void setConfiguredBindBean(ConfiguredBindBean configuredBindBean) {
		this.configuredBindBean = configuredBindBean;
	}
	public QueueOrder getQueueOrder() {
		return queueOrder;
	}
	public void setQueueOrder(QueueOrder queueOrder) {
		this.queueOrder = queueOrder;
	}
	public ProcStatus getPlatformStatus() {
		return platformStatus;
	}
	public void setPlatformStatus(ProcStatus platformStatus) {
		this.platformStatus = platformStatus;
	}
	public ProcStatus getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(ProcStatus interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	
	@Override
	public String toString() {
		return order.showSimpleLog();
	}
}
