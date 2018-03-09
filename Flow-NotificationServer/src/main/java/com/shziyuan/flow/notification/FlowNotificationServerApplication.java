package com.shziyuan.flow.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class FlowNotificationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowNotificationServerApplication.class, args);
	}
	
}
