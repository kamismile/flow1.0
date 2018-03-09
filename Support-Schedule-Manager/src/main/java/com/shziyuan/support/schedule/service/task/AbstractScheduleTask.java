package com.shziyuan.support.schedule.service.task;

import com.shziyuan.support.schedule.service.history.ScheduleHistory;

/**
 * 计划任务基类
 * @author james.hu
 *
 */
public abstract class AbstractScheduleTask implements Task{
	
	// 记录执行历史
	protected ScheduleHistory history;
		
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		try {
			doRun();
			long endTime = System.currentTimeMillis();
			history.runningLog(startTime, endTime, true, null);
		} catch (Exception e) {
			long endTime = System.currentTimeMillis();
			history.runningLog(startTime, endTime, false, e.getMessage());
		}
	}
	
	/**
	 * 由子类实现的具体业务逻辑
	 */
	protected abstract void doRun();

	/**
	 * 必须在使用前注入
	 * @param history
	 */
	public void setHistoryHandler(ScheduleHistory history) {
		this.history = history;
	}

}
