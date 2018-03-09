package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import com.shziyuan.flow.global.domain.flow.LogWebSupplierCodetableDiscountChange;

public interface LogWebSupplierCodetableDiscountChangeMapper extends ICRUDMapper<LogWebSupplierCodetableDiscountChange>{

	List<LogWebSupplierCodetableDiscountChange> export(Map<String, String> param);

}
