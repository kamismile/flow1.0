package com.shziyuan.flow.irs.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.InfoCitySubmobile;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.irs.dao.InfoCitySubmobileDao;

@Service
public class InfoCitySubmobileService extends com.shziyuan.flow.redis.base.service.RedisInfoCitySubmobileService{
	
	@Autowired
	private InfoCitySubmobileDao infoCitySubmobileDao;
	
	public void buildRedis() {
		int fetchSize = 10000;		
		Map<String,InfoCitySubmobile> dataMap = new HashMap<>(fetchSize);
		
		RowCallbackHandler callback = new RowCallbackHandler() {
			
			long rownum = 1;
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String submobile = rs.getString("submobile");
				int provinceid = rs.getInt("provinceid");
				InfoCitySubmobile bean = new InfoCitySubmobile();
				bean.setSubmobile(submobile);
				bean.setProvinceid(provinceid);
				dataMap.put(submobile, bean);
				
				if(rownum ++ > fetchSize) {
					redisTemplate.opsForHash().putAll(RedisKey.INFO_CITY_SUBMOBILE_KEY.val, dataMap);
					LoggerUtil.console.info("[InfoCitySubmobile]记录插入到Redis,当前记录集大小:{}",dataMap.size());
					dataMap.clear();
					rownum = 1;
				}
				
			}
			
		};
		
		infoCitySubmobileDao.selectAll(callback);
		if(dataMap.size() > 0) {
			redisTemplate.opsForHash().putAll(RedisKey.INFO_CITY_SUBMOBILE_KEY.val, dataMap);
			LoggerUtil.console.info("[InfoCitySubmobile]RS最后数据集记录插入到Redis,当前记录集大小:{}",dataMap.size());
		}
	}
	
}
