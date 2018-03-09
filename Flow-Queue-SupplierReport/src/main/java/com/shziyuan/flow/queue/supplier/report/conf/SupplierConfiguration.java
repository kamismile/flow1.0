package com.shziyuan.flow.queue.supplier.report.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.shziyuan.flow.global.groovy.GroovyConfiguration;
import com.shziyuan.flow.global.http.rest.RestConnectionConfiguration;
import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;
import com.shziyuan.flow.queue.base.FlowQueueBaseConfiguration;
import com.shziyuan.flow.queue.base.conf.QueueBaseConfiguration;
import com.shziyuan.flow.redis.base.FlowRedisBaseConfiguration;
import com.shziyuan.flow.redis.passiveReport.FlowPassiveReportRedisConfiguration;

/**
 * 供应商相关配置
 * @author james.hu
 *
 */
@Configuration
@Import({GroovyConfiguration.class , RestConnectionConfiguration.class,
	FlowQueueBaseConfiguration.class,FlowMqStreamBaseConfiguration.class,
	FlowRedisBaseConfiguration.class,FlowPassiveReportRedisConfiguration.class})
public class SupplierConfiguration extends QueueBaseConfiguration{

	
}
