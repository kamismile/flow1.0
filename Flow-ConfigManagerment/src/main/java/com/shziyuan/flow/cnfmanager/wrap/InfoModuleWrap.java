package com.shziyuan.flow.cnfmanager.wrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shziyuan.flow.cnfmanager.dao.InfoModuleMapper;
import com.shziyuan.flow.cnfmanager.domain.InfoModule;

@Repository
public class InfoModuleWrap extends BaseWrap<InfoModule>{

	@Autowired
	protected InfoModuleWrap(InfoModuleMapper baseMapper) {
		super(baseMapper);
	}

}
