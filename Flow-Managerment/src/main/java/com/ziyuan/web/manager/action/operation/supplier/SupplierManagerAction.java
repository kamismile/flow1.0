package com.ziyuan.web.manager.action.operation.supplier;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.InfoSupplierService;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;

/**
 * 供应商管理
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/suppliers")
public class SupplierManagerAction extends AbstractCRUDAction<InfoSupplier>{

	@Resource
	private InfoSupplierService infoSupplierService;
	
	@Override
	public ICRUDService<InfoSupplier> getService() {
		return infoSupplierService;
	}
	
	@Override
	public JEasyuiData insert(HttpServletRequest request, Principal user, InfoSupplier domain) {
		domain.setCreate_time(TimestampUtil.now());
		domain.setUpdate_time(TimestampUtil.now());
		domain.setUpdate_user(user.getName());
		return super.insert(request, user, domain);
	}
	
	@Override
	public JEasyuiData update(HttpServletRequest request, Principal user, InfoSupplier domain) {
		domain.setUpdate_time(TimestampUtil.now());
		domain.setUpdate_user(user.getName());
		return super.update(request, user, domain);
	}
	
	/**
	 * 获取全部供应商信息
	 * @return
	 */
	@RequestMapping(value="/name",method=RequestMethod.GET)
	@ResponseBody
	public List<InfoSupplier> selectName() {
		return infoSupplierService.selectName();	
	}
	
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value="/attribute",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateSupplierInfoAttribute(Principal user, InfoSupplier infoSupplier, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了供应商信息修改操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(infoSupplier));
		return infoSupplierService.updateSupplierInfoAttribute(infoSupplier);
	}
	
	/**
	 * 可根据供应商名称或产品名称查询 全部 或 部分 缓存订单页面
	 * 运营管理--》缓存订单开关
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/cacheTable",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectUseCache(JEasyuiRequestBean bean) {
		return infoSupplierService.selectUseCache(bean);
	}
	
	/**
	 * 修改缓存模式
	 * @param domain
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/cacheTable",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateCacheTable(Principal user, InfoSupplier domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了修改供应商缓存模式操作, 服务类[{}], 数据[{}]",
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return infoSupplierService.updateCacheTable(domain);
	}

}
