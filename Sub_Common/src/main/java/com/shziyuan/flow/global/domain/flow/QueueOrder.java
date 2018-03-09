package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;

import com.shziyuan.flow.global.util.TimestampUtil;

public class QueueOrder {
	private Timestamp create_time;
	private Timestamp schedule_time;
	private int retries;
	private int orderState;
	
	private int manualCommand = CMD_NORMALIZING;
	
	public static final int CMD_NORMALIZING = 0xFF;
	public static final int CMD_ORDER_PUSH_FAILD = 0x00;
	public static final int CMD_ORDER_PUSH_SUCCESS = 0x01;
	public static final int CMD_ORDER_CACHE_RESUBMIT = 0x02;
		
	/**
	 * 初次生成使用
	 * @param notify_url
	 */
	public QueueOrder() {
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
	public QueueOrder(Timestamp schedule_time,int retries) {
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
	
	public void addRetries() {
		this.retries += 1;
	}

	public int getManualCommand() {
		return manualCommand;
	}

	public void setManualCommand(int manualCommand) {
		this.manualCommand = manualCommand;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	
}
