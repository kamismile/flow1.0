package com.shziyuan.flow.oauth2.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.shziyuan.flow.oauth2.service.LDAPNativeService;

/**
 * security配置
 * @author james.hu
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()		// 关闭csrf
		
		.requestMatchers().anyRequest()
		.and()
		.authorizeRequests()
			.antMatchers("/oauth/**").permitAll();		// 暴露oauth路径,请求oauth方法不需要权限
	}
	
	@Autowired
	private LDAPProperties ldapProperties;		// ldap参数
	
	/**
	 * 注册LDAP查询服务
	 * @return
	 */
	@Bean
	public LDAPNativeService ldapNativeService(){
		return new LDAPNativeService(ldapProperties);
	}
	
	/**
	 * 注册LDAP查询
	 */
	@Override
	@Bean
	public LDAPUserDetailsService userDetailsService() {
		return new LDAPUserDetailsService(ldapNativeService());
	}
	
	/**
	 * 注册LDAP密钥认证
	 * @return
	 */
	@Bean
	public AuthenticationProvider ldapAuthencationProvider() {
		return new LDAPAuthencationProvider(userDetailsService());
	}
	
	/**
	 * 配置认证服务为LDAP
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ldapAuthencationProvider());
	}
	
	/**
	 * 暴露认证管理器供oauth使用
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
