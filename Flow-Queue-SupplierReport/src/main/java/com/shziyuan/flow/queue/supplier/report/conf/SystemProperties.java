package com.shziyuan.flow.queue.supplier.report.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.shziyuan.flow.global.domain.common.AbstractSystemProperties;

@Component
@ConfigurationProperties("system")
public class SystemProperties extends AbstractSystemProperties{
	
}
