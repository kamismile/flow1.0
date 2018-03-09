package com.ziyuan.web.distributor.service;

import com.ziyuan.web.distributor.domain.MoreDistributor;

public interface IConfigService {

	MoreDistributor getDistributorConfiguration(String clientCode);

}
