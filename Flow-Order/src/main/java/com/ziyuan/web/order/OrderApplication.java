package com.ziyuan.web.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.EnableJms;

import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import(FlowMqStreamBaseConfiguration.class)
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
