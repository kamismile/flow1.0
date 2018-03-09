package com.ziyuan.web.manager.action;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

public abstract class AbstractCRUDAction<DOMAIN> implements ICRUDAction<DOMAIN>,InitializingBean{
	private ICRUDService<DOMAIN> service;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setService(getService());
	}
	
	@Override
	@RequestMapping(value="/table",method=RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	public JEasyuiData select(JEasyuiRequestBean param) {
		JEasyuiData result = service.select(param);
		return result;
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/table/{id}/row",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectOne(@PathVariable("id") int id) {
		return service.selectOne(id);
	}
	
	@Override
	@RequestMapping(value="/table",method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	public JEasyuiData insert(HttpServletRequest request, Principal user,DOMAIN domain) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了插入操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getService(), JacksonUtil.bean2Json(domain));
		return service.insert(domain);
	}

	@Override
	@RequestMapping(value="/table",method=RequestMethod.PUT)
	@ResponseBody
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	public JEasyuiData update(HttpServletRequest request, Principal user,DOMAIN domain) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了更新操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getService(), JacksonUtil.bean2Json(domain));
		return service.update(domain);
	}

	@Override
	@RequestMapping(value="/table",method=RequestMethod.DELETE)
	@ResponseBody
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	public JEasyuiData delete(HttpServletRequest request, Principal user,int id) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了删除操作, 服务类[{}], 删除id为[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getService(), id);
		return service.delete(id);
	}

	@Override
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	public ResponseEntity<byte[]> export(HttpServletRequest request, JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = service.export(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}

	public abstract ICRUDService<DOMAIN> getService();

	public void setService(ICRUDService<DOMAIN> service) {
		this.service = service;
	}
}
