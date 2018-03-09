package com.shziyuan.flow.cnfmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@MapperScan("com.shziyuan.flow.cnfmanager.dao")
public class FlowConfigManagermentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowConfigManagermentApplication.class, args);
	}
}
