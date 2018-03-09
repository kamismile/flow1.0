package com.ziyuan.web.manager.domain;
import java.io.Serializable;
import java.util.Date;


public class DistributorStatistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String distributor_name;
	private double sales;
	private double balance;
	private Date create_time;
	
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	

}
