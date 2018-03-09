package com.shziyuan.flow.cnfmanager.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.cnfmanager.domain.InfoModule;
import com.shziyuan.flow.cnfmanager.service.InfoModuleService;

@RestController
@RequestMapping("/module")
public class InfoModuleAction extends BaseAction<InfoModule>{

	@Autowired
	public InfoModuleAction(InfoModuleService infoModuleService) {
		super(infoModuleService);
	}
}
