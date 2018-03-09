package com.ziyuan.web.manager.domain;

public class ArchiveSupplierReportActive extends QueueSupplierReportActive{
	private static final long serialVersionUID = -5931533951308002647L;

	public ArchiveSupplierReportActive() {
	}

	public ArchiveSupplierReportActive(QueueSupplierReportActive queue) {
		this.id = queue.getId();
		this.client_order_no = queue.getClient_order_no();
		this.sku_mask = queue.getSku_mask();
		this.distributor_code = queue.getDistributor_code();
		this.phone = queue.getPhone();
		this.pkg_type = queue.getPkg_type();
		this.provinceid = queue.getProvinceid();
		this.order_no = queue.getOrder_no();
		this.source = queue.getSource();
		this.consumer = queue.getConsumer();
		this.connection_id = queue.getConnection_id();
		this.status = queue.getStatus();
		this.process_time = queue.getProcess_time();
		this.insert_time = queue.getInsert_time();
		this.send_time = queue.getSend_time();
		this.retries = queue.getRetries();
		this.notify_url = queue.getNotify_url();
		this.supplier_sort = queue.getSupplier_sort();
		this.if_attribute = queue.getIf_attribute();
		this.sku_id = queue.getSku_id();
		this.sku = queue.getSku();
		this.supplier_id = queue.getSupplier_id();
		this.supplier_name = queue.getSupplier_name();
		this.supplier_code_id = queue.getSupplier_code_id();
		this.supplier_code_name = queue.getSupplier_code_name();
		this.standard_price = queue.getStandard_price();
		this.supplier_discount = queue.getSupplier_discount();
		this.supplier_price = queue.getSupplier_price();
		this.distributor_discount = queue.getDistributor_discount();
		this.distributor_price = queue.getDistributor_price();
		this.distributor_id = queue.getDistributor_id();
		this.distributor_name = queue.getDistributor_name();
		this.mark = queue.getMark();
	}

	
}
