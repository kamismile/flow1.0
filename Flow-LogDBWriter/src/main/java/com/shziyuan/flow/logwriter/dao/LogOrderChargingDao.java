package com.shziyuan.flow.logwriter.dao;

import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.LogOrderCharging;

/**
 * LogQueueOrderSubmit 日志DAO
 * @author james.hu
 *
 */
@Repository
public class LogOrderChargingDao extends BaseInsertDao<LogOrderCharging>{
	
	public LogOrderChargingDao() {
		super("insert into log_order_charging(order_no, distributor_id, distributor_name, distributor_banlance, distributor_credit, fee) VALUES (:order_no,:distributor_id,:distributor_name,:distributor_banlance,:distributor_credit,:fee)");
	}
}
