package com.ziyuan.web.manager.action.operation.manage;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.LogWebDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.LogWebSupplierCodetableDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.OptSupplierCodetableDiscountChange;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.domain.OperatorSupplierDiscountBean;
import com.ziyuan.web.manager.feign.AccountFeign;
import com.ziyuan.web.manager.service.impl.LogWebDistributorDiscountChangeService;
import com.ziyuan.web.manager.service.impl.LogWebSupplierCodetableDiscountChangeService;
import com.ziyuan.web.manager.service.impl.OptDistributorDiscountChangeService;
import com.ziyuan.web.manager.service.impl.OptSupplierCodetableDiscountChangeService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 运营管理--》折扣调整操作类
 */
@RequestMapping("/operation/discount")
@Controller
public class OperationDiscountChangeAction {
	@Resource
	private OptSupplierCodetableDiscountChangeService optSupplierCodetableDiscountChangeService;
	
	@Resource
	private OptDistributorDiscountChangeService optDistributorDiscountChangeService;
	
	@Resource
	private LogWebSupplierCodetableDiscountChangeService logWebSupplierCodetableDiscountChangeService;
	
	@Resource
	private LogWebDistributorDiscountChangeService logWebDistributorDiscountChangeService;
	
	@Resource
	private AccountFeign accountFeign;
	
	/**
	 * 供应商折扣调整
	 * @param bean
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping("/supplier/change")
	@ResponseBody
	public JEasyuiData changeSupplier(
			@RequestBody() OperatorSupplierDiscountBean<OptSupplierCodetableDiscountChange,LogWebSupplierCodetableDiscountChange> bean,
			Principal user, HttpServletRequest request) {
		
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了供应商折扣调整操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(bean));
		bean.getLogs().forEach(log -> {
			log.setUpdate_user(user.getName());
			log.setInsert_time(TimestampUtil.now());
		});
		bean.getDatas().forEach(opt -> opt.setStatus(0));
		
		try {
			ActionResponse res = accountFeign.SupplierDiscount(bean.getDatas());
			if(!res.isSuccess()){
				return new JEasyuiData(false, res.getErrorMsg());
			}
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
		
		logWebSupplierCodetableDiscountChangeService.batchInsert(bean.getLogs());
		return new JEasyuiData(true,"");
	}
	
	/**
	 * 渠道商折扣调整
	 * @param bean
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping("/distributor/change")
	@ResponseBody
	public JEasyuiData changeDistributor(
			@RequestBody OperatorSupplierDiscountBean<OptDistributorDiscountChange,LogWebDistributorDiscountChange> bean,
			Principal user, HttpServletRequest request) {
		
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了渠道商折扣调整操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(bean));
		bean.getLogs().forEach(log -> {
			log.setUpdate_user(user.getName());
			log.setInsert_time(TimestampUtil.now());
		});
		bean.getDatas().forEach(opt -> opt.setStatus(0));
		try {
			ActionResponse res = accountFeign.DistributorDiscount(bean.getDatas());
			if(!res.isSuccess()){
				return new JEasyuiData(false, res.getErrorMsg());
			}
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
		logWebDistributorDiscountChangeService.batchInsert(bean.getLogs());
		return new JEasyuiData(true,"");
	}
	
	/**
	 * 供应商折扣日志
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/supplier/log",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectSupplierLog(JEasyuiRequestBean bean) {
		return logWebSupplierCodetableDiscountChangeService.select(bean);
	}
	
	/**
	 * 渠道商折扣日志
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/distributor/log",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectDistributorLog(JEasyuiRequestBean bean) {
		return logWebDistributorDiscountChangeService.select(bean);
	}
	
	/**
	 * 供应商折扣日志导出
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/supplier/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportSupplier(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = logWebSupplierCodetableDiscountChangeService.export(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	/**
	 * 渠道商折扣日志导出
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/distributor/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportDistributor(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = logWebDistributorDiscountChangeService.export(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
}
