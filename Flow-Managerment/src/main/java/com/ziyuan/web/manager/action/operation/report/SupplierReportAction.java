package com.ziyuan.web.manager.action.operation.report;

import java.io.ByteArrayOutputStream;
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
public class SupplierReportAction {

	@Resource
	private OrderService orderService;

	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/supplier/day", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData listSupplierStaticsReportDaily(JEasyuiRequestBean bean) {
		return orderService.listSupplierStaticsReportDaily(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/supplier/month", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData listSupplierStaticsReportMonth(JEasyuiRequestBean bean) {
		return orderService.listSupplierStaticsReportMonth(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/supplier/day/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportDailySupplier(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportDailySupplier(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/supplier/month/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportMonthSupplier(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportMonthSupplier(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
}
