package com.ziyuan.web.distributor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients //feign使用声明
@Import(FlowMqStreamBaseConfiguration.class)
public class DwiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwiApplication.class, args);
	}
}
