package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.LogQueueSupplierSubmit;
import com.ziyuan.web.manager.dao.LogQueueSupplierSubmitMapper;

@Repository
public class LogQueueSupplierSubmitWrap {
	@Resource
	private LogQueueSupplierSubmitMapper logQueueSupplierSubmitMapper;
	
	@Transactional(readOnly = true)
	public List<LogQueueSupplierSubmit> selectByOrderNo(String order_no) {
		return logQueueSupplierSubmitMapper.selectByOrderNo(order_no);
	}
}
