package com.shziyuan.flow.logwriter.dao;

import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;

/**
 * LogQueueOrderSubmit 日志DAO
 * @author james.hu
 *
 */
@Repository
public class LogQueueSupplierSubmitDao extends BaseInsertDao<LogQueueSupplierSubmit>{
	
	public LogQueueSupplierSubmitDao() {
		super("insert into log_queue_supplier_submit(client_order_no,sku_mask,distributor_code,phone,pkg_type,provinceid,order_no,source,consumer,report_mode,insert_time,sku_id,sku,supplier_id,supplier_name,supplier_code_id,supplier_code_name,standard_price,supplier_discount,supplier_price,distributor_discount,distributor_price,distributor_id,distributor_name,scope_cid,type_cid,retries,queue_id,http_status_code,http_if_code,http_if_message) values(:client_order_no,:sku_mask,:distributor_code,:phone,:pkg_type,:provinceid,:order_no,:source,:consumer,:report_mode,:insert_time,:sku_id,:sku,:supplier_id,:supplier_name,:supplier_code_id,:supplier_code_name,:standard_price,:supplier_discount,:supplier_price,:distributor_discount,:distributor_price,:distributor_id,:distributor_name,:scope_cid,:type_cid,:retries,:queue_id,:http_status_code,:http_if_code,:http_if_message)");
	}
}
