package org.james.common.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * 子类需注解 @ComponentScan @Import
 * @author yaohu
 *
 */
@Configuration
public abstract class Application {
	
	@Bean(name = "messageSource")
	public ResourceBundleMessageSource getMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("res/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	@Bean(name = "localeResolver")
	public AcceptHeaderLocaleResolver getLocaleResolver() {
		return new AcceptHeaderLocaleResolver();
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource() {
		PropertySourcesPlaceholderConfigurer pc = new PropertySourcesPlaceholderConfigurer();
		pc.setLocation(new DefaultResourceLoader().getResource("classpath:/db.properties"));
		return pc;
	}
	
}
