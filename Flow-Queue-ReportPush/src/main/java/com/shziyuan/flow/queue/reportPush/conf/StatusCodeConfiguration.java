package com.shziyuan.flow.queue.reportPush.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.common.StatusCodeLoaderConfiguration;

/**
 * 用于加载状态码表
 * @author james.hu
 *
 */
@Configuration
public class StatusCodeConfiguration extends StatusCodeLoaderConfiguration{

	@Bean
	public StatusCode statusCode() {
		return loadStatusCode();
	}
}
