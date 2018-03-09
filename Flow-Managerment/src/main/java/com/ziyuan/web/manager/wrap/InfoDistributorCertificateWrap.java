package com.ziyuan.web.manager.wrap;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoDistributorCertificate;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoDistributorCertificateMapper;

@Repository
public class InfoDistributorCertificateWrap extends AbstractCRUDWrap<InfoDistributorCertificate>{

	@Resource
	private InfoDistributorCertificateMapper infoDistributorCertificateMapper;
	
	@Override
	public ICRUDMapper<InfoDistributorCertificate> getMapper() {
		return infoDistributorCertificateMapper;
	}
	
	@Transactional(readOnly = true)
	public List<InfoDistributorCertificate> selectByDistributorId(int distributor_id) {
		return infoDistributorCertificateMapper.selectByDistributorId(distributor_id);
	}

}
