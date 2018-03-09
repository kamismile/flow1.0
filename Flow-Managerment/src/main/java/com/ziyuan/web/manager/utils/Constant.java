package com.ziyuan.web.manager.utils;

public enum Constant {
	
	SUBMIT_ORDER("submit-order"),
	IMPORTANT("important");
	
	public String type;
	
	private Constant(String type){
		this.type = type;
	}
}
