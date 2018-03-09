package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.WebAccountDistributorAuthorityMapper;

@Repository
public class WebAccountDistributorAuthorityWrap extends AbstractCRUDWrap<WebAccountDistributorAuthority>{
	@Resource
	private WebAccountDistributorAuthorityMapper webAccountDistributorAuthorityMapper;
	
	@Override
	public ICRUDMapper<WebAccountDistributorAuthority> getMapper() {
		return webAccountDistributorAuthorityMapper;
	}
	
	@Transactional(readOnly = true)
	public List<WebAccountDistributorAuthority> selectByDistributorId(int distributor_id) {
		return webAccountDistributorAuthorityMapper.selectByDistributorId(distributor_id);
	}
	
	@Transactional(readOnly = false)
	public void deleteByDistributorId(WebAccountDistributorAuthority domain) {
		webAccountDistributorAuthorityMapper.deleteByDistributorId(domain);
	}

}
