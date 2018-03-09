package com.ziyuan.web.distributor.conf;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.shziyuan.flow.global.groovy.GroovyFactory;
import com.ziyuan.web.distributor.service.filter.IFilter;
import com.ziyuan.web.distributor.service.filter.LoadedGroovyFilter;

@Configuration
//@ImportResource(locations = "classpath:/",reader = GroovyConfigReader.class)
public class GroovyConfiguration {
	
	@Bean
	@Autowired
	public LoadedGroovyFilter loadedGroovyFilter(ApplicationContext applicationContext,GroovyFactory groovyFactory) {
		Map<String, IFilter> filters = applicationContext.getBeansOfType(IFilter.class);
		LoadedGroovyFilter loadedGroovyFilter = new LoadedGroovyFilter();
		filters.entrySet().stream().forEach(entry -> loadedGroovyFilter.addFilter(entry.getKey(), entry.getValue()));
		loadedGroovyFilter.buildFilters();
		return loadedGroovyFilter;
	}
}
