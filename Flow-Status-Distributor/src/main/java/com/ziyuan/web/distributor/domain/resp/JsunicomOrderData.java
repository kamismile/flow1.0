package com.ziyuan.web.distributor.domain.resp;

import java.io.Serializable;
import java.util.Map;

public class JsunicomOrderData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String partnerId;
	private String timeStamp;
	private String serviceName;
	private String encryptStr;
	private Map<String, String> param;
	
	private String remote_ip;
	private int provinceid;
	private String notify_url;
	
	
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public String getRemote_ip() {
		return remote_ip;
	}
	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getEncryptStr() {
		return encryptStr;
	}
	public void setEncryptStr(String encryptStr) {
		this.encryptStr = encryptStr;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}
}
