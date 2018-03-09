package com.ziyuan.web.manager.action.author;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;
import com.ziyuan.web.manager.service.author.AuthorityServiceImpl;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 权限表的Controller
 * @author user
 *
 */
@RestController
@RequestMapping("/author/authority")
public class WebAuthorityMegerAction {
	@Resource
	private AuthorityServiceImpl authorityServiceImpl;

	/**
	 * 增加权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/table.do",method=RequestMethod.POST)
	public JEasyuiData addAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了增加权限操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(webAccountAuthorityMegerBean));
		return authorityServiceImpl.addAccount(webAccountAuthorityMegerBean);
	}
	
	/**
	 * 删除权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	@RequestMapping(value = "/table.do",method=RequestMethod.DELETE)
	public JEasyuiData deleteAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了删除权限操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(webAccountAuthorityMegerBean));
		return authorityServiceImpl.deleteAccount(webAccountAuthorityMegerBean);
	}
	
	/**
	 * 更改权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/table.do",method=RequestMethod.PUT)
	public JEasyuiData updateAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了更改权限操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(webAccountAuthorityMegerBean));
		return authorityServiceImpl.updateAccount(webAccountAuthorityMegerBean);
	}
	
	/**
	 * 查找所有权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/table.do",method=RequestMethod.GET)
	public JEasyuiData selectAccount(JEasyuiRequestBean jEasyuiRequestBean) {
		return authorityServiceImpl.selectAllAccount(jEasyuiRequestBean);
	}
	
	/**
	 * 查询单个权限
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/selectOneAuthority.do",method=RequestMethod.GET)
	public JEasyuiData selectOneAuthority(String description, String enabled, int page, int rows) {
		return authorityServiceImpl.selectOneAuthority(description, enabled, page, rows);
	}
	
}
