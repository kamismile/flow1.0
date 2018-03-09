package com.shziyuan.flow.global.domain.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 用于加载config server中的状态码表
 * @author james.hu
 *
 */
public class StatusCodeLoaderConfiguration {
	
	@LoadBalanced
	@Bean
	@ConditionalOnMissingBean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Value("${spring.cloud.config.uri}")
	private String configServerUrl;			// configserver路径
	
	@Value("${spring.profiles.active}")
	private String activedProfiles;			// 指定加载的profile级别
	
	@Value("${system.platform.name}")
	private String platformName;			// 指定加载的平台名称 对应到git分支
	
	protected StatusCode loadStatusCode() {
		return loadStatusCode(configServerUrl, platformName, activedProfiles);
	}
	
	protected StatusCode loadStatusCode(String configServerUrl,String platformName,String activedProfiles) {
		RestTemplate rest = restTemplate();
		
		StatusCode statusCode = rest.getForObject(String.format("%s/%s/platformstatuscode-%s.json", configServerUrl,platformName,activedProfiles), StatusCode.class);
		
		return statusCode;
	}
}
