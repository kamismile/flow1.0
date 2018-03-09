package com.shziyuan.flow.queue.orderSubmit.service.executor;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.base.interactive.ISupplierInterface;

@Deprecated
public class SupplierSubmitTask implements Callable<BaseInterfaceRequestResponse>{

	private QueueOrderMQWrap wrap;
	private ISupplierInterface supplierInterface;
	
	public static final String ERRORCODE_ON_SUBMIT = "SERR";
	
	public SupplierSubmitTask(QueueOrderMQWrap wrap,ISupplierInterface supplierInterface) {
		try {
			this.wrap = JsonUtil.clone(wrap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.supplierInterface = supplierInterface;
	}

	public QueueOrderMQWrap getWrap() {
		return wrap;
	}

	public ISupplierInterface getSupplierInterface() {
		return supplierInterface;
	}
	
	@Override
	public BaseInterfaceRequestResponse call() throws Exception {
		return null;

	}
}
