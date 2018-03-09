package com.shziyuan.flow.queue.base.supplier.event;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

public interface ISupplierEventListener {
	public void on(MessageEvent event,QueueOrderMQWrap wrap);
	
	public void doNext(MessageEvent event,QueueOrderMQWrap wrap);
}
