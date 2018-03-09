package com.shziyuan.flow.cnfmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shziyuan.flow.cnfmanager.domain.InfoModule;
import com.shziyuan.flow.cnfmanager.wrap.InfoModuleWrap;

@Service
public class InfoModuleService extends BaseService<InfoModule>{
	@Autowired
	public InfoModuleService(InfoModuleWrap infoModuleWrap) {
		super(infoModuleWrap);
	}
}
