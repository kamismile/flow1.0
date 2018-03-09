package com.shziyuan.flow.queue.base.supplier.event;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

public abstract class AbstractSupplierEventListener implements ISupplierEventListener{

	private ISupplierEventListener nextListener;
	
	@Override
	public void doNext(MessageEvent event, QueueOrderMQWrap wrap) {
		this.nextListener.on(event, wrap);
	}

	public void setNextListener(ISupplierEventListener nextListener) {
		this.nextListener = nextListener;
	}
}
