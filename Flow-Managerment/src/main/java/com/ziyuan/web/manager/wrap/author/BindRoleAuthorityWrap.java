package com.ziyuan.web.manager.wrap.author;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.author.BindRoleAuthorityMapper;
import com.ziyuan.web.manager.domain.author.BindRoleAuthorityBean;
import com.ziyuan.web.manager.wrap.AbstractCRUDWrap;
@Repository
public class BindRoleAuthorityWrap extends AbstractCRUDWrap<BindRoleAuthorityBean>{
	@Resource
	private BindRoleAuthorityMapper bindRoleAuthorityMapper;

	@Override
	public ICRUDMapper<BindRoleAuthorityBean> getMapper() {
		// TODO Auto-generated method stub
		return bindRoleAuthorityMapper;
	}

}
