package com.ziyuan.web.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziyuan.web.manager.domain.InfoProvinceBean;
import com.ziyuan.web.manager.wrap.InfoProvinceWrap;

@Service
public class InfoProvinceService {
	@Resource
	private InfoProvinceWrap infoProvinceWrap;
	
	public List<InfoProvinceBean> select() {
		return infoProvinceWrap.select();
	} 

}
