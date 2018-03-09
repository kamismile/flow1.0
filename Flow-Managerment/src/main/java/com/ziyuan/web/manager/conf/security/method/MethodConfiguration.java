package com.ziyuan.web.manager.conf.security.method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * 注解验证权限的配置类
 * @author user
 *
 */
@EnableGlobalMethodSecurity(prePostEnabled=true)//开启注解权限验证
public class MethodConfiguration extends GlobalMethodSecurityConfiguration {
	
	private MyPermissionEvaluator myPermissionEvaluator;
	
	@Autowired
    public void setCustomPermissionEvaluator(MyPermissionEvaluator myPermissionEvaluator) {
        this.myPermissionEvaluator = myPermissionEvaluator;
    }
	
	/**
	 * 将自定义的MyPermissionEvaluator注入到DefaultMethodSecurityExpressionHandler中
	 */
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		handler.setPermissionEvaluator(myPermissionEvaluator);
		return handler;
	}

}
