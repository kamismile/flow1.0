package com.ziyuan.web.manager.dao;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.InfoDistributorCertificate;

public interface InfoDistributorCertificateMapper extends ICRUDMapper<InfoDistributorCertificate>{
	public List<InfoDistributorCertificate> selectByDistributorId(int distributor_id);
}
