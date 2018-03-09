package com.ziyuan.web.manager.action.operation.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ziyuan.web.manager.domain.InfoProvinceBean;
import com.ziyuan.web.manager.service.impl.InfoProvinceService;

@RestController
@RequestMapping("/province")
public class ProvinceAction {
	@Resource
	private InfoProvinceService infoProvinceService;
	
	/**
	 * 获取省份常量
	 * @return
	 */
	@RequestMapping(value="/name",method=RequestMethod.GET)
	@ResponseBody
	public List<InfoProvinceBean> selectProvinceNames() {
		return infoProvinceService.select();
	}
}
