package com.ziyuan.web.manager.action.operation.distributor;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.domain.BindDistributorChangeBean;
import com.ziyuan.web.manager.feign.AccountFeign;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.BindDistributorService;
import com.ziyuan.web.manager.utils.ExcelTools;

/**
 * 绑定渠道商信息
 * @author user
 *
 */
@Controller
@RequestMapping("/distributors/bind")
public class BindDistributorAction extends AbstractCRUDAction<BindDistributor> {

	@Resource
	private BindDistributorService bindDistributorService;
	
	@Resource
	private AccountFeign accountFeign;
	
	@Override
	public ICRUDService<BindDistributor> getService() {
		
		return bindDistributorService;
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/binding",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBind(JEasyuiRequestBean bean) {
		return bindDistributorService.selectBind(bean);
	}
	
	/**
	 * 渠道绑定产品筛选
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/binding2/binded",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		return bindDistributorService.selectBind2_binded(bean);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/binding2/nonbinded",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		return bindDistributorService.selectBind2_nonbinded(bean);
	}
	
	/**
	 * 渠道绑定产品-->绑定产品
	 * @param data
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/binding2/change",method = RequestMethod.POST)
	@ResponseBody
	public ActionResponse changeBinding2(Principal user, @RequestBody BindDistributorChangeBean data) {
		
		return accountFeign.bindDiscount(data);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_ADD')")
	@RequestMapping(value = "/change",method = RequestMethod.POST)
	@ResponseBody
	public JEasyuiData changeBinding(Principal user, @RequestBody BindDistributorChangeBean data) {
		return bindDistributorService.changeBinding(user, data);
	}

	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/bySku/tree",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeBySku() {
		return bindDistributorService.selectTreeBySku();
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/byDistributor/tree",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeByDistributor() {
		return bindDistributorService.selectTreeByDistributor();
	}
	
	/**
	 * 按产品筛选（废弃）
	 * 运营管理--》渠道商折扣调整--》按产品筛选
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/bySku/table",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTableBySku() {
		return bindDistributorService.selectTreeBySku();
	}
	
	/**
	 * 按渠道筛选
	 * 运营管理--》渠道商折扣调整--》按渠道筛选
	 * @param bean
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree/table",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeTable(JEasyuiRequestBean bean) {
		return bindDistributorService.selectTreeTable(bean);
	}
	
	/**
	 * 渠道商折扣调整导出
	 * @return
	 */
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree/table/excel",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> exportTreeTable(JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = bindDistributorService.exportTreeTable(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@PreAuthorize("hasPermission('user','AUTH_SELECT')")
	@RequestMapping(value = "/tree2",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectTreeInBind2() {
		return bindDistributorService.selectTreeInBind2();
	}

}
