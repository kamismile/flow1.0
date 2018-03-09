package com.shziyuan.flow.queue.orderSubmit.service.executor;

import com.shziyuan.flow.global.executor.IExecutorResultServiceFuture;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;

/**
 * 供应商提交处理结果 处理器
 * @author james.hu
 *
 */
@Deprecated
public class SupplierSubmitFuture implements IExecutorResultServiceFuture<BaseInterfaceRequestResponse>{

	
	@Override
	public void handler(BaseInterfaceRequestResponse resp) {
		
	}

	@Override
	public void onException(Exception e) {
		LoggerUtil.error.error(e.getMessage(),e);
	}

}
