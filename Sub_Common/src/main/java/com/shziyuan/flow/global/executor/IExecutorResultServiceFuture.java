package com.shziyuan.flow.global.executor;

/**
 * HTTP处理结果处理器接口
 * @author james.hu
 *
 */
public interface IExecutorResultServiceFuture<RESP> {
	/**
	 * 处理线程池的结果
	 * @param pkg
	 */
	public void handler(RESP resp);
	
	public void onException(Exception e);
}
