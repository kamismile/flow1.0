package com.shziyuan.flow.cnfmanager.wrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shziyuan.flow.cnfmanager.dao.InfoPlatformMapper;
import com.shziyuan.flow.cnfmanager.domain.InfoPlatform;

@Repository
public class InfoPlatformWrap extends BaseWrap<InfoPlatform>{
	private InfoPlatformMapper infoPlatformMapper;
	
	@Autowired
	public InfoPlatformWrap(InfoPlatformMapper infoPlatformMapper) {
		super(infoPlatformMapper);
		this.infoPlatformMapper = infoPlatformMapper;
	}
}
