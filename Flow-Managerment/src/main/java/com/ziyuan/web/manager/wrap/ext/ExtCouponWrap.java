package com.ziyuan.web.manager.wrap.ext;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.ext.ExtCouponMapper;
import com.ziyuan.web.manager.domain.ext.ECoupon;
import com.ziyuan.web.manager.wrap.AbstractCRUDWrap;

@Repository
public class ExtCouponWrap extends AbstractCRUDWrap<ECoupon>{

	@Resource
	private ExtCouponMapper extCouponMapper;
		
	@Override
	public ICRUDMapper<ECoupon> getMapper() {
		return extCouponMapper;
	}

	
}
