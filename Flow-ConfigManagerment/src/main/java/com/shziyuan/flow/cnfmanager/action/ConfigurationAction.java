package com.shziyuan.flow.cnfmanager.action;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.cnfmanager.service.ConfigurationService;
import com.shziyuan.flow.global.domain.action.ActionResponse;

@RestController
@RequestMapping("/Sub_SessionManagerment/config")
public class ConfigurationAction {
	
	@Resource
	private ConfigurationService configurationService;

	@RequestMapping(value="/module/{moduleId}",method=RequestMethod.GET)
	public ActionResponse getConfigByModuleId(@PathVariable("moduleId") int moduleId) {
		return ActionResponse.success(configurationService.getConfigByModuleId(moduleId));
	}
	
//	@RequestMapping(value="/module/session",method=RequestMethod.DELETE)
//	public ResponseData deleteModuleSessionByKey(String key) {
//		return configurationService.deleteModuleSessionByKey(key);
//		
//	}
//	
//	@RequestMapping(value="/module/session",method=RequestMethod.PUT)
//	public ResponseData updateModuleSession(RequestBean bean) {
//		return configurationService.updateModuleSession(bean);
//		
//	}
//	
//	@RequestMapping(value="/init/all",method=RequestMethod.POST)
//	public ResponseData initAllModuleSession(RequestBean bean) {
//		return configurationService.initAllModuleSession(bean);
//		
//	}
//	
//	@RequestMapping(value="/init",method=RequestMethod.POST)
//	public ResponseData initModuleSession(RequestBean bean) {
//		return configurationService.initModuleSession(bean);
//		
//	}
}
