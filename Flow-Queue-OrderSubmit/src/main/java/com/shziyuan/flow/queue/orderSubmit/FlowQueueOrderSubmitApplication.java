package com.shziyuan.flow.queue.orderSubmit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowQueueOrderSubmitApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FlowQueueOrderSubmitApplication.class);
		application.run(args);
	}
}
