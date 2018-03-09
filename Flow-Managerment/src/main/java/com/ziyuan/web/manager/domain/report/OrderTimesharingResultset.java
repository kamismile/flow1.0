package com.ziyuan.web.manager.domain.report;

public class OrderTimesharingResultset {
	private String p_day;
	private Integer p_hour;
	private String distributor_name;
	private Double price;
	public String getP_day() {
		return p_day;
	}
	public void setP_day(String p_day) {
		this.p_day = p_day;
	}
	public Integer getP_hour() {
		return p_hour;
	}
	public void setP_hour(Integer p_hour) {
		this.p_hour = p_hour;
	}
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
