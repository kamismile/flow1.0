package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.WebAccountAuthorityMapper;

@Repository
public class WebAccountAuthorityWrap extends AbstractCRUDWrap<WebAccountAuthority>{
	@Resource
	private WebAccountAuthorityMapper webAccountAuthorityMapper;
	
	@Override
	public ICRUDMapper<WebAccountAuthority> getMapper() {
		return webAccountAuthorityMapper;
	}
	
	@Transactional(readOnly = true)
	public List<WebAccountAuthority> selectByAccountId(int account_id) {
		return webAccountAuthorityMapper.selectByAccountId(account_id);
	}

}
