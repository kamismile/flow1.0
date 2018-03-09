package com.ziyuan.web.manager.domain;

import java.util.List;

public class TreeBindBean {
	private int id;
	private String name;
	private String operator;
	private double discount_min;
	private int count;
	private List<TreeBindBean> childs;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDiscount_min() {
		return discount_min;
	}

	public void setDiscount_min(double discount_min) {
		this.discount_min = discount_min;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<TreeBindBean> getChilds() {
		return childs;
	}

	public void setChilds(List<TreeBindBean> childs) {
		this.childs = childs;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
