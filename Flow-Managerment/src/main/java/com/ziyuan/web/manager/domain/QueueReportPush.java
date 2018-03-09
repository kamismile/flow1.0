package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.common.Constant;

public class QueueReportPush extends QueueBaseBean{
private static final long serialVersionUID = -6370100024980017359L;
	
	private int sku_id;
	private String sku;
	private int supplier_id;
	private String supplier_name;
	private int supplier_code_id;
	private String supplier_code_name;
	private double standard_price;
	private double supplier_discount;
	private double supplier_price;
	private double distributor_discount;
	private double distributor_price;
	private int distributor_id;
	private String distributor_name;
	private String result_code;
	private String result_message;
	private String supplier_result;
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public int getSupplier_code_id() {
		return supplier_code_id;
	}
	public void setSupplier_code_id(int supplier_code_id) {
		this.supplier_code_id = supplier_code_id;
	}
	public String getSupplier_code_name() {
		return supplier_code_name;
	}
	public void setSupplier_code_name(String supplier_code_name) {
		this.supplier_code_name = supplier_code_name;
	}
	public double getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(double standard_price) {
		this.standard_price = standard_price;
	}
	public double getSupplier_discount() {
		return supplier_discount;
	}
	public void setSupplier_discount(double supplier_discount) {
		this.supplier_discount = supplier_discount;
	}
	public double getSupplier_price() {
		return supplier_price;
	}
	public void setSupplier_price(double supplier_price) {
		this.supplier_price = supplier_price;
	}
	public double getDistributor_discount() {
		return distributor_discount;
	}
	public void setDistributor_discount(double distributor_discount) {
		this.distributor_discount = distributor_discount;
	}
	public double getDistributor_price() {
		return distributor_price;
	}
	public void setDistributor_price(double distributor_price) {
		this.distributor_price = distributor_price;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_message() {
		return result_message;
	}
	public void setResult_message(String result_message) {
		this.result_message = result_message;
	}
	public String getSupplier_result() {
		return supplier_result;
	}
	public void setSupplier_result(String supplier_result) {
		this.supplier_result = supplier_result;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
