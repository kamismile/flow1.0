package com.shziyuan.support.schedule.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.shziyuan.flow.global.util.LoggerUtil;

/**
 * Cron 形式任务上下文
 * @author james.hu
 *
 */
public class CronScheduleContext extends AbstractScheduleContext{
	// cron字符串
	private String cronStr;
	
	public CronScheduleContext() {
		
	}
	
	@Override
	public boolean start(ThreadPoolTaskScheduler threadPoolTaskScheduler,boolean override) {
		try {
			if(getScheduledFuture() == null) {

			} else if(override) {
				stop();
				setScheduledFuture(threadPoolTaskScheduler.schedule(getTask(), new CronTrigger(cronStr)));
			}
			return true;
		} catch (Exception e) {
			LoggerUtil.error.error(e.getMessage(),e);
			return false;
		}
	}
	
	public String getCronStr() {
		return cronStr;
	}

	public void setCronStr(String cronStr) {
		this.cronStr = cronStr;
	}

}
