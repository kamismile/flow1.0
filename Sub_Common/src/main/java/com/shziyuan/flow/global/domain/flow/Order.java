package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;
import java.sql.Timestamp;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.common.Constant.FEE_TYPE;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.util.TimestampUtil;

public class Order extends BaseDomain implements Serializable{
	private static final long serialVersionUID = 1684163900031705812L;
	
	private int id;
	private String client_order_no;
	private String sku_mask;
	private String distributor_code;
	private String phone;
	private int pkg_type;
	private int provinceid;
	private String order_no;
	private Timestamp process_time;
	private Timestamp create_time;
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
	private String mark;
	private String notify_url;
	private int state;
	private String status_submit_code;
	private String status_submit_message;
	private String status_report_code;
	private String status_report_message;
	private int supplier_success = ORDER_HOLD;
	private String status_push_code;
	private String status_push_message;
	private String http_code_push;
	private String status_push;
	private int supplier_report_success = ORDER_HOLD;
	private int sku_provinceid;
	private int pkgsize;
	private int fee_type;
	private int supplier_sort;
	private int state_payment;
	private String platform_mark;
	
	public static final int STATE_DWI = 0x01;
	public static final int STATE_SUPPLIER_SUBMIT = 0x02;
	public static final int STATE_SUPPLIER_REPORT = 0x03;
	public static final int STATE_DISTRIBUTOR_PUSH = 0x04;
	public static final int STATE_PROCESS_OVER = 0X05;
	public static final int STATE_ACTION_MANUAL_PROCESS = 0x11;
	public static final int STATE_CACHE = 0x12;
	public static final int STATE_CACHE_RESUBMIT = 0x13;
	
	public static final int STATE_PUSH_NORELAY = 0x80000001;
	
	
	public static final int ORDER_SUCCESS = 80;
	public static final int ORDER_FAILD = 81;
	public static final int ORDER_HOLD = 92;
	
	public Order(ConfiguredBindBean bean, FEE_TYPE fee, String order_no, String client_order_no, String phone, int provinceid, String notify_url, BindSupplierBean currentSupplier) {
		this.client_order_no = client_order_no;
		this.sku_mask = bean.getBindDistributor().getSku().getSku();
		this.distributor_code = bean.getBindDistributor().getDistributor().getCode();
		this.phone = phone;
		this.pkg_type = Constant.PKG_TYPE.SKU.val;
		this.provinceid = provinceid;
		this.process_time = TimestampUtil.now();
		this.create_time = TimestampUtil.now();
		this.order_no = order_no;
		this.sku_id = bean.getBindDistributor().getSku().getId();
		this.sku = bean.getBindDistributor().getSku().getSku();
		this.supplier_id = currentSupplier.getCurrentCode().getSupplier().getId();
		this.supplier_name = currentSupplier.getCurrentCode().getSupplier().getName();
		this.supplier_code_id = currentSupplier.getCurrentCode().getId();
		this.supplier_code_name = currentSupplier.getCurrentCode().getName();
		this.standard_price = bean.getBindDistributor().getSku().getStandard_price();
		this.supplier_discount = currentSupplier.getCurrentCode().getDiscount();
		this.supplier_price = currentSupplier.getCurrentCode().getPrice();
		this.platform_mark = currentSupplier.getCurrentCode().getSupplier().getPlatform_mark();
		this.distributor_discount = bean.getBindDistributor().getDiscount();
		this.distributor_price = bean.getBindDistributor().getPrice();
		this.distributor_id = bean.getBindDistributor().getDistributor().getId();
		this.distributor_name = bean.getBindDistributor().getDistributor().getName();
		this.notify_url = notify_url;
		this.state = Constant.ORDER_STATE.DWI_DISTRIBUTOR_SUBMIT_ORDER.val;
		this.sku_provinceid = bean.getBindDistributor().getSku().getProvinceid();
		this.pkgsize = bean.getBindDistributor().getSku().getPkgsize();
		this.fee_type = fee.val;
		this.state_payment = Constant.ORDER_STATE_PAYMENT.ADVANCE.val;
	}
	
	public String showSimpleLog() {
		StringBuilder sb = new StringBuilder();
		sb.append(order_no).append("|").append(sku).append('|').append(distributor_name).append('|')
			.append(supplier_name).append('|').append(supplier_code_name).append('|').append(platform_mark);
		return sb.toString();
	}
	
	public String showSimpleIdLog() {
		StringBuilder sb = new StringBuilder();
		sb.append(order_no).append("|").append(sku).append('|')
			.append(distributor_name).append('-').append(distributor_code).append('|')
			.append(supplier_name).append('-').append(supplier_id).append('|')
			.append(supplier_code_name).append('-').append(supplier_code_id).append('|').append(platform_mark);
		return sb.toString();
	}

	
	public int getState_payment() {
		return state_payment;
	}

	public void setState_payment(int state_payment) {
		this.state_payment = state_payment;
	}

	public String getStatus_submit_code() {
		return status_submit_code;
	}

	public void setStatus_submit_code(String status_submit_code) {
		this.status_submit_code = status_submit_code;
	}

	public String getStatus_submit_message() {
		return status_submit_message;
	}

	public void setStatus_submit_message(String status_submit_message) {
		this.status_submit_message = status_submit_message;
	}

	public String getStatus_report_code() {
		return status_report_code;
	}

	public void setStatus_report_code(String status_report_code) {
		this.status_report_code = status_report_code;
	}

	public String getStatus_report_message() {
		return status_report_message;
	}

	public void setStatus_report_message(String status_report_message) {
		this.status_report_message = status_report_message;
	}
	public String getStatus_push_code() {
		return status_push_code;
	}

	public void setStatus_push_code(String status_push_code) {
		this.status_push_code = status_push_code;
	}

	public String getStatus_push_message() {
		return status_push_message;
	}

	public void setStatus_push_message(String status_push_message) {
		this.status_push_message = status_push_message;
	}

	public int getSupplier_sort() {
		return supplier_sort;
	}

	public void setSupplier_sort(int supplier_sort) {
		this.supplier_sort = supplier_sort;
	}
	
	public void addSupplier_sort() {
		this.supplier_sort += 1;
	}

	public int getFee_type() {
		return fee_type;
	}

	public void setFee_type(int fee_type) {
		this.fee_type = fee_type;
	}

	public Order() {
	
	}
		
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
	public Timestamp getProcess_time() {
		return process_time;
	}
	public void setProcess_time(Timestamp process_time) {
		this.process_time = process_time;
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public int getSupplier_report_success() {
		return supplier_report_success;
	}

	public void setSupplier_report_success(int supplier_report_success) {
		this.supplier_report_success = supplier_report_success;
	}

	public int getSku_provinceid() {
		return sku_provinceid;
	}

	public void setSku_provinceid(int sku_provinceid) {
		this.sku_provinceid = sku_provinceid;
	}

	public int getPkgsize() {
		return pkgsize;
	}

	public void setPkgsize(int pkgsize) {
		this.pkgsize = pkgsize;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getStatus_push() {
		return status_push;
	}

	public void setStatus_push(String status_push) {
		this.status_push = status_push;
	}

	public String getHttp_code_push() {
		return http_code_push;
	}

	public void setHttp_code_push(String http_code_push) {
		this.http_code_push = http_code_push;
	}

	public int getSupplier_success() {
		return supplier_success;
	}

	public void setSupplier_success(int supplier_success) {
		this.supplier_success = supplier_success;
	}

	public String getPlatform_mark() {
		return platform_mark;
	}

	public void setPlatform_mark(String platform_mark) {
		this.platform_mark = platform_mark;
	}
	
	
}
