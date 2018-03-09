package com.shziyuan.flow.irs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.shziyuan.flow.redis.base.FlowRedisBaseConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import(FlowRedisBaseConfiguration.class)
public class FlowInfoRelationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowInfoRelationServiceApplication.class, args);
	}
}
