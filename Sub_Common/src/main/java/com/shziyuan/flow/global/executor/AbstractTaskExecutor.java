package com.shziyuan.flow.global.executor;

import java.util.concurrent.Callable;

import org.slf4j.Logger;

import com.shziyuan.flow.global.executor.exception.NormallyExceptionHandler;
import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 线程池基类
 * 声明线程组及默认异常处理器
 * @author james.hu
 *
 */
public abstract class AbstractTaskExecutor<RESP> implements IExecutor<RESP>{
	protected Logger logger = LoggerUtil.console;

	protected ThreadGroup DEFAULT_THREAD_GROUP = new ThreadGroup("PlatformThreadGroup");
	protected NormallyExceptionHandler normallyExceptionHandler = new NormallyExceptionHandler();
	
	protected DefaultThreadFactory createThreadFactory() {
		return createThreadFactory(normallyExceptionHandler);
	}
	protected DefaultThreadFactory createThreadFactory(Thread.UncaughtExceptionHandler exceptionHandler) {
		return new DefaultThreadFactory(DEFAULT_THREAD_GROUP, getClass().getName(),exceptionHandler);
	}

	@Override
	public void submit(Callable<RESP> task) {
		getPool().submitCompletionTask(task);
	}
}
