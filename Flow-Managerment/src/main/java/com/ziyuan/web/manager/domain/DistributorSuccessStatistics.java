package com.ziyuan.web.manager.domain;

import java.io.Serializable;
import java.util.Date;

public class DistributorSuccessStatistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String distributor_name;
	
	private int success;
	
	private int total;
	
	private Date create_time;

	public String getDistributor_name() {
		return distributor_name;
	}

	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
}
