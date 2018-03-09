package com.ziyuan.web.distributor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.wraped.PassiveSupplierReportMQWrap;
import com.shziyuan.flow.mq.stream.producer.QueueMessageProducer;

@Service
public class SupplierReportService {
	@Autowired
	private QueueMessageProducer queueMessageProducer;
	
	public void receiveSupplierReport(int supplier_id,String reportSource) {
		PassiveSupplierReportMQWrap wrap = new PassiveSupplierReportMQWrap();
		wrap.setSupplierId(supplier_id);
		wrap.setReportSource(reportSource);
		
		queueMessageProducer.sendQueueSupplierReportPassive(wrap);
	}
}
