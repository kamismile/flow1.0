package com.ziyuan.web.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;

public interface InfoDistributorMapper extends ICRUDMapper<InfoDistributor>{

	List<InfoDistributor> selectDistributorNames();

	public void updateUrl(InfoDistributor domain);
	int selectCountByCode(String code);

	public void verifySuccess(int id);
	public void verifyFaild(int id);

	int selectIDByName(@Param("name") String name);
}
