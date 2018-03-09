package com.ziyuan.web.manager.wrap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ziyuan.web.manager.dao.LogPlatformOrderMapper;
import com.ziyuan.web.manager.domain.LogPlatformOrder;

@Repository
public class LogPlatformOrderWrap {
	@Resource
	private LogPlatformOrderMapper logPlatformOrderMapper;
	
	@Transactional(readOnly = true)
	public List<LogPlatformOrder> selectByOrderNo(String order_no) {
		return logPlatformOrderMapper.selectByOrderNo(order_no);
	}
}
