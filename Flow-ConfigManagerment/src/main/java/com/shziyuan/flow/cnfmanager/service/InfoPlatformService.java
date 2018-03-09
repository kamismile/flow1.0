package com.shziyuan.flow.cnfmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shziyuan.flow.cnfmanager.domain.InfoPlatform;
import com.shziyuan.flow.cnfmanager.wrap.InfoPlatformWrap;

@Service
public class InfoPlatformService extends BaseService<InfoPlatform> {
	@Autowired
	public InfoPlatformService(InfoPlatformWrap infoPlatformWrap) {
		super(infoPlatformWrap);
	}
}
