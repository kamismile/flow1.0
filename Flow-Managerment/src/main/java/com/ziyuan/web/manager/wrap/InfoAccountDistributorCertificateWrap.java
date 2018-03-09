package com.ziyuan.web.manager.wrap;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.InfoAccountDistributorCertificate;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoAccountDistributorCertificateMapper;

@Repository
public class InfoAccountDistributorCertificateWrap extends AbstractCRUDWrap<InfoAccountDistributorCertificate>{

	@Resource
	private InfoAccountDistributorCertificateMapper infoAccountDistributorCertificateMapper;
	
	@Override
	public ICRUDMapper<InfoAccountDistributorCertificate> getMapper() {
		return infoAccountDistributorCertificateMapper;
	}
	
	@Transactional(readOnly = true)
	public List<InfoAccountDistributorCertificate> selectByDistributorId(int distributor_id) {
		return infoAccountDistributorCertificateMapper.selectByDistributorId(distributor_id);
	}

	@Transactional(readOnly = true)
	public List<InfoAccountDistributorCertificate> selectByPendingId(int pending_id) {
		return infoAccountDistributorCertificateMapper.selectByPendingId(pending_id);
	}
}
