package com.ziyuan.web.manager.dao;

import org.apache.ibatis.annotations.Param;

import com.shziyuan.flow.global.domain.flow.LogAccountDistributorPresent;

public interface LogAccountDistributorPresentMapper extends ICRUDMapper<LogAccountDistributorPresent> {
	public int selectAlreadyPresent(@Param("year") String year,@Param("month") String month);
}
