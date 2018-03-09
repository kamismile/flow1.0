package com.ziyuan.web.distributor.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;

import com.shziyuan.flow.global.groovy.GroovyFactory;

@Configuration
public class GroovyFactoryLoadConfiguration {
	@Autowired
	private Environment environment;
	
	@Bean
	public GroovyFactory groovyFactory() {
		GroovyFactory gf = new GroovyFactory();
		List<String> directorys = new ArrayList<>(1);
		directorys.add("classpath:/" + environment.getProperty("system.platform.name", "cc"));
		gf.setDirectorys(directorys);
		return gf;
	}
	
	@Bean
	public ScriptFactoryPostProcessor scriptFactoryPostProcessor() {
		return new ScriptFactoryPostProcessor();
	}
}
