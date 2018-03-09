package com.ziyuan.web.manager.action.operation.account;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.AccountDistributorPresentRole;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ziyuan.web.manager.feign.AccountFeign;
import com.ziyuan.web.manager.service.AccountingDistributorService;
import com.ziyuan.web.manager.service.LogAccountDistributorPresentService;
import com.ziyuan.web.manager.service.impl.AccountDistributorPresentRoleService;
import com.ziyuan.web.manager.service.impl.InfoAccountDistributorCertificateService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;



@RequestMapping("/accounting/distributor")
@Controller
public class AccoutingDistributorAction {
	@Resource
	private AccountingDistributorService accountingDistributorService;
	
	@Resource
	private InfoAccountDistributorCertificateService infoAccountDistributorCertificateService;
	
	@Resource
	private LogAccountDistributorPresentService logAccountDistributorPresentService;
	
	@Resource
	private AccountDistributorPresentRoleService accountDistributorPresentRoleService;
	
	@Resource
	private AccountFeign accountFeign;
	
	private static final String UPLOAD_PATH = "/upload/distributor/pending/";

	/**
	 * 加款
	 * @param user
	 * @param domain
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/addFunds",method = RequestMethod.POST)
	@ResponseBody
	public ActionResponse addFunds(Principal user,PendingAccountDistributor domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了加款申请操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return accountFeign.addFunds(user.getName(), AccountingDistributorService.FIELD_BANLANCE, domain);
	}
	
	/**
	 * 授信
	 * @param user
	 * @param domain
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/addCredit",method = RequestMethod.POST)
	@ResponseBody
	public ActionResponse addCredit(Principal user,PendingAccountDistributor domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了授信申请操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return accountFeign.addCredit(user.getName(), AccountingDistributorService.FIELD_CREDIT, domain);
	}
	
	/**
	 * 赠送
	 * @param user
	 * @param domain
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/addDonation",method = RequestMethod.POST)
	@ResponseBody
	public ActionResponse addDonation(Principal user,PendingAccountDistributor domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了赠送申请操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return accountFeign.addDonation(user.getName(), AccountingDistributorService.FIELD_DONATION, domain);
	}
	
//	/**
//	 * 查看凭证
//	 * @param request
//	 * @param pending_id
//	 * @return
//	 */
//	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
//	@RequestMapping(value = "/uploadCertificate/pending",method=RequestMethod.GET)
//	@ResponseBody
//	public JEasyuiData uploadCertificateSelectPending(HttpServletRequest request,int pending_id) {
//		return infoAccountDistributorCertificateService.selectByPendingId(UPLOAD_PATH,pending_id);
//	}
	
	/**
	 * 查看凭证
	 * @param request
	 * @param pending_id
	 * @return
	 * @throws Exception 
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/uploadCertificate/pending",method=RequestMethod.GET)
	@ResponseBody
	public void uploadCertificateSelectPending(HttpServletRequest request,int pending_id, HttpServletResponse res) throws Exception {
		infoAccountDistributorCertificateService.selectByPendingId(UPLOAD_PATH,pending_id, request, res);
	}
	
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/uploadCertificate/distributor_id",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData uploadCertificateSelectDistributor(HttpServletRequest request,int distributor_id) {
		return infoAccountDistributorCertificateService.selectByDistributorId(UPLOAD_PATH,distributor_id);
	}
	
	/**
	 * 上传凭证
	 * @param request
	 * @param file
	 * @param distributor_id
	 * @param pending_id
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/uploadCertificate",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData uploadCertificateInsert(HttpServletRequest request,MultipartFile file,int distributor_id,int pending_id) {
		return infoAccountDistributorCertificateService.insert(
				UPLOAD_PATH,
				file, distributor_id,pending_id);
	}
	
	/**
	 * 财务管理--》渠道商加款日志--》查看凭证--》删除凭证
	 * @param request
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	@RequestMapping(value = "/uploadCertificate",method=RequestMethod.DELETE)
	@ResponseBody
	public JEasyuiData uploadCertificateDelete(Principal user, HttpServletRequest request,int id) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了删除加款凭证操作, 服务类[{}], id=[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), id);
		return infoAccountDistributorCertificateService.delete(
				UPLOAD_PATH,
				id);
	}
	
	/**
	 * 渠道审核--》渠道商加款审核--》查找
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/pending/table.do",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData pendingSelect(JEasyuiRequestBean bean) {
		return accountingDistributorService.pendingSelect(bean);
	}

	/**
	 * 渠道审核--》渠道商加款审核--》审核成功或失败
	 * @param user
	 * @param pending_id
	 * @param ispass
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/pending/table.do",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData pendingVerify(Principal user,int pending_id,boolean ispass, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了加款审核操作, 服务类[{}], pending_id=[{}],审核是否通过:[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), pending_id, ispass);
		return accountingDistributorService.pendingVerify(user,pending_id,ispass);
	}
	
	/**
	 * 财务管理--》渠道商加款日志（包括筛选框）
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/log",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectLog(JEasyuiRequestBean bean) {
		JEasyuiData data = accountingDistributorService.selectLog(bean);
		return data;
	}
	
	/**
	 * 财务管理--》月末赠送管理
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/present/log",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectLogPresent(JEasyuiRequestBean bean) {
		return logAccountDistributorPresentService.select(bean);
	}
	
	/**
	 * 财务管理--》月末赠送管理--》查询
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/present/role",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectPresentRole(JEasyuiRequestBean bean) {
		return accountDistributorPresentRoleService.select(bean);
	}
	
	/**
	 * 财务管理--》月末赠送管理--》插入
	 * @param domain
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/present/role",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData insertPresentRole(Principal user, AccountDistributorPresentRole domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了月末赠送管理-插入操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return accountDistributorPresentRoleService.insert(domain);
	}
	
	/**
	 * 财务管理--》月末赠送管理--》删除
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	@RequestMapping(value = "/present/role",method = RequestMethod.DELETE)
	@ResponseBody
	public JEasyuiData deletePresentRole(Principal user, int id, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了月末赠送管理-删除操作, 服务类[{}], 删除id[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), id);
		return accountDistributorPresentRoleService.delete(id);
	}
	
	/**
	 * 财务管理--》渠道商加款日志--》导出excel
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> export(JEasyuiRequestBean bean) {
		//filename指文件名
		String fileName = bean.getParam().get("filename");
		//传至service层，获取数据并转换为流的形式
		ByteArrayOutputStream bOutputStream = accountingDistributorService.export(bean);
		//调用导出工具类的导出方法
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	/**
	 *财务管理--》月末赠送管理--》导出excel
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/present/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportPresent(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = logAccountDistributorPresentService.export(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
}
