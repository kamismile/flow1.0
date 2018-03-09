package com.ziyuan.web.manager.domain;

import java.io.Serializable;

public class PageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -339875495398425501L;
	//分页
	private int page;
	private int rows;
	//供应商查询
	private String name;
	private Integer enabled;
	//供应商产品查询
	private int supplier_id;
	private String province;
	private String operator;
	private String sort;
	private String order;
	
	private int distributor_id;
	
	//产品查询
	private Integer provinceid;
	private Integer scope_cid;
	private float standard_price;
	private Integer skuid;
	public float getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(float standard_price) {
		this.standard_price = standard_price;
	}
	public Integer getSkuid() {
		return skuid;
	}
	public void setSkuid(Integer skuid) {
		this.skuid = skuid;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}
	public Integer getScope_cid() {
		return scope_cid;
	}
	public void setScope_cid(Integer scope_cid) {
		this.scope_cid = scope_cid;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	
}
