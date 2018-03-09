package com.ziyuan.web.manager.action.operation.report;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ziyuan.web.manager.domain.report.OrderTimesharingBean;
import com.ziyuan.web.manager.domain.report.OrderTimesharingBeanReturn;
import com.ziyuan.web.manager.service.impl.report.OrderReportService;
import com.ziyuan.web.manager.utils.ExcelTools;

@RequestMapping("/reports")
@Controller
public class PlatformOrderReportAction {
	@Resource
	private OrderReportService orderReportService;
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/distributor/timesharing",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectOrderTimesharing(JEasyuiRequestBean bean) {
		List<OrderTimesharingBeanReturn> dataList = orderReportService.selectOrderTimesharingTotal(bean);
		
		return new JEasyuiData(dataList);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/timesharing/export.do",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportOrderTimesharing(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderReportService.export(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
}
