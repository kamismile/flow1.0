package org.james.common.thread;

import org.james.common.util.Util;

/**
 * 线程基类
 * 固定间隔时间的循环线程
 * @author Allen Hu
 *
 */
public abstract class BaseLoopThread implements ThreadAction{
	public static final byte FLAG_INIT = 1;
	public static final byte FLAG_READY = 2;
	public static final byte FLAG_RUN = 3;
	public static final byte FLAG_DIE = 4;
	public static final byte FLAG_ENDED = 5;
	
	// 线程执行状态
	protected byte runFlag;
	
	// 上一次线程执行时间
	protected long lastExecuteTimestamp;
	
	// 线程执行间隔时间
	protected int delay;
	
	
	public BaseLoopThread(int delay) {
		runFlag = FLAG_INIT;
		
		init();
		
		this.delay = delay;
		
		runFlag = FLAG_RUN;
	}
	
	/**
	 * 用于设置初始化
	 */
	protected void init() {
	}
	
	public void run() {
		lastExecuteTimestamp = System.currentTimeMillis();
		
		while(runFlag == FLAG_RUN) {
			try {
				doWhileAction();
			} catch (Exception e) {
				onRunningException(e);
			}
			
			lastExecuteTimestamp = System.currentTimeMillis();
			
			sleep();
		}
		
		runFlag = FLAG_ENDED;
	}
	
	protected void sleep() {
		if(delay > 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * 终止线程
	 */
	public void stopThread() {
		runFlag = FLAG_DIE;
//		Thread.currentThread().interrupt();
	}
	
	public boolean isEnded() {
		return runFlag == FLAG_ENDED;
	}
	
	public long getLastExecuteTimestamp() {
		return lastExecuteTimestamp;
	}
	
	/**
	 * 获取线程执行情况,返回值越大(超过2),线程可能已死
	 * @return
	 */
	public double getExecuteStatus() {
		if(delay == 0)
			return 0;
		else
			return (System.currentTimeMillis() - lastExecuteTimestamp) / delay;
	}
	
	
	@Override
	public String toString() {
		return getRunFlag() + "\t" + Util.parseFulltime(getLastExecuteTimestamp()) + "\t" + (getExecuteStatus() < 2 ? "live" : "Died");
	}
	
	public abstract void onRunningException(Exception e);

	public byte getRunFlag() {
		return runFlag;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}
