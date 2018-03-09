package com.ziyuan.web.manager.action.business;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.service.impl.InfoDistributorService;
import com.ziyuan.web.manager.service.impl.OrderService;
import com.ziyuan.web.manager.utils.DateUtils;
import com.ziyuan.web.manager.utils.ExcelTools;



/**
 * 商户订单管理
 * @author yangyl
 *
 */
@Controller
@RequestMapping("/business/order")
public class BusinessOrderAction {

	@Resource
	private OrderService orderService;
	
	@Resource
	private InfoDistributorService infoDistributorService;
	
	/**
	 * 查询渠道商订单
	 * @param user
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/table",method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData selectBusinessOrder(Principal user, JEasyuiRequestBean bean) {
		int id = infoDistributorService.selectIDByName(user.getName());
		bean.getParam().put("distributor_id", String.valueOf(id));
		JEasyuiData result = orderService.select(bean);
		return result;
	}
	
	/**
	 * 导出订单
	 * @param user
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportOrder(Principal user, JEasyuiRequestBean bean) {
		int id = infoDistributorService.selectIDByName(user.getName());
		bean.getParam().put("distributor_id", String.valueOf(id));
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportOrder(bean);
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@RequestMapping(value="/account/excel",method=RequestMethod.GET)
	public ResponseEntity<byte[]> exportDistributorAccount(Principal user, JEasyuiRequestBean bean) {
		String fileName = bean.getParam().get("filename");
		ByteArrayOutputStream bOutputStream = orderService.exportDistributorAccount(user, bean);	
		return ExcelTools.export(bOutputStream, fileName);
	}
	
	@RequestMapping(value="/account", method=RequestMethod.GET)
	@ResponseBody
	public JEasyuiData listAccountStatics(Principal user, JEasyuiRequestBean bean) {
		int id = infoDistributorService.selectIDByName(user.getName());
		bean.getParam().put("distributor_id", String.valueOf(id));
		return orderService.listAccountStatics(bean);
	}
}
