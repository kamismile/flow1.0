package com.ziyuan.web.manager.action.operation.distributor;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributorCertificate;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributor;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.InfoDistributorCertificateService;
import com.ziyuan.web.manager.service.impl.InfoDistributorService;
import com.ziyuan.web.manager.service.impl.WebAccountDistributorService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 渠道商信息管理类
 * @author user
 *
 */
@Controller
@RequestMapping("/distributors")
public class DistributorManagerAction extends AbstractCRUDAction<InfoDistributor>{
	
	@Resource
	private InfoDistributorService infoDistributorService;
	
	@Resource
	private InfoDistributorCertificateService infoDistributorCertificateService;
	
	@Resource
	private WebAccountDistributorService webAccountDistributorService;

	private static final String UPLOAD_PATH = "/upload/distributor/certificate/";
	
	@Override
	public ICRUDService<InfoDistributor> getService() {
		return infoDistributorService;
	}
	
	@Override
	public JEasyuiData insert(HttpServletRequest request, Principal user, InfoDistributor domain) {
		domain.setCreate_time(TimestampUtil.now());
		domain.setUpdate_time(TimestampUtil.now());
		domain.setUpdate_user(user.getName());
		return super.insert(request, user, domain);
	}
	
	@Override
	public JEasyuiData update(HttpServletRequest request, Principal user, InfoDistributor domain) {
		domain.setUpdate_time(TimestampUtil.now());
		domain.setUpdate_user(user.getName());
		return super.update(request, user, domain);
	}
	
	/**
	 * 查找全部渠道商信息
	 * @return
	 */
	@RequestMapping(value="/name",method=RequestMethod.GET)
	@ResponseBody
	public List<InfoDistributor> selectDistributorNames() {
		return infoDistributorService.selectDistributorNames();
	}

	/**
	 * 配置商户登录，get方法
	 * @param distributor_id
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value="/webaccount",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectWebAccountPassword(@RequestParam("distributor_id") int distributor_id) {
		return webAccountDistributorService.selectOne(distributor_id);
	}
	
	/**
	 * 配置商户登录，post方法
	 * @param domain
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value="/webaccount",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData createWebAccountPassword(Principal user, WebAccountDistributor domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了配置商户登录操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return webAccountDistributorService.insert(domain);
	}
	
	/**
	 * 配置商户登录，put方法
	 * @param domain
	 * @param ROLE_DIS_SELFFLOW
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value="/webaccount",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateWebAccountPassword(WebAccountDistributor domain,
			@RequestParam("ROLE_DIS_SELFFLOW") boolean ROLE_DIS_SELFFLOW) {
		return webAccountDistributorService.update(domain,ROLE_DIS_SELFFLOW);
	}
	
//	/**
//	 * 渠道审核--》渠道商录入审核--》查看相关凭证
//	 * @param request
//	 * @param id
//	 * @return
//	 */
//	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
//	@RequestMapping(value = "/uploadCertificate",method=RequestMethod.GET)
//	@ResponseBody
//	public JEasyuiData uploadCertificateSelect(HttpServletRequest request,int distributor_id) {
//		JEasyuiData ret = infoDistributorCertificateService.selectByDistributorId(distributor_id);
//		((List<InfoDistributorCertificate>)ret.getRows()).forEach(idc -> idc.setFilename(UPLOAD_PATH + idc.getFilename()));
//		return ret;
//	}
	
	/**
	 * 渠道审核--》渠道商录入审核--》查看相关凭证
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/uploadCertificate",method=RequestMethod.GET)
	@ResponseBody
	public void uploadCertificateSelect(HttpServletRequest request, HttpServletResponse res, int distributor_id) throws Exception {
		infoDistributorCertificateService.selectByDistributorId(request, res, distributor_id, UPLOAD_PATH);
	}
	
	@RequestMapping(value = "/uploadCertificate",method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	public JEasyuiData uploadCertificateInsert(Principal user, HttpServletRequest request,MultipartFile file,int distributor_id) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了渠道商管理凭证-上传操作, 服务类[{}], distributor_id[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), distributor_id);
		return infoDistributorCertificateService.insert(
//				request.getSession().getServletContext().getRealPath("/") + UPLOAD_PATH, 
				UPLOAD_PATH, 
				file, distributor_id);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_DELETE')")
	@RequestMapping(value = "/uploadCertificate",method=RequestMethod.DELETE)
	@ResponseBody
	public JEasyuiData uploadCertificateDelete(Principal user, HttpServletRequest request,int id) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了渠道商管理凭证-删除操作, 服务类[{}], id[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), id);
		return infoDistributorCertificateService.delete(
//				request.getSession().getServletContext().getRealPath("/") + UPLOAD_PATH,
				UPLOAD_PATH,
				id);
	}
	
	/**
	 * 渠道审核--》渠道商录入审核--》审核通过或失败
	 * @param id
	 * @param ispass
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/table/verify",method=RequestMethod.POST)
	@ResponseBody
	public JEasyuiData distributorInsertVerify(Principal user, int id,boolean ispass) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了渠道商录入审核操作, 服务类[{}], id[{}],是否通过[{}]",
				user.getName(), getClass(), id, ispass);
		return ispass ? infoDistributorService.verifySuccess(id) : infoDistributorService.verifyFaild(id);
	}
	
	/**
	 * 导出渠道商消费账户信息
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/exportFund",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportFund(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = infoDistributorService.exportFund(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	/**
	 * 下载凭证
	 * @throws UnsupportedEncodingException 
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/downloadCertificate",method=RequestMethod.GET)
	public void downloadCertificate(int id,HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
//		String path = request.getSession().getServletContext().getRealPath("/") + UPLOAD_PATH;
		String path = UPLOAD_PATH;
		infoDistributorCertificateService.downloadCertificate(id, response, path);
	}
	
}
