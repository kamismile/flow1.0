package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;

public class QueueOrderBean extends QueueBaseBean{
	private static final long serialVersionUID = 3912119255370204049L;

	public QueueOrderBean() {
	
	}
	public QueueOrderBean(DistributorOrder order,String orderNo,InfoSupplierBinded currentSupplier) {
		this.client_order_no = order.getClientOrderNo();
		this.sku_mask = order.getProductCode();
		this.distributor_code = order.getClientCode();
		this.phone = order.getPhone();
		this.pkg_type = Constant.PKG_TYPE.SKU.val;
		this.provinceid = order.getProvinceid();
		this.order_no = orderNo;
//		this.source = Constant.SYSTEM_MARK;
		this.connection_id = Constant.DEFAULT_CONNECTION_ID;
		this.consumer = currentSupplier.getPlatform_mark();
		this.status = Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val;
		this.retries = 0;
		this.notify_url = order.getNotifyUrl();
		this.supplier_id = currentSupplier.getId();
	}
	
	public QueueOrderBean(QueueSupplierReportActive report) {
		this.client_order_no = report.getClient_order_no();
		this.sku_mask = report.getSku_mask();
		this.distributor_code = report.getDistributor_code();
		this.phone = report.getPhone();
		this.pkg_type = report.getPkg_type();
		this.provinceid = report.getProvinceid();
		this.order_no = report.getOrder_no();
//		this.source = Constant.SYSTEM_MARK;
		this.connection_id = Constant.DEFAULT_CONNECTION_ID;
		this.status = Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val;
		this.supplier_sort = report.getSupplier_sort();
		this.notify_url = report.getNotify_url();
		this.retries = 0;
		this.supplier_id = report.getSupplier_id();
	}
}
