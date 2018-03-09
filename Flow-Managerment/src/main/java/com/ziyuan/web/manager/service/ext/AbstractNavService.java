package com.ziyuan.web.manager.service.ext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shziyuan.flow.global.common.Constant;
import com.ziyuan.web.manager.domain.ext.NavMenu;

public abstract class AbstractNavService implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	private List<NavMenu> cacheNavPlatform;
	private long lastEditNavPlatform = 0;
	
	protected static final String NAVPLATFORM = "classpath:/nav/NavPlatform.json";
	protected static final String NAVDISTRIBUTOR = "classpath:/nav/NavDistributor.json";
	
	public abstract JEasyuiData userInfo(UserDetails user);
	
	protected List<NavMenu> readCacheNavPlatform(String path) {
		if(cacheNavPlatform == null || lastEditNavPlatform == 0 || getModify(path) > lastEditNavPlatform) {
//			Constant.logger.debug("建立前端菜单[{}]缓存",path);
			try {
				cacheNavPlatform(path);
			} catch (IOException e) {
//				Constant.logger.error("加载前端菜单文件[{}]失败 :{}",path,e.getMessage());
				return null;
			}
		}
		return this.cacheNavPlatform;
	}
	protected long getModify(String path) {
		org.springframework.core.io.Resource resource = applicationContext.getResource(path);
		try {
			return resource.lastModified();
		} catch (IOException e) {
			return 0;
		}
	}
	protected void cacheNavPlatform(String path) throws IOException {
		org.springframework.core.io.Resource resource = applicationContext.getResource(path);
		InputStream is = null;
		
		try {
			is = resource.getInputStream();
			ObjectMapper json = new ObjectMapper();
			JavaType type = json.getTypeFactory().constructParametricType(List.class, NavMenu.class);
			this.cacheNavPlatform = json.readValue(is, type);
			this.lastEditNavPlatform = resource.lastModified();
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	protected boolean hasRole(Collection<? extends GrantedAuthority> authList,String role) {
		if("any".equals(role))
			return true;
		String[] roles = role.split("\\|");
		boolean isHasRole = false;
		for(String r : roles) {
			if(authList.stream().anyMatch(g -> g.getAuthority().equals(r)))
				isHasRole = true;
		}
			
		return isHasRole;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}



}
