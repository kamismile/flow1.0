package com.ziyuan.web.manager.conf.security.method;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;
import com.ziyuan.web.manager.wrap.author.RoleWrap;

/**
 * 自定义PermissionEvaluator
 * @author user
 *
 */
@Configuration
public class MyPermissionEvaluator implements PermissionEvaluator{
	
	@Resource
	private RoleWrap roleWrap;

	/**
	 * 自定义的hasPermission方法
	 * @param authentication
	 * @param permission
	 * @return
	 */
	private boolean hasPermission(Authentication authentication, Object permission) {
	     Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();
	     for (GrantedAuthority grantedAuthority : auth) {
	    	//获取id
	    	int id = roleWrap.getID(grantedAuthority.toString());
			List<WebAccountAuthorityMegerBean> list = roleWrap.selectAuth(String.valueOf(id));
			for (WebAccountAuthorityMegerBean authority : list) {
				if(authority.getAuthority().equals(permission)){
					return true;
				}
			}
		}
	    return false;
	}
	 
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		//判断targetDomainObject是否符合，如果是掉用自定义的hasPermission
		if ("user".equals(targetDomainObject)) {
	        return this.hasPermission(authentication, permission);
	     }
	    return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
