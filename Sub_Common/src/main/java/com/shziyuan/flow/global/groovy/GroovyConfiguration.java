package com.shziyuan.flow.global.groovy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;

@Configuration
public class GroovyConfiguration {
	@Bean
	public GroovyFactory groovyFactory() {
		return new GroovyFactory();
	}
	
	@Bean
	public ScriptFactoryPostProcessor scriptFactoryPostProcessor() {
		return new ScriptFactoryPostProcessor();
	}
}
