package com.ziyuan.web.manager.action.operation.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.impl.WebAccountAuthorityService;

@RequestMapping("/system/accounts/authority")
@Controller
public class WebAccountAuthorityAction extends AbstractCRUDAction<WebAccountAuthority>{
	@Resource
	private WebAccountAuthorityService webAccountAuthorityService;
	
	@Override
	public ICRUDService<WebAccountAuthority> getService() {
		return webAccountAuthorityService;
	}

}
