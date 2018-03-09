package com.shziyuan.flow.cnfmanager.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.cnfmanager.domain.InfoPlatform;
import com.shziyuan.flow.cnfmanager.service.InfoPlatformService;

@RestController
@RequestMapping("/platform")
public class InfoPlatformAction extends BaseAction<InfoPlatform>{

	@Autowired
	public InfoPlatformAction(InfoPlatformService infoPlatformService) {
		super(infoPlatformService);
	}
}
