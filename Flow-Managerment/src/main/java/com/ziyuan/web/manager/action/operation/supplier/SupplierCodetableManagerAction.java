package com.ziyuan.web.manager.action.operation.supplier;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.InfoSupplierCodetableService;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;
/**
 * 供应商产品管理
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/suppliers/codetable")
public class SupplierCodetableManagerAction extends AbstractCRUDAction<InfoSupplierCodetable> {

	@Resource
	private InfoSupplierCodetableService infoSupplierCodetableService;
	
	@Override
	public ICRUDService<InfoSupplierCodetable> getService() {
		return infoSupplierCodetableService;
	}
	
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/cacheTable",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateCacheTable(Principal user, InfoSupplierCodetable domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了供应商产品缓存模式操作, 服务类[{}], 数据[{}]", 
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return infoSupplierCodetableService.updateCacheTable(domain);
	}
	
	/**
	 * 修改供应商产品特有属性
	 */
	@PreAuthorize("hasPermission('user','AUTH_UPDATE')")
	@RequestMapping(value = "/param",method=RequestMethod.PUT)
	@ResponseBody
	public JEasyuiData updateCodeParam(Principal user, InfoSupplierCodetable domain, HttpServletRequest request) {
		LoggerUtil.request.info("用户[{}], 请求URL[{}], 请求IP[{}], 进行了修改供应商产品特有属性操作, 服务类[{}], 数据[{}]", 
				user.getName(), request.getRequestURL(), request.getHeader("ipRemote"), getClass(), JacksonUtil.bean2Json(domain));
		return infoSupplierCodetableService.updateCodeParam(domain);
	}
	
	
	/**
	 * TREE
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTree() {
		return infoSupplierCodetableService.selectTree();
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree2",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTree2() {
		return infoSupplierCodetableService.selectTreeInCodetable2();
	}

	/**
	 * 按供应商筛选
	 * 运营管理--》供应商折扣调整--》按供应商筛选
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree/bysupplier/table",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectJoinSupplier(JEasyuiRequestBean bean) {
		return infoSupplierCodetableService.selectJoinSupplier(bean);
	}
	
	/**
	 * 供应商折扣调整导出
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree/bysupplier/table/excel",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportJoinSupplier(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = infoSupplierCodetableService.exportJoinSupplier(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	/**
	 * 按产品筛选（废弃）
	 * 运营管理--》供应商折扣调整--》按产品筛选
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree/bysku/table",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectJoinSku(JEasyuiRequestBean bean) {
		return infoSupplierCodetableService.selectJoinSku(bean);
	}
	
}
