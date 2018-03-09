package com.shziyuan.flow.cnfmanager.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shziyuan.flow.cnfmanager.service.BaseService;
import com.shziyuan.flow.global.domain.action.ActionResponse;

public class BaseAction<T> {

	private BaseService<T> baseService;
	
	public BaseAction(BaseService<T> baseService) {
		this.baseService = baseService;
	}
	
	@RequestMapping(value = "/table",method=RequestMethod.GET)
	public ActionResponse selectByExample(T domain) {
		return ActionResponse.success(baseService.selectByExample(domain));
	}
	
	@RequestMapping(value = "/table/{id}",method=RequestMethod.GET)
	public ActionResponse selectOne(T domain) {
		List<T> rows = new ArrayList<>(1);
		rows.add(baseService.selectOne(domain));
		return ActionResponse.success(rows);
	}
	
	@RequestMapping(value = "/table",method=RequestMethod.DELETE)
	public ActionResponse deleteById(T domain) {
		baseService.delete(domain);
		return ActionResponse.success();
	}
	
	@RequestMapping(value = "/table",method=RequestMethod.POST)
	public ActionResponse insert(T platform) {
		baseService.insert(platform);
		return ActionResponse.success();
	}
	
	@RequestMapping(value = "/table",method=RequestMethod.PUT)
	public ActionResponse update(T platform) {
		baseService.update(platform);
		return ActionResponse.success();
	}
	
}
