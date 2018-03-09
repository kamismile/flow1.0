package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.ziyuan.web.manager.dao.InfoDistributorMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;

@Repository
public class InfoDistributorWrap extends AbstractCRUDWrap<InfoDistributor>{

	@Resource
	private InfoDistributorMapper distributorMapper;
	
	@Override
	public ICRUDMapper<InfoDistributor> getMapper() {
		return distributorMapper;
	}

	@Transactional(readOnly=true)
	public List<InfoDistributor> selectDistributorNames() {
		// TODO Auto-generated method stub
		return distributorMapper.selectDistributorNames();
	}

	@Transactional(readOnly=true)
	public int selectCountByCode(String code) {
		// TODO Auto-generated method stub
		return distributorMapper.selectCountByCode(code);
	}
	
	@Transactional(readOnly = false)
	public void updateUrl(InfoDistributor domain) {
		distributorMapper.updateUrl(domain);
	}
	
	@Transactional(readOnly = false)
	public void verifySuccess(int id) {
		distributorMapper.verifySuccess(id);
	}
	@Transactional(readOnly = false)
	public void verifyFaild(int id) {
		distributorMapper.verifyFaild(id);
	}

	@Transactional(readOnly = true)
	public int selectIDByName(String name) {
		// TODO Auto-generated method stub
		return distributorMapper.selectIDByName(name);
	}
}
