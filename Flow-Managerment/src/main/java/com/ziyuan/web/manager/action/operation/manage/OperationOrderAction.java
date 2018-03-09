package com.ziyuan.web.manager.action.operation.manage;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.domain.OrderStatisticBean;
import com.ziyuan.web.manager.service.impl.OperationOrderService;
import com.ziyuan.web.manager.service.impl.OrderService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;


/**
 * 运营平台->运营管理->订单详情
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/operation/manage/order")
public class OperationOrderAction {

	@Resource
	private OrderService orderService;
	
	@Resource
	private OperationOrderService operationOrderService;
	
	/**
	 * 订单详情搜索选项
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/table",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBusinessOrderCommon(JEasyuiRequestBean bean) {
		JEasyuiData result = orderService.select(bean);
		return result;
	}
	
	/**
	 * 根据条件统计金额
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/statisticByTime.do",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData statisticByTime(JEasyuiRequestBean bean) {
		return orderService.statisticByTime(bean);
	}
	
	/**
	 * 单条订单推送成功
	 * @param user
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/sendSuccess",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData sendSuccess(Principal user, JEasyuiRequestBean bean, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了订单详情推送成功操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(bean));
		JEasyuiData result = orderService.sendSuccess(user, bean);
		return result;
	}
	
	/**
	 * 单条订单推送失败
	 * @param user
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/sendFail",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData sendFail(Principal user, JEasyuiRequestBean bean, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了订单详情推送失败操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(bean));
		JEasyuiData result = orderService.sendFail(user, bean);
		return result;
	}
	
	/**
	 * 订单批量推送成功
	 * @param requests
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendSuccessBatch",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData sendSuccessBatch(@RequestBody List<Map<String,Object>> requests, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了订单详情批量推送成功操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(requests));
		JEasyuiData result = orderService.sendSuccessBatch(requests);
		return result;
	}
	
	/**
	 * 订单批量推送失败
	 * @param requests
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendFaildBatch",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData sendFaildBatch(@RequestBody List<Map<String,Object>> requests, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了订单详情批量推送失败操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(requests));
		JEasyuiData result = orderService.sendFaildBatch(requests);
		return result;
	}
	
	/**
	 * 重新推送
	 * @param user
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/pushAgain",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData pushAgain(Principal user, JEasyuiRequestBean bean, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了订单详情重推操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(bean));
		JEasyuiData result = orderService.pushAgain(user, bean);
		return result;
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportOrderCommon(JEasyuiRequestBean bean) {
		String filename = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.export(bean);
		return ExcelTools.export(bOutputStream, filename);
	}
	
	@RequestMapping("/timeline.do")
	public ModelAndView timelineOrderNo(String order_no) {
		ModelAndView view = new ModelAndView("/operation/orderTimeline");
		view.addObject("datas", operationOrderService.selectTimelineByOrderNo(order_no));
		return view;
	}
	
//	@RequestMapping("/timeline.do")
//	@ResponseBody
//	public JEasyuiData timelineOrderNo2(String order_no) {
//		return operationOrderService.selectTimelineByOrderNo2(order_no);
//	}
}
