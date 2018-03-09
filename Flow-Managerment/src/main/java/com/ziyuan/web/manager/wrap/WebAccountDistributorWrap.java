package com.ziyuan.web.manager.wrap;

import javax.annotation.Resource;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributor;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.stereotype.Repository;

import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.WebAccountDistributorMapper;

@Repository
public class WebAccountDistributorWrap extends AbstractCRUDWrap<WebAccountDistributor>{
	
	@Resource
	private WebAccountDistributorMapper webAccountDistributorMapper;
	
	@Override
	public ICRUDMapper<WebAccountDistributor> getMapper() {
		return webAccountDistributorMapper;
	}

	public JEasyuiData updateAccountInfo(InfoDistributor bean) {
		webAccountDistributorMapper.updateAccountInfo(bean);
		return new JEasyuiData(true,"");
	}

}
