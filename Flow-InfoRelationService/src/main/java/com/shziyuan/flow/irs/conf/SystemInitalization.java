package com.shziyuan.flow.irs.conf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shziyuan.flow.irs.service.BuildRedisService;

@Component
public class SystemInitalization {
	@Autowired
	private BuildRedisService buildRedisService;
	
	/**
	 * 系统启动时从数据库加载配置
	 */
	@PostConstruct
	public void init() {
		buildRedisService.loadBindDistributor();
		buildRedisService.loadBindSupplier();
		buildRedisService.checkAndBuildRedisFirst();
	}
}
