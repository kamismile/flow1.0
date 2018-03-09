package org.james.common.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class Mvc extends WebMvcConfigurerAdapter{
	@Bean
	public InternalResourceViewResolver viewJsp() {
		InternalResourceViewResolver jspResolver = new InternalResourceViewResolver();
		jspResolver.setViewClass(JstlView.class);
		jspResolver.setSuffix(".jsp");
		jspResolver.setPrefix("/WEB-INF/jsp/");
		return jspResolver;
	}
	/*
	@Bean
	public CustomDomainArgumentResolver customDomainArgumentResolver() {
		return new CustomDomainArgumentResolver();
	}
	
	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(customDomainArgumentResolver());
	}
	
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.dateFormat(dateFormat);
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
	}
	*/
}
