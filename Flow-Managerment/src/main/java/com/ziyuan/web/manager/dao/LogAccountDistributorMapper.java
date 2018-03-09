package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.LogAccountDistributor;

public interface LogAccountDistributorMapper extends ICRUDMapper<LogAccountDistributor>{

	List<LogAccountDistributor> export(Map<String, String> param);

}
