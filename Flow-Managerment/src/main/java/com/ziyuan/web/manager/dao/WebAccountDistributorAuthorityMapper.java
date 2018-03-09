package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;

public interface WebAccountDistributorAuthorityMapper extends ICRUDMapper<WebAccountDistributorAuthority>{
	public List<WebAccountDistributorAuthority> selectByDistributorId(int distributor_id);
	
	public void deleteByDistributorId(WebAccountDistributorAuthority domain);
}
