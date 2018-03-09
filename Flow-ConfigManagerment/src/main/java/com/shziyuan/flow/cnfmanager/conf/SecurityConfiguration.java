package com.shziyuan.flow.cnfmanager.conf;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.shziyuan.flow.cnfmanager.conf.security.AjaxAuthenticationEntryPointWithRememberMe;
import com.shziyuan.flow.cnfmanager.conf.security.OAuth2AuthenticationFaildHandler;
import com.shziyuan.flow.cnfmanager.conf.security.OAuth2AuthenticationSuccessHandlerWithRememberMe;
import com.shziyuan.flow.cnfmanager.conf.security.OAuth2UserInfoTokenService;


/**
 * security配置
 * @author james.hu
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;		// oauth2客户端上下文 spring默认注入
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.eraseCredentials(false);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			
			.authorizeRequests()
			.antMatchers("/login","/html/**").permitAll()	// 过滤登录及静态页不需要认证
			.antMatchers("/login/oauth2").permitAll()		// oauth认证登录地址 不需要认证
			.antMatchers("/a").authenticated()				// 测试获取当前登录用户信息地址
			.antMatchers("/**").authenticated()
			.anyRequest().permitAll()
			
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index.html").permitAll()

			.and().addFilterBefore(sso(),BasicAuthenticationFilter.class)		// 加入oauth的filter

			.exceptionHandling().authenticationEntryPoint(ajaxAuthenticationEntryPoint()).and()		// 拦截ajax请求,防止进行302跳转
			
			.rememberMe().and()
			.formLogin().permitAll();
	}
	
	/**
	 * 用于拦截AJAX请求
	 * @return
	 */
	@Bean
	public AuthenticationEntryPoint ajaxAuthenticationEntryPoint() {
		return new AjaxAuthenticationEntryPointWithRememberMe("/html/loginPage.html");
	}
	
	/**
	 * OAuth2认证完成后的处理器
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return new OAuth2AuthenticationSuccessHandlerWithRememberMe();
	}
	
	/**
	 * OAuth2请求模板,Spring默认封装模板
	 * @return
	 */
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate() {
		return new OAuth2RestTemplate(ldapServer(), oauth2ClientContext);
	}
	
	/**
	 * OAuth2请求Token数据接口服务
	 * @return
	 */
	@Bean
	public ResourceServerTokenServices oAuth2UserInfoTokenService() {
		return new OAuth2UserInfoTokenService(ldapResource().getUserInfoUri(), ldapServer().getClientId());
	}
	
	/**
	 * 注册一个OAuth组件的Filter
	 * @return
	 */
	private Filter sso() {
		// OAuth客户端Filter 绑定到地址 /Login/oauth2
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login/oauth2");
        // 把模板加入filter
        filter.setRestTemplate(oAuth2RestTemplate());
        // 设置获取token的服务
        filter.setTokenServices(oAuth2UserInfoTokenService());
        
        filter.setAuthenticationSuccessHandler(oAuth2AuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new OAuth2AuthenticationFaildHandler());
        return filter;
    }

	/**
	 * 注册ldap服务器端信息,由配置文件读取 prefix=security.oauth2.client
	 * @return
	 */
    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public ResourceOwnerPasswordResourceDetails ldapServer() {
    	return new ResourceOwnerPasswordResourceDetails();
    }
    
    /**
     * 注册资源服务器信息,由配置文件读取 prefix=security.oauth2.resource
     * @return
     */
    @Bean
    @ConfigurationProperties("security.oauth2.resource")
    public ResourceServerProperties ldapResource() {
    	return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

	
	
}
