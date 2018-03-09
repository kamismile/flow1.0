package com.ziyuan.web.manager.conf;

import javax.servlet.Filter;
import javax.sql.DataSource;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ziyuan.web.manager.conf.security.AjaxAuthenticationEntryPointWithRememberMe;
import com.ziyuan.web.manager.conf.security.DistributorUserDetailsService;
import com.ziyuan.web.manager.conf.security.OAuth2AuthenticationFaildHandler;
import com.ziyuan.web.manager.conf.security.OAuth2AuthenticationSuccessHandlerWithRememberMe;
import com.ziyuan.web.manager.conf.security.OAuth2UserInfoTokenService;
import com.ziyuan.web.manager.conf.security.PlatformAccessDeniedHandler;
import com.ziyuan.web.manager.conf.security.PlatformAuthencationProvider;

/**
 * Spring Security 配置
 * @author james.hu
 *
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;		// oauth2客户端上下文 spring默认注入
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(platformAuthencationProvider());
	}
	
	@Bean
	public PlatformAuthencationProvider platformAuthencationProvider() {
		return new PlatformAuthencationProvider(distributorUserDetailsService());
	}
	
	@Bean
	public DistributorUserDetailsService distributorUserDetailsService() {
		return new DistributorUserDetailsService();
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
//			.antMatchers("/**").permitAll()
			.antMatchers("/login","/html/**").permitAll()	// 过滤登录及静态页不需要认证
			.antMatchers("/login/oauth2").permitAll()		// oauth认证登录地址 不需要认证
			.antMatchers("/login/test").permitAll()
			.antMatchers("/business/login").permitAll()
			.antMatchers("/a").authenticated()				// 测试获取当前登录用户信息地址
			.antMatchers("/**").authenticated()
			.anyRequest().permitAll()
			.and()
			//渠道商登录接口
			.formLogin().loginPage("/anonymous/login.jsp").loginProcessingUrl("/anonymous/j_spring_security_check")
				.failureHandler(new OAuth2AuthenticationFaildHandler())
				.successHandler(oAuth2AuthenticationSuccessHandler())
			//渠道商登出接口
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index.html").permitAll()
			.and().addFilterBefore(sso(),BasicAuthenticationFilter.class)		// 加入oauth的filter

			.exceptionHandling().authenticationEntryPoint(ajaxAuthenticationEntryPoint())		// 拦截ajax请求,防止进行302跳转
			.accessDeniedHandler(platformAccessDeniedHandler())//设置权限不足时的处理办法
			
			.and().rememberMe()
			//配置失效时间
			.tokenValiditySeconds(1206900)
			.userDetailsService(userDetailsService)
			.key("cc")
			.and().formLogin().permitAll();
	}
	
	@Bean
	public PlatformAccessDeniedHandler platformAccessDeniedHandler(){
		return new PlatformAccessDeniedHandler();
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

    @Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}
