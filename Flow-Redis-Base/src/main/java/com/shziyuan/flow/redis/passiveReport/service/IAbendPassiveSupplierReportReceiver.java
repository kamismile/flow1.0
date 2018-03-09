package com.shziyuan.flow.redis.passiveReport.service;

import org.springframework.data.redis.connection.MessageListener;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

public interface IAbendPassiveSupplierReportReceiver extends MessageListener{
	public void handler(QueueOrderMQWrap wrap);
}
