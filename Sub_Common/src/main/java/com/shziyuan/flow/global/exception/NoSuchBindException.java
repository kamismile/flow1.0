package com.shziyuan.flow.global.exception;

public class NoSuchBindException extends RuntimeException{

	private static final long serialVersionUID = -1753057864332346734L;

	private String distributorCode;
	private String sku;
	
	public NoSuchBindException(String distributorCode,String sku) {
		super("不存在对应[" + distributorCode + "][" + sku + "]的渠道绑定信息");
		this.distributorCode = distributorCode;
		this.sku = sku;
	}
	
	public NoSuchBindException(String sku) {
		super("不存在对应[" + sku + "]的供应商绑定信息");
		this.sku = sku;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public String getSku() {
		return sku;
	}
	
}
