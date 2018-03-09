package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;

public interface LogQueueSupplierSubmitMapper {
	public List<LogQueueSupplierSubmit> selectByOrderNo(String order_no);
}
