package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class StatisticsSupplier implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1266071963333592732L;
	
	private int supplier_id;
    //日成功率
    private Float daily_rate;
    //消费总额
    private Double sales_total; 
    //日销量
    private Double sales_daily;
    //月销量
    private Double sales_monthly;
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Float getDaily_rate() {
		return daily_rate;
	}
	public void setDaily_rate(Float daily_rate) {
		this.daily_rate = daily_rate;
	}
	
	public Double getSales_total() {
		return sales_total;
	}
	public void setSales_total(Double sales_total) {
		this.sales_total = sales_total;
	}
	public Double getSales_daily() {
		return sales_daily;
	}
	public void setSales_daily(Double sales_daily) {
		this.sales_daily = sales_daily;
	}
	public Double getSales_monthly() {
		return sales_monthly;
	}
	public void setSales_monthly(Double sales_monthly) {
		this.sales_monthly = sales_monthly;
	}
    
    
}