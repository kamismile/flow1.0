package com.shziyuan.support.schedule.service;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.support.schedule.service.history.SingleHistory;
import com.shziyuan.support.schedule.service.task.AbstractScheduleTask;
import com.shziyuan.support.schedule.service.task.Task;

/**
 * 计划任务上下文
 * @author james.hu
 *
 */
public abstract class AbstractScheduleContext {
	// 数据库主键
	private int id;
	
	// 任务名称
	private String name;
	
	// 任务类型
	private String taskClass;
	
	// 任务
	@JsonIgnore
	private transient Task task;
	
	// 执行future
	@JsonIgnore
	private transient ScheduledFuture<?> scheduledFuture;
	
	// 执行历史
	private SingleHistory history;
	
	// 任务标签
	private List<String> tags;
	
	public AbstractScheduleContext() {
	
	}
	
	public void create(Task task) {
		this.task = task;
		this.history = new SingleHistory();
		task.setHistoryHandler(history);
	}
	
	public abstract boolean start(ThreadPoolTaskScheduler threadPoolTaskScheduler,boolean override);
	
	public boolean stop() {
		try {
			if(scheduledFuture != null)
				scheduledFuture.cancel(true);
			return true;
		} catch (Exception e) {
			LoggerUtil.error.error(e.getMessage(),e);
			return false;
		}
	}
	
	public boolean isRunning() {
		return scheduledFuture != null ? !scheduledFuture.isCancelled() && !scheduledFuture.isDone() : false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(AbstractScheduleTask task) {
		this.task = task;
	}

	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}

	public SingleHistory getHistory() {
		return history;
	}

	public void setHistory(SingleHistory history) {
		this.history = history;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}
}
