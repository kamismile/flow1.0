package com.ziyuan.web.manager.service.fmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ziyuan.web.manager.domain.InfoProvinceBean;
import com.ziyuan.web.manager.wrap.InfoProvinceWrap;

@Component
public class Formatter implements InitializingBean{
	private final Map<Integer,String> SCOPE = new HashMap<Integer, String>();
	
	private void initScopeData() {
		SCOPE.put(1, "全国");
		SCOPE.put(2, "省内");
		SCOPE.put(3, "省漫");
	}
	
	private final Map<Integer,String> RENTTYPE = new HashMap<>();
	
	private void initRentTypeData() {
		RENTTYPE.put(1, "月租");
		RENTTYPE.put(2, "日租");
		RENTTYPE.put(3, "三日包");
		RENTTYPE.put(4, "七日包");
	}
	
	private final Map<Integer,String> SUCCESS = new HashMap<>();
	
	private void initSuccess() {
		SUCCESS.put(80, "成功");
		SUCCESS.put(81, "失败");
		SUCCESS.put(92, "处理中");
	}
	
	private Map<Integer,String> PROVINCE = new HashMap<>();
	
	public Formatter() {
		initScopeData();
		initRentTypeData();
		initSuccess();
	}
	
	// province
	@Resource
	private InfoProvinceWrap infoProvinceWrap;
	
	private void initProvinceData() {
		List<InfoProvinceBean> provinces = infoProvinceWrap.select();
		provinces.forEach(p -> PROVINCE.put(p.getProvinceid(), p.getProvince()));
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		initProvinceData();
	}
	
	
	// getter
	public String getScope(Integer scope_cid) {
		return SCOPE.get(scope_cid);
	}
	
	public String getRentType(Integer rent_type) {
		return RENTTYPE.get(rent_type);
	}
	
	public String getProvince(Integer provinceid) {
		return PROVINCE.get(provinceid);
	}
	
	public String getSuccess(int success) {
		return SUCCESS.get(success);
	}
}
