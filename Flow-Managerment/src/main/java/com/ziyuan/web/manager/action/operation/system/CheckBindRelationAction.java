package com.ziyuan.web.manager.action.operation.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.ziyuan.web.manager.service.impl.CheckBindRelationService;

/**
 * 检查绑定关系
 * @author user
 *
 */
@RestController
public class CheckBindRelationAction {
	
	@Autowired
	private CheckBindRelationService checkBindRelationService;

	/**
	 * 检查数据绑定关系
	 * @return
	 */
	@GetMapping("/checkBindRelationAction")
	public JEasyuiData check(){
		
		return checkBindRelationService.check();
	}
	
	/**
	 * 获取常量表产品类型信息
	 */
	@GetMapping("/getSkuType")
	public JEasyuiData getSkuType(){
		
		return checkBindRelationService.getSkuType();
	}
	
}
