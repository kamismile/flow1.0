package org.james.common.thread;

/**
 * 线程动作标识接口
 * @author Allen Hu
 *
 */
public interface ThreadAction extends Runnable{
	/**
	 * 线程循环动作 由子类覆盖实现
	 * TODO 由子类覆盖实现
	 */
	public void doWhileAction();
	
	/**
	 * 通知线程停止
	 */
	public void stopThread();
	
	/**
	 * 测试线程是否已停止
	 * @return
	 */
	public boolean isEnded();
}
