package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shziyuan.flow.global.util.TimestampUtil;

@Deprecated
public class QueueBaseBean implements Serializable{
	private static final long serialVersionUID = -328072521781929116L;
	
	protected Timestamp create_time;
	protected Timestamp schedule_time;
	protected int retries;
	
	public static final int RETRIES_SET_FAILD = -1;
	public static final int RETRIES_SET_SUCCESS = -2;
	
	/**
	 * 初次生成使用
	 * @param notify_url
	 */
	protected QueueBaseBean() {
		this.create_time = TimestampUtil.now();
		this.schedule_time = TimestampUtil.now();
		this.retries = 0;
	}
	
	/**
	 * 生成定时队列时使用
	 * @param schedule_time
	 * @param retries
	 * @param notify_url
	 */
	protected QueueBaseBean(Timestamp schedule_time,int retries) {
		this.create_time = TimestampUtil.now();
		this.schedule_time = schedule_time;
		this.retries = retries;
	}
	
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getSchedule_time() {
		return schedule_time;
	}
	public void setSchedule_time(Timestamp schedule_time) {
		this.schedule_time = schedule_time;
	}
	public int getRetries() {
		return retries;
	}
	public void setRetries(int retries) {
		this.retries = retries;
	}
	
	public void putRetriesToFaild() {
		this.retries = RETRIES_SET_FAILD;
	}
	public void putRetriesToSuccess() {
		this.retries = RETRIES_SET_SUCCESS;
	}
	
	@JsonIgnore
	public boolean isRetriesFaild() {
		return this.retries == RETRIES_SET_FAILD;
	}
	@JsonIgnore
	public boolean isRetriesSuccess() {
		return this.retries == RETRIES_SET_SUCCESS;
	}
}
