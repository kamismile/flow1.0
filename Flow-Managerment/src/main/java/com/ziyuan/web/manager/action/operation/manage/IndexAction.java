package com.ziyuan.web.manager.action.operation.manage;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.jeasyui.JChartData;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.domain.CountTodayBean;
import com.ziyuan.web.manager.service.impl.IndexService;

@RequestMapping("/operation/index")
@Controller
public class IndexAction {
	@Resource
	private IndexService indexService;
	
	@RequestMapping(value = "/log/queueOrderSubmit",method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	public JEasyuiData selectLogQueueOrderSubmit(JEasyuiRequestBean bean) {
		return indexService.selectLogQueueOrderSubmit(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/log/queueSupplierReport",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectLogQueueSupplierReport(JEasyuiRequestBean bean) {
		return indexService.selectLogQueueSupplierReport(bean);
	}
	
	/**
	 * 折线图
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/log/orderCharLine",method = RequestMethod.GET)
	@ResponseBody
	public JChartData selectOrderChartLine(JEasyuiRequestBean bean) {
		return indexService.selectOrderChartLine(bean);
	}
	
	/**
	 * 饼状图
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/log/orderCharPie",method = RequestMethod.GET)
	@ResponseBody
	public JChartData selectIndexChartPieGroupByStatusPush(JEasyuiRequestBean bean) {
		return indexService.selectIndexChartPieGroupByStatusPush(bean);
	}
	
	/**
	 * 今日订单总数，统计总数金额
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/log/countToday",method = RequestMethod.GET)
	@ResponseBody
	public CountTodayBean selectCountToday(JEasyuiRequestBean bean) {
		return indexService.selectCountToday(bean);
	}
}
