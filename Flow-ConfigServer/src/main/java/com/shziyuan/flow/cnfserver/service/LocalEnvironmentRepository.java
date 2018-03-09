package com.shziyuan.flow.cnfserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//@Component
public class LocalEnvironmentRepository implements EnvironmentRepository{

	@Override
	public Environment findOne(String application, String profile, String label) {
		Environment env = new Environment(application, StringUtils.commaDelimitedListToStringArray(profile),label,"1.0","state");
		
		Map<String,String> map = new HashMap<>();
		map.put("item", "value");
		env.add(new PropertySource(String.format("%s_%s_%s", application,profile,label), map));
		return env;
	}

}
