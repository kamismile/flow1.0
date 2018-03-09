package com.shziyuan.flow.logwriter.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;
import com.shziyuan.flow.queue.base.conf.QueueBaseConfiguration;

/**
 * 供应商相关配置
 * @author james.hu
 *
 */
@Configuration
@Import({FlowMqStreamBaseConfiguration.class})
public class SystemConfiguration extends QueueBaseConfiguration{

	
}
