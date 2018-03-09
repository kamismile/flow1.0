package com.shziyuan.flow.queue.reportPush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.shziyuan.flow.mq.stream.FlowMqStreamBaseConfiguration;

@SpringBootApplication
@Import(FlowMqStreamBaseConfiguration.class)
public class FlowQueueReportPushApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowQueueReportPushApplication.class, args);
	}
}
