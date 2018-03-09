package com.shziyuan.flow.logwriter.dao;

import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.LogQueueReportPush;

/**
 * LogQueueOrderSubmit 日志DAO
 * @author james.hu
 *
 */
@Repository
public class LogQueueReportPushDao extends BaseInsertDao<LogQueueReportPush>{
	
	public LogQueueReportPushDao() {
		super("insert into log_queue_report_push(client_order_no,sku_mask,distributor_code,phone,pkg_type,provinceid,order_no,source,consumer,insert_time,sku_id,sku,supplier_id,supplier_name,supplier_code_id,supplier_code_name,standard_price,supplier_discount,supplier_price,distributor_discount,distributor_price,distributor_id,distributor_name,scope_cid,type_cid,result_code,supplier_result,sync_mode,retries,http_status,queue_id) values(:client_order_no,:sku_mask,:distributor_code,:phone,:pkg_type,:provinceid,:order_no,:source,:consumer,:insert_time,:sku_id,:sku,:supplier_id,:supplier_name,:supplier_code_id,:supplier_code_name,:standard_price,:supplier_discount,:supplier_price,:distributor_discount,:distributor_price,:distributor_id,:distributor_name,:scope_cid,:type_cid,:result_code,:supplier_result,:sync_mode,:retries,:http_status,:queue_id)");
	}
}
