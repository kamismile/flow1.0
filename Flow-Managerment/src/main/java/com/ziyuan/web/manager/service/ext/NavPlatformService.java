package com.ziyuan.web.manager.service.ext;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ziyuan.web.manager.conf.WebConstant.ROLES;
import com.ziyuan.web.manager.domain.ext.NavMenu;


@Service
public class NavPlatformService extends AbstractNavService{
	
	@Override
	public JEasyuiData userInfo(UserDetails user) {
		String username = user.getUsername();
		Collection<? extends GrantedAuthority> authList = user.getAuthorities();
		boolean isAdmin = authList.stream().anyMatch(g -> g.getAuthority().equals(ROLES.ADMIN.role));
		List<NavMenu> jsonMenu = readCacheNavPlatform(NAVPLATFORM);
		
		List<NavMenu> resultMenu = isAdmin ? jsonMenu : 
			jsonMenu.stream()
			.filter(nav -> {
				return hasRole(authList, nav.getRole());
			})
			.collect(Collectors.toList());
//		List<NavMenu> resultMenu = jsonMenu;
		
		List<String> auths = authList.stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
		
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("username", username);
		retMap.put("navMenu", resultMenu);
		retMap.put("auths", auths);
		
		return new JEasyuiData(retMap);
	}
	

}
