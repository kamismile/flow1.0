package com.shziyuan.support.schedule.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.shziyuan.support.schedule.service.task.Task;
import com.shziyuan.support.schedule.wrap.ScheduleConfWrap;

/**
 * 计划任务管理器
 * @author james.hu
 *
 */
@Service
@DependsOn("groovyFactory")
public class ScheduleManager {
	// 任务执行线程池
	@Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	// 任务列表
	private List<CronScheduleContext> contextMap;
	
	// dao
	@Autowired
	private ScheduleConfWrap scheduleConfWrap;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void init() {
		this.contextMap = scheduleConfWrap.selectAll();
	}
	
	/**
	 * 获取所有计划
	 * @return
	 */
	public List<CronScheduleContext> getAll() {
		return contextMap;
	}
	
	/**
	 * 获取已加载到的groovy任务类
	 * @return
	 */
	public String[] getGroovyTasks() {
		return applicationContext.getBeanNamesForType(Task.class);
	}
	
	/**
	 * 启动指定计划
	 * @param id
	 * @param override 是否强制重启
	 * @return
	 */
	public boolean start(int id,boolean override) {
		AbstractScheduleContext ctx = contextMap.stream().filter(asc -> asc.getId() == id).findFirst().get();
		return ctx.start(threadPoolTaskScheduler, override);
	}
	
	/**
	 * 启动所有计划
	 * @param override
	 */
	public void startAll(boolean override) {
		contextMap.forEach(ctx -> ctx.start(threadPoolTaskScheduler, override));
	}
	
	/**
	 * 停止指定计划
	 * @param id
	 * @return
	 */
	public boolean stop(int id) {
		AbstractScheduleContext ctx = contextMap.stream().filter(asc -> asc.getId() == id).findFirst().get();
		
		return ctx.stop();
	}
	
	/**
	 * 停止所有计划
	 */
	public void stopAll() {
		contextMap.forEach(ctx -> ctx.stop());
	}
	
	/**
	 * 更新指定计划信息
	 * @param id
	 * @param name 名称
	 * @param cronStr cron字符串
	 * @param tags 标签
	 * @param override 完成后启动
	 * @return
	 */
	public boolean update(int id,String name,String cronStr,String tags,boolean override) {
		AbstractScheduleContext ctx = contextMap.stream().filter(asc -> asc.getId() == id).findFirst().get();
		
		if(ctx instanceof CronScheduleContext) {
			// 获取对应计划
			CronScheduleContext csc = (CronScheduleContext) ctx;
//			csc.setId(id);
			// 设置属性
			if(name != null)
				csc.setName(name);
			if(cronStr != null)
				csc.setCronStr(cronStr);
			if(tags != null)
				csc.setTags(Arrays.stream(tags.split(",")).collect(Collectors.toList()));
			
			// 更新数据库配置表
			scheduleConfWrap.update(csc);
			
			// 完成后启动
			if(override) {
				start(id, true);
			}
			return true;
		} else
			return false;
	}
	
	/**
	 * 新建计划
	 * @param name 名称
	 * @param cronStr cron字符串
	 * @param taskClass groovy任务类名
	 * @param tags 标签
	 * @param override 完成后启动
	 * @return
	 */
	public boolean insert(String name,String cronStr,String taskClass,String tags,boolean override) {
		//新建计划信息
		CronScheduleContext ctx = new CronScheduleContext();
		ctx.setName(name);
		ctx.setCronStr(cronStr);
		ctx.setTaskClass(taskClass);
		ctx.setTags(Arrays.asList(tags.split(",")));
		
		// 插入数据库配置表
		scheduleConfWrap.insert(ctx);
		
		// 获取指定任务类
		Task task = applicationContext.getBean(ctx.getTaskClass(),Task.class);
		// 创建计划
		ctx.create(task);
		
		// 加入缓存表
		contextMap.add(ctx);
		
		// 完成后启动
		if(override)
			start(ctx.getId(), true);
		
		return true;
	}
	
	/**
	 * 删除指定任务
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		AbstractScheduleContext ctx = contextMap.stream().filter(asc -> asc.getId() == id).findFirst().get();
		
		if(ctx instanceof CronScheduleContext) {
			CronScheduleContext csc = (CronScheduleContext) ctx;
			csc.stop();
			scheduleConfWrap.delete(csc);
			contextMap.removeIf(c -> c.getId() == id);
			return true;
		} else
			return false;
	}
}
