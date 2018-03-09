package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.InfoProvinceMapper;
import com.ziyuan.web.manager.domain.InfoProvinceBean;

@Repository
public class InfoProvinceWrap {
	@Resource
	private InfoProvinceMapper infoProvinceMapper;
	
	@Transactional(readOnly = true)
	public List<InfoProvinceBean> select() {
		return infoProvinceMapper.select();
	}
}
