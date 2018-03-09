package com.ziyuan.web.order.domain;

import java.io.Serializable;

public class DistinctDistributorOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String distributor_order_no;

	public String getDistributor_order_no() {
		return distributor_order_no;
	}

	public void setDistributor_order_no(String distributor_order_no) {
		this.distributor_order_no = distributor_order_no;
	}

	public DistinctDistributorOrder(String distributor_order_no) {
		super();
		this.distributor_order_no = distributor_order_no;
	}
	
	

}
