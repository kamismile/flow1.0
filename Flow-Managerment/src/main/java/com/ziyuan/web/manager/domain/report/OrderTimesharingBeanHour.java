package com.ziyuan.web.manager.domain.report;

import java.io.Serializable;
import java.util.HashMap;

public class OrderTimesharingBeanHour extends HashMap<String, AtomicDouble> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer flag;
	
	public OrderTimesharingBeanHour(Integer flag) {
		super();
		this.flag = flag;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
