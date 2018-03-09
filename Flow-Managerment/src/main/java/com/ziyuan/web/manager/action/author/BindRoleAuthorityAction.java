package com.ziyuan.web.manager.action.author;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.domain.author.BindAccountRoleAuthorityBean;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.author.BindRoleAuthorityImpl;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 角色-权限绑定表的Controller
 * @author user
 *
 */
@RestController
@RequestMapping("/author/bindAuthority")
public class BindRoleAuthorityAction {
	
	@Resource
	private BindRoleAuthorityImpl bindRoleAuthorityImpl;
	
	/**
	 * 插入角色绑定权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/insertBindAuthority.do",method=RequestMethod.POST)
	public JEasyuiData insertBindAuthority(@RequestBody BindAccountRoleAuthorityBean data, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了插入角色绑定权限操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(data));
		return bindRoleAuthorityImpl.insertBindAuthority(data);
	}
	
	/**
	 * 删除角色绑定角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	@RequestMapping(value = "/deleteBindAuthority.do",method=RequestMethod.DELETE)
	public JEasyuiData deleteBindAuthority(@RequestBody BindAccountRoleAuthorityBean data, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了删除角色绑定权限操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(data));
		return bindRoleAuthorityImpl.deleteBindAuthority(data);
	}
	
	/**
	 * 更新角色绑定角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/updateBindAuthority.do",method=RequestMethod.PUT)
	public JEasyuiData updateBindAuthority(@RequestBody BindAccountRoleAuthorityBean data, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了更新角色绑定权限操作, 服务类[{}], 数据[{}]", 
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(data));
		return bindRoleAuthorityImpl.updateBindAuthority(data);
	}

}
