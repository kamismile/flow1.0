package com.ziyuan.web.distributor.domain.resp;

//响应数据
public class CCResponseData {
	private String status;
	private String orderNo;
	private String cstmOrderNo;
	private String errorDesc;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
