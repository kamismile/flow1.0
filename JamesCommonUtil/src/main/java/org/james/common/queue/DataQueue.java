package org.james.common.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.james.common.thread.BaseLoopThread;

public class DataQueue<T> extends BaseLoopThread{
	private BlockingQueue<T> queue;
	
	private DataQueueExceptionListener<T> listener;
	
	// 用于设置检测队列状态的延迟
	private static final int THREAD_EACH_CHECK_DELAY = 5000;
	// 用于队列大小的控制 及报警
	private int QUEUE_ALERT_SIZE = 1000;
	
	/**
	 * 数据队列,自身线程
	 * 默认检测间隔 5秒
	 */
	public DataQueue() {
		this(null);
	}
	/**
	 * 带有错误通知模块的队列
	 * @param listener
	 */
	public DataQueue(DataQueueExceptionListener<T> listener) {
		this(THREAD_EACH_CHECK_DELAY,listener);
	}
	public DataQueue(int delay,DataQueueExceptionListener<T> listener) {
		super(delay);
		this.queue = new LinkedBlockingDeque<T>();
		this.listener = listener;
	}
	
	public void doWhileAction() {
		if(queue.size() > QUEUE_ALERT_SIZE) {
			if(listener != null)
				listener.onQueueSizeAlert(this.queue.size(), QUEUE_ALERT_SIZE);
		}
	}
	
	/**
	 * 压入数据
	 * @param obj
	 */
	public void put(T obj) {
		if(!queue.offer(obj)) {
			if(listener != null)
				listener.onOfferException(obj);
		}
	}

	/**
	 * 获取数据
	 * @return
	 * @throws InterruptedException 
	 */
	public T take() throws InterruptedException {
		return queue.take();
	}

	@Override
	public void onRunningException(Exception e) {
		if(listener != null)
			listener.onRunningException(e);
	}
	
	// getter / setter
	public DataQueueExceptionListener<T> getListener() {
		return listener;
	}
	public void setListener(DataQueueExceptionListener<T> listener) {
		this.listener = listener;
	}
	public void setCheckDelay(int delay) {
		super.setDelay(delay);
	}
	public int getCheckDelay() {
		return super.getDelay();
	}
	public void setAlertSize(int size) {
		this.QUEUE_ALERT_SIZE = size;
	}
	public int getAlertSize() {
		return QUEUE_ALERT_SIZE;
	}
}
