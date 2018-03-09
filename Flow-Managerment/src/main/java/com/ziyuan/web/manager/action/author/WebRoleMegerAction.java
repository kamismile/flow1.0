package com.ziyuan.web.manager.action.author;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;
import com.ziyuan.web.manager.service.author.RoleServiceImpl;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 角色表的Controller
 * @author user
 *
 */
@RestController
@RequestMapping("/author/role")
public class WebRoleMegerAction {
	@Resource
	private RoleServiceImpl roleServiceImpl;

	/**
	 * 增加角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/table.do",method=RequestMethod.POST)
	public JEasyuiData addAccount(WebAccountRoleMegerBean webAccountRoleMegerBean, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了增加角色操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(webAccountRoleMegerBean));
		return roleServiceImpl.addAccount(webAccountRoleMegerBean);
	}
	
	/**
	 * 删除角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	@RequestMapping(value = "/table.do",method=RequestMethod.DELETE)
	public JEasyuiData deleteAccount(WebAccountRoleMegerBean webAccountRoleMegerBean, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了删除角色操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(webAccountRoleMegerBean));
		return roleServiceImpl.deleteAccount(webAccountRoleMegerBean);
	}
	
	/**
	 * 更改角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/table.do",method=RequestMethod.PUT)
	public JEasyuiData updateAccount(WebAccountRoleMegerBean webAccountRoleMegerBean, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了更改角色操作, 服务类[{}], 数据[{}]", 
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(webAccountRoleMegerBean));
		return roleServiceImpl.updateAccount(webAccountRoleMegerBean);
	}
	
	/**
	 * 查找所有角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/table.do",method=RequestMethod.GET)
	public JEasyuiData selectAccount(JEasyuiRequestBean jEasyuiRequestBean) {
		return roleServiceImpl.selectAllAccount(jEasyuiRequestBean);
	}
	
	/**
	 * 查找单个角色
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/selectOneRole.do",method=RequestMethod.GET)
	public JEasyuiData selectOneRole(String description, String enabled, int page, int rows) {
		return roleServiceImpl.selectOneRole(description, enabled, page, rows);
	}
	
	/**
	 * 查询角色绑定权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/selectBindAuthority.do",method=RequestMethod.GET)
	public JEasyuiData selectBindAuthority(JEasyuiRequestBean jEasyuiRequestBean) {
		return roleServiceImpl.selectBindAuthority(jEasyuiRequestBean);
	}
	
}
