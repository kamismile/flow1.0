package com.shziyuan.flow.cnfmanager.daoutil;

import java.util.HashMap;
import java.util.Map;

public class BaseSqlCache {
	private Map<String, _Cache> cacheMap = new HashMap<>();
	
	_Cache getCache(String tablename) {
		_Cache cache = cacheMap.get(tablename);
		if(cache == null) {
			cache = new _Cache();
			cacheMap.put(tablename, cache);
		}
		return cache;
	}
	public void putSelectByExample(String tablename,String value) {
		_Cache cache = getCache(tablename);
		cache.selectByExample = value;
	}
	
	public void putInsert(String tablename,String value) {
		_Cache cache = getCache(tablename);
		cache.insert = value;
	}
	
	public void putUpdate(String tablename,String value) {
		_Cache cache = getCache(tablename);
		cache.update = value;
	}
	
	public void putDelete(String tablename,String value) {
		_Cache cache = getCache(tablename);
		cache.delete = value;
	}
	
	public String getSelectByExample(String tablename) {
		_Cache cache = getCache(tablename);
		return cache.selectByExample;
	}
	
	public String getInsert(String tablename) {
		_Cache cache = getCache(tablename);
		return cache.insert;
	}
	
	public String getUpdate(String tablename) {
		_Cache cache = getCache(tablename);
		return cache.update;
	}
	
	public String getDelete(String tablename) {
		_Cache cache = getCache(tablename);
		return cache.delete;
	}
	
	
	private class _Cache {
		String selectByExample;
		String insert;
		String update;
		String delete;
	}
}
