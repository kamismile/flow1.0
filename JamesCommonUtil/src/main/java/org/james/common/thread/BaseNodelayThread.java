package org.james.common.thread;

/**
 * 线程基类
 * 无条件 无延迟 循环线程
 * @author Allen Hu
 *
 */
public abstract class BaseNodelayThread extends BaseLoopThread{

	public BaseNodelayThread() {
		super(0);
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
		}
		
		runFlag = FLAG_ENDED;
	}
}
