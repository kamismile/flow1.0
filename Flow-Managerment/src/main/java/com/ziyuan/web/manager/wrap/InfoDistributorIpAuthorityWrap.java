package com.ziyuan.web.manager.wrap;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.shziyuan.flow.global.domain.flow.InfoDistributorIpAuthority;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoDistributorIpAuthorityMapper;

@Repository
public class InfoDistributorIpAuthorityWrap extends AbstractCRUDWrap<InfoDistributorIpAuthority>{

	@Resource
	private InfoDistributorIpAuthorityMapper infoDistributorIpAuthorityMapper;
	
	@Override
	public ICRUDMapper<InfoDistributorIpAuthority> getMapper() {
		return infoDistributorIpAuthorityMapper;
	}

}
