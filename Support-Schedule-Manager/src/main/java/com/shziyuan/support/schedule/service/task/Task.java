package com.shziyuan.support.schedule.service.task;

import com.shziyuan.support.schedule.service.history.ScheduleHistory;

/**
 * 任务标记用接口
 * @author james.hu
 *
 */
public interface Task extends Runnable{
	public void setHistoryHandler(ScheduleHistory history);
}
