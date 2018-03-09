package com.shziyuan.flow.oauth2.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * OAUTH2 资源服务配置
 * @author james.hu
 *
 */
@Configuration
@EnableResourceServer
public class ResourceConfiguraion extends ResourceServerConfigurerAdapter{
	public static final String RESOURCE_ID = "user-service";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()		// 关闭csrf
		
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .requestMatchers().anyRequest()
        .and()
        .anonymous()
        .and()
        
		.exceptionHandling()
		.accessDeniedHandler(new OAuth2AccessDeniedHandler())
//		.and()
//		.requestMatchers().antMatchers("/me","/test")
		.and()
		.authorizeRequests()
//		.antMatchers("/oauth/**").permitAll()
		.antMatchers("/me").authenticated();
	}
	
	
}
