package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.LogWebDistributorDiscountChange;

public interface LogWebDistributorDiscountChangeMapper extends ICRUDMapper<LogWebDistributorDiscountChange>{

	List<LogWebDistributorDiscountChange> export(Map<String, String> param);

}
