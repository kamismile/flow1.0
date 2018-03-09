package com.ziyuan.web.manager.action.operation.sku;

import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.domain.flow.InfoSku;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.InfoSkuService;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 产品管理
 * @author user
 *
 */
@Controller
@RequestMapping("/skus")
public class SkuManagerAction extends AbstractCRUDAction<InfoSku>{
	
	@Resource
	private InfoSkuService infoSkuService;
	
	@Override
	public ICRUDService<InfoSku> getService() {
		return infoSkuService;
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeInSku() {
		return infoSkuService.selectTreeInSku();
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree2",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeInSku2() {
		return infoSkuService.selectTreeInSku2();
	}
	
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/param",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateSkuParam(InfoSku sku) {
		return infoSkuService.updateSkuParam(sku);
	}
	
	/**
	 * 新增平台产品
	 */
	@Override
	public JEasyuiData insert(HttpServletRequest request, Principal user,InfoSku domain) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了插入操作, 服务类[{}],数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		domain.setUpdate_time(TimestampUtil.now());
		domain.setUpdate_user(user.getName());
		return infoSkuService.insert(domain);
	}
	
	/**
	 * 更新平台产品
	 */
	@Override
	public JEasyuiData update(HttpServletRequest request,Principal user, InfoSku domain) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了更新操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		domain.setUpdate_time(TimestampUtil.now());
		domain.setUpdate_user(user.getName());
		return super.update(request, user, domain);
	}
	
	/**
	 * 查询全部平台产品
	 */
	@Override
	public JEasyuiData select(JEasyuiRequestBean param) {
		// TODO Auto-generated method stub
		return infoSkuService.selectAllSku(param);
	}
}
