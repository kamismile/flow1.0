package com.ziyuan.web.manager.domain;

import java.io.Serializable;


public class ArchiveSupplierReportActiveBean extends ArchiveSupplierReportActive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double bid;
	private double standard;
	private String operator;
	private String province;
	private String scope_name;
	private String info_ptype;
	private String rent_type;
	private int pkgsize;
	
	
	public int getPkgsize() {
		return pkgsize;
	}
	public void setPkgsize(int pkgsize) {
		this.pkgsize = pkgsize;
	}
	
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getStandard() {
		return standard;
	}
	public void setStandard(double standard) {
		this.standard = standard;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getScope_name() {
		return scope_name;
	}
	public void setScope_name(String scope_name) {
		this.scope_name = scope_name;
	}
	public String getInfo_ptype() {
		return info_ptype;
	}
	public void setInfo_ptype(String info_ptype) {
		this.info_ptype = info_ptype;
	}
	public String getRent_type() {
		return rent_type;
	}
	public void setRent_type(String rent_type) {
		this.rent_type = rent_type;
	}
	
	

}
