package com.ziyuan.web.manager.dao;

import java.util.List;

import com.ziyuan.web.manager.domain.LogPlatformOrder;

public interface LogPlatformOrderMapper {
	public List<LogPlatformOrder> selectByOrderNo(String order_no);
}
