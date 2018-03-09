package com.ziyuan.web.manager.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shziyuan.flow.global.util.TimestampUtil;

public class QueueBaseBean implements Serializable{
	private static final long serialVersionUID = -328072521781929116L;
	
	protected int id;
	protected String client_order_no;
	protected String sku_mask;
	protected String distributor_code;
	protected String phone;
	protected int pkg_type;
	protected int provinceid;
	protected String order_no;
	protected String source;
	protected String consumer;
	protected long connection_id;
	protected int status;
	protected Timestamp process_time;
	protected Timestamp insert_time;
	protected Timestamp send_time;
	protected int retries;
	protected String notify_url;
	protected int supplier_sort;
	protected int supplier_id;
	protected int supplier_code_id;
	protected int fee_type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public long getConnection_id() {
		return connection_id;
	}
	public void setConnection_id(long connection_id) {
		this.connection_id = connection_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getProcess_time() {
		return process_time;
	}
	public void setProcess_time(Timestamp process_time) {
		this.process_time = process_time;
	}
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}
	public Timestamp getSend_time() {
		return send_time;
	}
	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}
	public int getRetries() {
		return retries;
	}
	public void setRetries(int retries) {
		this.retries = retries;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public int getSupplier_sort() {
		return supplier_sort;
	}
	public void setSupplier_sort(int supplier_sort) {
		this.supplier_sort = supplier_sort;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public int getSupplier_code_id() {
		return supplier_code_id;
	}
	public void setSupplier_code_id(int supplier_code_id) {
		this.supplier_code_id = supplier_code_id;
	}
	public int getFee_type() {
		return fee_type;
	}
	public void setFee_type(int fee_type) {
		this.fee_type = fee_type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
