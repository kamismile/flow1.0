package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;

public interface WebAccountAuthorityMapper extends ICRUDMapper<WebAccountAuthority>{
	public List<WebAccountAuthority> selectByAccountId(int account_id);
	
	public void deleteByAccountId(int account_id);
}
