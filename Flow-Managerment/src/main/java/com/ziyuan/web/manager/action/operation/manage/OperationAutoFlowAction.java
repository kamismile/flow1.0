package com.ziyuan.web.manager.action.operation.manage;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ziyuan.web.manager.domain.AutoFlowBean;
import com.ziyuan.web.manager.service.impl.BindDistributorService;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 运营平台->运营管理->流量自充
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/operation/manage/autoFlow")
public class OperationAutoFlowAction {

	@Resource
	private BindDistributorService bindDistributorService;
	
	/**
	 * 流量自冲充值
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/orderPhone",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData orderCommon(@RequestBody AutoFlowBean bean, HttpServletRequest request, HttpServletResponse response, Principal user) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了流量自冲操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(bean));
		return bindDistributorService.order(bean, request, response);
	}
	
	/**
	 * 根据省份查询产品
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/skus",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBusinessSkus(JEasyuiRequestBean bean) {
		return bindDistributorService.selectBusinessSkus(bean);
	}
	
	/**
	 * 读取excel文件
	 * @param file
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/readExcel",method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<ArrayList<Object>> readExcel(@RequestParam(value="file") MultipartFile file){
		//获取文件名  
	    String fileName=file.getOriginalFilename();
		return bindDistributorService.readExcel(file, fileName);
	}
}
