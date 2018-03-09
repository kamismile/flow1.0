package com.shziyuan.flow.global.http;

public class HttpProperties {
	private int connReqTimeout;
	private int connTimeout;
	private int socketTimeout;
	public int getConnReqTimeout() {
		return connReqTimeout;
	}
	public void setConnReqTimeout(int connReqTimeout) {
		this.connReqTimeout = connReqTimeout;
	}
	public int getConnTimeout() {
		return connTimeout;
	}
	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}
	public int getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
}
