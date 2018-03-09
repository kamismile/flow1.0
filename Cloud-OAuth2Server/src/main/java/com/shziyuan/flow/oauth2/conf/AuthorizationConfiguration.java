package com.shziyuan.flow.oauth2.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * OAuth2 认证配置
 * @author james.hu
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter{
	@Autowired
	private AuthenticationManager authenticationManager;	// security注册的认证管理
	
	@Autowired
	private LDAPUserDetailsService ldapUserDetailsService;	// secuirty注册的LDAP用户查询逻辑
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 配置一个内存鉴权客户端
		clients.inMemory()
			.withClient("ldap")
				.resourceIds(ResourceConfiguraion.RESOURCE_ID)
				.secret("940430")
				.scopes("web","app")
				.authorities("ROLE_TRUST_OAUTH")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token");
	}
	
	/**
	 * 配置TOKEN服务的认证方法
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);		// 认证管理
		endpoints.userDetailsService(ldapUserDetailsService);		// ldap查询
		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);		// 允许get/post请求token
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();	// 允许form提交认证参数
	}
}
