package com.shziyuan.flow.global.executor;

import java.util.concurrent.ThreadFactory;

/**
 * 单/多 线程池的包装
 * @author james.hu
 *
 * @param <T>
 */
public abstract class AbstractCompletionMultiThreadExecutor<RESP> extends AbstractCompletionTaskExecutor<RESP>{

	private int processThreadNum;
	
	public AbstractCompletionMultiThreadExecutor(String executorName,IExecutorResultServiceFuture<RESP> future,int processThreadNum) {
		this.processThreadNum = processThreadNum;
		
		super.register(executorName, future);
	}
	
	@Override
	protected CompletionExecutorService<RESP> createSubmitPool(ThreadFactory threadFactory,
			IExecutorResultServiceFuture<RESP> future) {
		return new CompletionExecutorService<RESP>(threadFactory,processThreadNum,future);
	}
	
}
