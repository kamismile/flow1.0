package com.ziyuan.web.distributor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziyuan.web.distributor.dao.ConfigDao;
import com.ziyuan.web.distributor.domain.MoreDistributor;
import com.ziyuan.web.distributor.service.IConfigService;

@Service
public class ConfigServiceImpl implements IConfigService{

	@Resource
	private ConfigDao configDao;
	
	@Override
	public MoreDistributor getDistributorConfiguration(String clientCode) {
		MoreDistributor more = configDao.getDistributorByCode(clientCode);
		if (more == null) {
			return null;
		}
		List<String> ips = configDao.loadIpsByDistributorId(more.getId());
		if (ips == null || ips.size() == 0) {
			return more;
		}
		more.setIpAuthoritys(ips);
		return more;
	}

}
