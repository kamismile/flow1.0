package com.shziyuan.support.schedule.wrap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.shziyuan.support.schedule.dao.ScheduleConfDao;
import com.shziyuan.support.schedule.service.CronScheduleContext;
import com.shziyuan.support.schedule.service.task.Task;

@Service
public class ScheduleConfWrap {
	@Autowired
	private ScheduleConfDao scheduleConfDao;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public List<CronScheduleContext> selectAll() {
		List<CronScheduleContext> list = scheduleConfDao.selectAll();
		for(CronScheduleContext ctx : list) {
			Task task = applicationContext.getBean(ctx.getTaskClass(),Task.class);
			ctx.create(task);
		}
		return list;
	}
	
	public void update(CronScheduleContext ctx) {
		scheduleConfDao.update(ctx);
	}
	
	public void insert(CronScheduleContext ctx) {
		scheduleConfDao.insert(ctx);
	}
	
	public void delete(CronScheduleContext ctx) {
		scheduleConfDao.delete(ctx);
	}
}
