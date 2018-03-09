package com.shziyuan.flow.queue.reportPush.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.shziyuan.flow.global.http.rest.RestConnectionConfiguration;
import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;
import com.shziyuan.flow.queue.base.FlowQueueBaseConfiguration;
import com.shziyuan.flow.queue.base.conf.QueueBaseConfiguration;

/**
 * 供应商相关配置
 * @author james.hu
 *
 */
@Configuration
@Import({RestConnectionConfiguration.class,
	FlowQueueBaseConfiguration.class,FlowMqStreamBaseConfiguration.class})
public class SupplierConfiguration extends QueueBaseConfiguration{

	
}
