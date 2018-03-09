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
import org.springframework.web.servlet.ModelAndView;

import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.domain.ArchiveSupplierReportActiveBean;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.ArchiveSupplierReportActiveService;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 运营平台->运营管理->存档卡单
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/operation/manage/archives")
public class OperationArchiveAction extends AbstractCRUDAction<ArchiveSupplierReportActiveBean> {

	@Resource
	private ArchiveSupplierReportActiveService archiveSupplierReportActiveService;
	@Override
	public ICRUDService<ArchiveSupplierReportActiveBean> getService() {
		// TODO Auto-generated method stub
		return archiveSupplierReportActiveService;
	}
	
	/**
	 * 重新推送
	 * @param user
	 * @param archiveIds
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/resend", method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData SendAgain(Principal user, @RequestBody List<Integer> archiveIds, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了重新推送操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(archiveIds));
		return archiveSupplierReportActiveService.SendAgain(archiveIds);
	}
	
	/**
	 * 推送失败
	 * @param user
	 * @param archiveIds
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendFail", method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData SendFail(Principal user, @RequestBody List<Integer> archiveIds, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了推送失败操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(archiveIds));
		return archiveSupplierReportActiveService.sendFail(archiveIds);
	}
	
	/**
	 * 推送成功
	 * @param user
	 * @param archiveIds
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/sendSuccess", method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData sendSuccess(Principal user, @RequestBody List<Integer> archiveIds, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了推送成功操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(archiveIds));
		return archiveSupplierReportActiveService.sendSuccess(archiveIds);
	}

}
