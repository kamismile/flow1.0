package com.shziyuan.flow.global.executor.exception;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * 线程池 内部任务 异常处理器
 * @author james.hu
 *
 */
public class NormallyExceptionHandler implements Thread.UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LoggerUtil.error.error(t.getName(),e);
	}

}
