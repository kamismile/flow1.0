package com.shziyuan.flow.global.executor;

import java.util.concurrent.Callable;
import org.springframework.beans.factory.DisposableBean;

/**
 * 线程池接口
 *  - 标识接口
 * @author james.hu
 *
 */
public interface IExecutor<RESP> extends DisposableBean{
	public void submit(Callable<RESP> task);
	
	public boolean isPoolFree();
	
	public ICompletionExecutorService<RESP> getPool();
}
