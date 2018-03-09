package com.ziyuan.web.manager.action.operation.supplier;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.domain.BindSupplierChangeBean;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.BindSupplierService;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 绑定供应商信息
 * @author user
 *
 */
@Controller
@RequestMapping("/suppliers/bind")
public class BindSupplierAction extends AbstractCRUDAction<BindSupplier> {

	@Resource
	private BindSupplierService bindSupplierService;
	
	@Override
	public ICRUDService<BindSupplier> getService() {
		return bindSupplierService;
	}
	
	/**
	 * 查询供应商绑定产品
	 * @param param
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/binding",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectCodetable(JEasyuiRequestBean param) {
		return bindSupplierService.selectCodetable(param);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/bindedSorting",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBindedSorting(JEasyuiRequestBean param) {
		return bindSupplierService.selectBindedSorting(param);
	}
	
	/**
	 * 新绑定逻辑
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/binding2/binded",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		return bindSupplierService.selectBind2_binded(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/binding2/nonbinded",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		return bindSupplierService.selectBind2_nonbinded(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/binding2/change",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData changeBinding2(Principal user, @RequestBody BindSupplierChangeBean data) {
		return bindSupplierService.changeBinding(user, data);
	}
	
	/**
	 * 查询赠送绑定供货产品
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/present/binded",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectPresent_binded(JEasyuiRequestBean bean) {
		return bindSupplierService.selectPresent_binded(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/present/nonbinded",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectPresent_nonbinded(JEasyuiRequestBean bean) {
		return bindSupplierService.selectPresent_nonbinded(bean);
	}
	
	/**
	 * 赠送绑定供货产品
	 * @param data
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/present/change",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData changePresent(@RequestBody BindSupplierChangeBean data, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了产品绑定赠送供应商产品操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(data));
		return bindSupplierService.changePresent(data);
	}
	
	/**
	 * 普通绑定供货产品
	 * @param data
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/change",method = RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData changeBinding(@RequestBody BindSupplierChangeBean data, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了产品绑定供应商产品操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(data));
		return bindSupplierService.changeBinding(user, data);
	}
	/**
	 * 批量绑定
	 * @param param
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/batchChange",method = RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData batchChangeBinding(Principal user, JEasyuiRequestBean param, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了批量产品绑定供应商产品操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(param));
		return bindSupplierService.batchChangeBinding(param);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/treeBySupplier",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeBySupplier() {
		return bindSupplierService.selectTreeBySupplier();
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/treeBySku",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeBySku() {
		return bindSupplierService.selectTreeBySku();
	}
	
}
