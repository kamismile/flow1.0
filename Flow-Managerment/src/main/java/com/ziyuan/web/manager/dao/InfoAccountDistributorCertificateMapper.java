package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.InfoAccountDistributorCertificate;

public interface InfoAccountDistributorCertificateMapper extends ICRUDMapper<InfoAccountDistributorCertificate>{
	public List<InfoAccountDistributorCertificate> selectByDistributorId(int distributor_id);
	public List<InfoAccountDistributorCertificate> selectByPendingId(int pending_id);
}
