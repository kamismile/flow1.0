package com.shziyuan.flow.global.executor;

import java.util.concurrent.ThreadFactory;

/**
 * 带有结果处理器的线程池包装
 * @author james.hu
 *
 * @param <T>
 */
public abstract class AbstractCompletionTaskExecutor<RESP> extends AbstractTaskExecutor<RESP>{

	protected CompletionExecutorService<RESP> submitPool;
	
	protected String executorName;
	
	public AbstractCompletionTaskExecutor(String executorName,IExecutorResultServiceFuture<RESP> future) {
		register(executorName, future);
	}
	
	protected AbstractCompletionTaskExecutor() {
		
	}
	
	protected CompletionExecutorService<RESP> createSubmitPool(ThreadFactory threadFactory,IExecutorResultServiceFuture<RESP> future) {
		return new CompletionExecutorService<>(threadFactory,future);
	}
		
	protected void register(String executorName,IExecutorResultServiceFuture<RESP> future) {
		this.executorName = executorName;
		
		logger.debug("产生{}处理线程池",executorName);
		ThreadFactory threadFactory = createThreadFactory();
		submitPool = createSubmitPool(threadFactory,future);
	}
	
	
	@Override
	public void destroy() throws Exception {
		logger.debug("销毁线程池 - {}处理",executorName);
		submitPool.destroy();
	}
	
	@Override
	public ICompletionExecutorService<RESP> getPool() {
		return submitPool;
	}
		
	public CompletionExecutorService<RESP> getCompletionPool() {
		return submitPool;
	}

	@Override
	public boolean isPoolFree() {
		return true;
	}
}
