package com.ziyuan.web.manager.domain;

import java.sql.Timestamp;

import com.shziyuan.flow.global.domain.flow.InfoDistributorBinded;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;


public class LogPlatformOrder {
	private String client_order_no;
	private String sku_mask;
	private String distributor_code;
	private String phone;
	private int pkg_type;
	private int provinceid;
	private String order_no;
	private String source;
	private String consumer;
	private int report_mode;
	private Timestamp insert_time;
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
	private int scope_cid;
	private int type_cid;
	private int retries;
	private int queue_id;
	private String status;
	private String status_message;
	
	public LogPlatformOrder() {
	
	}
	
	public LogPlatformOrder(QueueBaseBean queue,InfoDistributorBinded distributor,
			InfoSupplierBinded supplier,InfoSku sku,String status) {
		String[] statusArr = status.split(":");
		this.client_order_no = queue.getClient_order_no();
		this.sku_mask = queue.getSku_mask();
		this.distributor_code = queue.getDistributor_code();
		this.phone = queue.getPhone();
		this.pkg_type = queue.getPkg_type();
		this.provinceid = queue.getProvinceid();
		this.order_no = queue.getOrder_no();
		this.source = queue.getSource();
		this.consumer = queue.getConsumer();
		this.report_mode = supplier.getReport_mode();
		this.sku_id = sku.getId();
		this.sku = sku.getSku();
		this.supplier_id = supplier.getId();
		this.supplier_name = supplier.getName();
		this.supplier_code_id = supplier.getSupplierCode().getId();
		this.supplier_code_name = supplier.getSupplierCode().getName();
		this.standard_price = sku.getStandard_price();
		this.supplier_discount = supplier.getSupplierCode().getDiscount();
		this.supplier_price = supplier.getSupplierCode().getPrice();
		this.distributor_discount = distributor.getBind().getDiscount();
		this.distributor_price = distributor.getBind().getPrice();
		this.distributor_id = distributor.getId();
		this.distributor_name = distributor.getName();
		this.scope_cid = sku.getScope_cid();
		this.type_cid = sku.getType_cid();
		this.retries = queue.getRetries();
		this.queue_id = queue.getId();
		this.status = statusArr[0];
		this.status_message = statusArr[1];
	}
	
	public String getClient_order_no() {
		return client_order_no;
	}
	public void setClient_order_no(String client_order_no) {
		this.client_order_no = client_order_no;
	}
	public String getSku_mask() {
		return sku_mask;
	}
	public void setSku_mask(String sku_mask) {
		this.sku_mask = sku_mask;
	}
	public String getDistributor_code() {
		return distributor_code;
	}
	public void setDistributor_code(String distributor_code) {
		this.distributor_code = distributor_code;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPkg_type() {
		return pkg_type;
	}
	public void setPkg_type(int pkg_type) {
		this.pkg_type = pkg_type;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public int getReport_mode() {
		return report_mode;
	}
	public void setReport_mode(int report_mode) {
		this.report_mode = report_mode;
	}
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}
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
	public int getScope_cid() {
		return scope_cid;
	}
	public void setScope_cid(int scope_cid) {
		this.scope_cid = scope_cid;
	}
	public int getType_cid() {
		return type_cid;
	}
	public void setType_cid(int type_cid) {
		this.type_cid = type_cid;
	}
	public int getRetries() {
		return retries;
	}
	public void setRetries(int retries) {
		this.retries = retries;
	}
	public int getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(int queue_id) {
		this.queue_id = queue_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_message() {
		return status_message;
	}

	public void setStatus_message(String status_message) {
		this.status_message = status_message;
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
}
