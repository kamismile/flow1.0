package com.ziyuan.web.manager.action.operation.manage;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.domain.QueueOrderBean;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.QueueOrderService;
import com.ziyuan.web.manager.utils.ReflectionToString;

@Controller
@RequestMapping("/operation/queueOrder")
public class QueueOrderAction extends AbstractCRUDAction<QueueOrderBean>{

	@Resource
	private QueueOrderService queueOrderService;
	
	@Override
	public ICRUDService<QueueOrderBean> getService() {
		return queueOrderService;
	}

	/**
	 * 单条推送
	 * @param queue
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/resetCacheOne",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData resetCacheOne(Principal user, QueueOrderBean queue, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 进行了缓存订单单条推送操作, 服务类[{}], 数据[{}]",
				user.getName(), getClass(), ReflectionToString.reflectionToString(queue));
		return queueOrderService.resetCacheOne(queue);
	}
	
	/**
	 * 批量推送
	 * @param datas
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/resetCacheBatch",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData resetCacheBatch(Principal user, String order_no, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 进行了缓存订单批量推送操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), ReflectionToString.reflectionToString(order_no));
		return queueOrderService.resetCacheBatch(order_no);
	}
	
	/**
	 * 批量推送失败
	 * @param datas
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/pushFail",method= RequestMethod.POST)
	@ResponseBody
	public JEasyuiData pushFailOne(Principal user, String datas, HttpServletRequest request){
		LoggerUtil.request.info("用户[{}], 进行了缓存订单批量推送失败操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), ReflectionToString.reflectionToString(datas));
		return queueOrderService.pushFail(datas);
	}
	
	/**
	 * 
	 * @param supplier_code_id
	 * @param interval
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/resetCacheCodetable",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData resetCacheCodetable(Principal user, int supplier_code_id,int interval, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 进行了启用缓存，并重新推送缓存订单(按照产品)操作, 服务类[{}], supplier_id[{}]", 
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), supplier_code_id);
		return queueOrderService.resetCacheCodetable(supplier_code_id, interval);
	}
	
	/**
	 * 启用缓存，并重新推送缓存订单
	 * @param supplier_id
	 * @param interval
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/resetCache",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData resetCache(Principal user, int supplier_id,int interval, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 进行了启用缓存，并重新推送缓存订单操作, 服务类[{}], supplier_id[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), supplier_id);
		return queueOrderService.resetCache(supplier_id, interval);
	}
	
	/**
	 * 查询缓存订单数量
	 * @param supplier_id
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/cacheCount",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData cacheCount(Principal user, int supplier_id) {
		return queueOrderService.cacheCount(supplier_id);
	}
}
