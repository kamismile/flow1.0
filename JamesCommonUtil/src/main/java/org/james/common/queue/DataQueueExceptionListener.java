package org.james.common.queue;

public interface DataQueueExceptionListener<T> {
	/**
	 * 队列超出长度报警
	 * @param nowQueueSize
	 * @param alertSize
	 */
	public void onQueueSizeAlert(int nowQueueSize,int alertSize);
	
	/**
	 * 插入数据失败报警
	 * @param obj
	 */
	public void onOfferException(T obj);
	
	/**
	 * 线程执行异常
	 */
	public void onRunningException(Exception e);
}
