package com.shziyuan.support.schedule.service.history;

/**
 * 任务运行历史
 * @author james.hu
 *
 */
public interface ScheduleHistory {
	public void runningLog(long startTime,long endTime,boolean success,Object result);
}
