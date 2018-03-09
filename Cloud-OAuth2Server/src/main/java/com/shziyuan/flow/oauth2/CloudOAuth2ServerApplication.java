package com.shziyuan.flow.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class CloudOAuth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudOAuth2ServerApplication.class, args);
	}
}
