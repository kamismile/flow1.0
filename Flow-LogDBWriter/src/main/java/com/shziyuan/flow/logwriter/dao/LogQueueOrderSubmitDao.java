package com.shziyuan.flow.logwriter.dao;

import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.LogQueueOrderSubmit;

/**
 * LogQueueOrderSubmit 日志DAO
 * @author james.hu
 *
 */
@Repository
public class LogQueueOrderSubmitDao extends BaseInsertDao<LogQueueOrderSubmit>{
	
	public LogQueueOrderSubmitDao() {
		super("insert into log_queue_order_submit(insert_time,clientOrderNo,productCode,clientCode,phone,sign,remote_ip,provinceid,orderNo,resp_status,resp_message,notify_url) values(:insert_time,:clientOrderNo,:productCode,:clientCode,:phone,:sign,:remote_ip,:provinceid,:orderNo,:resp_status,:resp_message,:notify_url)");
	}
}
