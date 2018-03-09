package com.ziyuan.web.distributor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
public class FlowZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowZuulGatewayApplication.class, args);
	}
	
	@RestController
    public class ServiceController {

        public static final String RESPONSE_BODY = "ResponseBody";

        @GetMapping("/dwi")
        public ResponseEntity<String> serviceA() {
            return ResponseEntity.ok(RESPONSE_BODY);
        }
    }
}
