package com.shziyuan.flow.global.util;

import java.util.Map;

import org.springframework.context.ApplicationContext;

public class SpringUtil {
	private static ApplicationContext applicationContext;
	
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	public static <T> T getBean(String name,Class<T> clazz) {
		return applicationContext.getBean(name,clazz);
	}
	
	public static <T> Map<String,T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}
	
	

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
