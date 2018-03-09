package com.ziyuan.web.distributor.domain.resp;

import com.shziyuan.flow.global.domain.common.Status;

//响应数据
public class CCResponse {
	
	private String code;
	private String msg;
	private Object data;
		
	public void status(Status status) {
		this.code = status.getCode();
		this.msg = status.getMsg();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
    
}
