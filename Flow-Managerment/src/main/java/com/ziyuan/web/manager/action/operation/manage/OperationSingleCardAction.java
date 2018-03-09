package com.ziyuan.web.manager.action.operation.manage;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.List;

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

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.command.ManualCommand;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.impl.QueueSupplierReportActiveService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.ReflectionToString;


/**
 * 运营平台->运营管理->处理中卡单
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/operation/manage/singleCards")
public class OperationSingleCardAction {

	@Resource
	private QueueSupplierReportActiveService queueSupplierReportActiveService;
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/table",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData select(JEasyuiRequestBean param) {
		JEasyuiData result = queueSupplierReportActiveService.select(param);
		return result;
	}
	
	/**
	 * 推送失败
	 * @param user
	 * @param order_no
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendFail", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData SendFail(Principal user, String order_no, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了卡单推送失败操作, 服务类[{}], order_no[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), order_no);
		return queueSupplierReportActiveService.sendFail(user, order_no);
	}
	
	/**
	 * 推送成功
	 * @param user
	 * @param order_no
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendSuccess", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData sendSuccess(Principal user, String order_no, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了卡单推送成功操作, 服务类[{}], order_no[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), order_no);
		return queueSupplierReportActiveService.sendSuccess(user, order_no);
	}
	
	/**
	 * 批量推成功
	 * @param orders
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendSuccessBatch", method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData sendSuccessBatch(@RequestBody List<String> orders, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了卡单批量推送成功操作, 服务类[{}], order_no[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), ReflectionToString.reflectionToString(orders));
		return queueSupplierReportActiveService.sendPushBatch(orders, QueueOrder.CMD_ORDER_PUSH_SUCCESS);
	}
	
	/**
	 * 批量推失败
	 * @param orders
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendFaildBatch", method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData sendFaildBatch(@RequestBody List<String> orders, Principal user, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了卡单批量推送失败操作, 服务类[{}], order_no[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), ReflectionToString.reflectionToString(orders));
		return queueSupplierReportActiveService.sendPushBatch(orders, QueueOrder.CMD_ORDER_PUSH_FAILD);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> export(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = queueSupplierReportActiveService.export(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
//	@RequestMapping("/timeline.do")
//	public ModelAndView timelineOrderNo(String order_no) {
//		ModelAndView view = new ModelAndView("/operation/orderTimeline");
//		view.addObject("datas", queueSupplierReportActiveService.selectTimelineByOrderNo(order_no));
//		return view;
//	}

}
