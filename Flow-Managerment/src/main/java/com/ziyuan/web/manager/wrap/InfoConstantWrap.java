package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.InfoConstantMapper;
import com.ziyuan.web.manager.domain.InfoConstant;

@Repository
public class InfoConstantWrap {

	@Resource
	private InfoConstantMapper infoConstantMapper;
	
	@Transactional(readOnly = true)
	public List<InfoConstant> selectSkuType(){
		return infoConstantMapper.selectSkuType();
	}
}
