package com.ziyuan.web.manager.service.ext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class NavDistributorService extends AbstractNavService{

	@Override
	public JEasyuiData userInfo(UserDetails user) {
		String username = user.getUsername();
		Collection<? extends GrantedAuthority> authList = user.getAuthorities();
		List<NavMenu> jsonMenu = readCacheNavPlatform(NAVDISTRIBUTOR);
		
		
		List<NavMenu> resultMenu = clone(jsonMenu);
				
		resultMenu.stream().forEach(nav -> {
			if(nav.getMenus() != null) {
				List<NavMenu> sublist = nav.getMenus();
				List<NavMenu> newSubList = sublist.stream().filter(subnav -> {
					return subnav.getRole() == null ? true : hasRole(authList, subnav.getRole());
				}).collect(Collectors.toList());
				nav.setMenus(newSubList);
			}
		});
		
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("username", username);
		retMap.put("navMenu", resultMenu);
		retMap.put("auths", "[\"" + ROLES.DISTRIBUTOR.role + "\"]");
		
		return new JEasyuiData(retMap);
	}

	public List<NavMenu> clone(List<NavMenu> list) {
		List<NavMenu> cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(list);
            obs.close();
            
            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            cloneObj = (List<NavMenu>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
	}
}
