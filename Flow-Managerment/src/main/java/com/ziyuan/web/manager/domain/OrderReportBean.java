package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.Order;

public class OrderReportBean extends Order {
	private static final long serialVersionUID = 1260716575824503824L;
	
	private String ctime;
	private int count;
	private double sales;
	private double cost;
	private double standard;
	
	private boolean subtotal = false;
	
	private String province;
	private String scopeName;
	private String renttypeName;
	
	private InfoSku infoSku;
	private InfoDistributor distributor;
	private InfoSupplier supplier;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getStandard() {
		return standard;
	}
	public void setStandard(double standard) {
		this.standard = standard;
	}
	
	public InfoSku getInfoSku() {
		return infoSku;
	}
	public void setInfoSku(InfoSku infoSku) {
		this.infoSku = infoSku;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public InfoDistributor getDistributor() {
		return distributor;
	}
	public void setDistributor(InfoDistributor distributor) {
		this.distributor = distributor;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getScopeName() {
		return scopeName;
	}
	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}
	public String getRenttypeName() {
		return renttypeName;
	}
	public void setRenttypeName(String renttypeName) {
		this.renttypeName = renttypeName;
	}
	public boolean isSubtotal() {
		return subtotal;
	}
	public void setSubtotal(boolean subtotal) {
		this.subtotal = subtotal;
	}
	public InfoSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(InfoSupplier supplier) {
		this.supplier = supplier;
	}

}
