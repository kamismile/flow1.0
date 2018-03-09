package com.shziyuan.flow.irs.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.irs.service.BuildRedisService;
import com.shziyuan.flow.irs.service.InfoCitySubmobileService;

@RestController
@RequestMapping("/refresh")
public class RefreshAction {
	@Autowired
	private BuildRedisService buildRedisService;
	
	@Autowired
	private InfoCitySubmobileService infoCitySubmobileService;
	
	/**
	 * 刷新所有配置
	 * # 不重新加载手机号省份数据
	 * @return
	 */
	@PutMapping("/all")
	public ConfiguredBindBean refreshAll() {
		try {
			buildRedisService.loadBindDistributor();
			buildRedisService.loadBindSupplier();
			return ConfiguredBindBean.success();
		} catch (Exception e) {
			return ConfiguredBindBean.faild(e);
		}
	}
	
	@PutMapping("/submobile")
	public ConfiguredBindBean refreshSubmobile() {
		try {
			infoCitySubmobileService.buildRedis();
			return ConfiguredBindBean.success();
		} catch (Exception e) {
			return ConfiguredBindBean.faild(e);
		}
	}
	
}
