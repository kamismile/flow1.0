package com.shziyuan.flow.dbnotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
public class FlowDbNotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowDbNotifyApplication.class, args);
	}
	
	
}
