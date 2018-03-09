package com.shziyuan.flow.cnfmanager.conf.security;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

/**
 * 内存缓存登录用户数据 实现remember me
 * @author james.hu
 *
 */
@Repository
public class InMemoryOAuthRequestStorage extends HashMap<String, InMemoryOAuthParam>{
	
	private static final long serialVersionUID = 1L;
	
	public static final String COOKIE_ID_NAME = "sol-gu-t";		// 用于标识的cookie id
	
	/**
	 * 覆盖了原get方法
	 * 每次调用时将清除一天前的缓存数据,并移除当前获取到的数据
	 */
	@Override
	public InMemoryOAuthParam get(Object key) {
		clearMoreThenDay();
		InMemoryOAuthParam out = super.get(key);
		this.remove(key);
		return out;
	}
	
	/**
	 * 清除一天之前的数据
	 */
	private void clearMoreThenDay() {
		long now = System.currentTimeMillis();
		long passtime = now - 86400000;
		List<String> uuids = this.entrySet().stream()
			.filter(entry -> entry.getValue().getInsert_time() < passtime).map(entry -> entry.getKey())
			.collect(Collectors.toList());
		if(uuids.size() > 0) {
			for(String uuid : uuids) {
				this.remove(uuid);
			}
		}
	}
}
