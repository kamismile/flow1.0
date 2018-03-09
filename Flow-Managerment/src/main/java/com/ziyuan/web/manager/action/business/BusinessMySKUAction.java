package com.ziyuan.web.manager.action.business;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.service.impl.BindDistributorService;
import com.ziyuan.web.manager.utils.ExcelTools;


/**
 * 商户平台我的产品
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/business/mysku")
public class BusinessMySKUAction {

	@Resource
	private BindDistributorService bindDistributorService;
	
	/**
	 * 导出
	 * @param user
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportBusinessSkus(Principal user, JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = bindDistributorService.exportBusinessSkus(user, bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	/**
	 * 查询
	 * @param user
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/table",method = RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBusinessSkusPages(Principal user, JEasyuiRequestBean bean) {
		String username = user.getName();
		bean.getParam().put("username", username);
		return bindDistributorService.selectBusinessSkusPages(bean);
	}
}
