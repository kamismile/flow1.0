package com.shziyuan.flow.queue.base.supplier.event;

public class MessageEvent {
	public enum EVENT {
		MessageIncome,MessageOver,
		SupplierCache,SupplierCacheResubmit,SupplierCheckFaild,SupplierSubmitTo,SupplierResponse,
		DistributorSubmitTo,DistributorResponse
	}
	
	
	private EVENT event;
	private Object param;
	
	public MessageEvent(EVENT event,Object param) {
		this.event = event;
		this.param = param;
	}
	public MessageEvent(EVENT event) {
		this(event,null);
	}
	public EVENT getEvent() {
		return event;
	}
	public void setEvent(EVENT event) {
		this.event = event;
	}
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	
}
