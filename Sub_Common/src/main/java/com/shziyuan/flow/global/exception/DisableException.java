package com.shziyuan.flow.global.exception;

import java.util.Arrays;

import com.shziyuan.flow.global.domain.flow.BaseDomain;

public class DisableException extends RuntimeException{
	private static final long serialVersionUID = 331855055544713858L;

	private DisableType type;
	private BaseDomain instance;
	
	private Object[] params;
	
	public enum DisableType {
		TYPE_BIND_DISTRIBUTOR,TYPE_BIND_SUPPLIER,TYPE_DISTRIBUTOR,TYPE_SKU,TYPE_SUPPLIER,TYPE_SUPPLIER_CODE
	}
	
	public DisableException(DisableType type,BaseDomain instance) {
		super("类型[" + type.name() + "][" + instance.showname() + "]已被关闭");
		this.type = type;
		this.instance = instance;
	}
	
	public DisableException(DisableType type,Object... params) {
		super("类型[" + type.name() + "][" + Arrays.deepToString(params) + "]已被关闭");
		this.type = type;
		this.params = params;
	}

	public DisableType getType() {
		return type;
	}

	public BaseDomain getInstance() {
		return instance;
	}

	public Object[] getParams() {
		return params;
	}


}
