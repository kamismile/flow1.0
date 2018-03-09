package com.shziyuan.flow.queue.base.supplier.event;

import java.util.Collection;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

public class SupplierEventChain {
	private ISupplierEventListener[] eventChain;
	
	private ISupplierEventListener firstEvent;
	
	public SupplierEventChain() {
		this.firstEvent = new LastEndedSupplierEventListener();
	}
	
	public SupplierEventChain(Collection<AbstractSupplierEventListener> eventListeners) {
		this(eventListeners.toArray(new AbstractSupplierEventListener[0]));
	}
		
	public SupplierEventChain(AbstractSupplierEventListener... eventListeners) {
		this.eventChain = eventListeners;
		this.firstEvent = eventListeners[0];
		
		int len = eventListeners.length;
		if(len > 1) {
			AbstractSupplierEventListener prevListener = eventListeners[0];
			for(int i = 1; i < len; ++i) {
				AbstractSupplierEventListener nowListener = eventListeners[i];
				prevListener.setNextListener(nowListener);
				prevListener = nowListener;
			}
			prevListener.setNextListener(new LastEndedSupplierEventListener());
		} else {
			eventListeners[0].setNextListener(new LastEndedSupplierEventListener());
		}
	}
	
	public void on(MessageEvent event,QueueOrderMQWrap wrap) {
		this.firstEvent.on(event, wrap);
	}

	public ISupplierEventListener[] getEventChain() {
		return eventChain;
	}
}
