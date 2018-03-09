package com.shziyuan.flow.global.domain.common;

import java.util.Map;

public class ProcStatus extends Status{
	private boolean success;
	private boolean hold;
	protected Map<String, Object> remark;		// 备用附加参数

	public ProcStatus() {
	
	}
	
	public ProcStatus(boolean success,String code,String msg) {
		super(code, msg);
		setSuccess(success);
	}
	
	public ProcStatus(boolean success,String code,String msg,Map<String,Object> remark) {
		super(code, msg);
		setSuccess(success);
		setRemark(remark);
	}
	
	public ProcStatus(boolean success,Status status) {
		this(success,status.getCode(),status.getMsg());
	}
	
	public void hold(String code,String msg) {
		setSuccess(false);
		setHold(true);
		setCode(code);
		setMsg(msg);
	}
	
	public void success(String code,String msg) {
		setSuccess(true);
		setCode(code);
		setMsg(msg);
	}
	
	public void success(String code,String msg,Map<String,Object> remark) {
		setSuccess(true);
		setCode(code);
		setMsg(msg);
		setRemark(remark);
	}
	
	public void faild(String code,String msg) {
		setSuccess(false);
		setCode(code);
		setMsg(msg);
	}
	
	public void faild(String code,String msg,Map<String,Object> remark) {
		setSuccess(false);
		setCode(code);
		setMsg(msg);
		setRemark(remark);
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, Object> getRemark() {
		return remark;
	}

	public void setRemark(Map<String, Object> remark) {
		this.remark = remark;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

}
