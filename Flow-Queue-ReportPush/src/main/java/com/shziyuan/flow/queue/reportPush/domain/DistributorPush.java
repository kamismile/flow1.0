package com.shziyuan.flow.queue.reportPush.domain;

import java.util.HashMap;
import java.util.Map;

import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.util.TimestampUtil;

/**
 * 渠道推送数据格式
 * 
 * @author james.hu
 *
 */
public class DistributorPush {

	private String orderNo;
	private String cstmOrderNo;
	private String status;
	private String msg;
	private String timeStamp;
	
	private Map<String,Object> data;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCstmOrderNo() {
		return cstmOrderNo;
	}

	public void setCstmOrderNo(String cstmOrderNo) {
		this.cstmOrderNo = cstmOrderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus(Status status) {
		this.status = status.getCode();
		this.msg = status.getMsg();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setTimeStamp() {
		this.timeStamp = TimestampUtil.nowString();
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void addData(String key,Object value) {
		if(this.data == null)
			this.data = new HashMap<>();
		this.data.put(key, value);
	}

}
