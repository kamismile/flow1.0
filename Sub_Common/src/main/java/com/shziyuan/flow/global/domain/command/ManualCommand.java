package com.shziyuan.flow.global.domain.command;

import java.util.Map;

public class ManualCommand {
	public static final int CMD_NORMALIZING = 0xFF;
	public static final int CMD_ORDER_PUSH_FAILD = 0x00;
	public static final int CMD_ORDER_PUSH_SUCCESS = 0x01;
	public static final int CMD_ORDER_CACHE_RESUBMIT = 0x02;
	
	private long insertTime;
	private int action;
	private Map<String,Object> param;
	
	public long getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
