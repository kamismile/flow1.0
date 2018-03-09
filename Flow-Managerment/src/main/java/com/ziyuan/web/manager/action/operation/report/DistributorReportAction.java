package com.ziyuan.web.manager.action.operation.report;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.service.impl.OrderService;
import com.ziyuan.web.manager.utils.ExcelTools;

@Controller
@RequestMapping("/reports")
public class DistributorReportAction {

	@Resource
	private OrderService orderService;

	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/day", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData listDistributorStaticsReportDaily(JEasyuiRequestBean bean) {
		return orderService.listDistributorStaticsReportDaily(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/month", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData listDistributorStaticsReportMonth(JEasyuiRequestBean bean) {
		return orderService.listDistributorStaticsReportMonth(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/day/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportDaily(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportDaily(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/month/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportMonth(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportMonth(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/day/account/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportDistributorStatistics(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportTimeStatistics(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/day/account",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDistributorStatistics(JEasyuiRequestBean bean) {
		return orderService.getTimeStatistics(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/day/success",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDistributorStatisticsSuccess(JEasyuiRequestBean bean) {
		return orderService.getTimeSuccessStatistics(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/total/detail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getTotalDetail(JEasyuiRequestBean bean) {
		return orderService.getTotalDetail(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/total/detail/excel",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportTotalDetail(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportTotalDetail(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/day/success/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportDistributorStatisticsSuccess(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportTimeSuccessStatistics(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/name",method=RequestMethod.GET)
	@ResponseBody
	public List<String> getDistributorNames(JEasyuiRequestBean bean) {
		return orderService.getDistributorNames(bean);
	}
}
