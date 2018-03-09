package com.ziyuan.web.manager.action.operation.distributor;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shziyuan.flow.global.domain.flow.InfoDistributorIpAuthority;
import com.ziyuan.web.manager.action.AbstractCRUDAction;
import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.service.InfoDistributorIpAuthorityService;

/**
 * 配置ip鉴权
 * @author user
 *
 */
@Controller
@RequestMapping("/distributors/ip")
public class InfoDistributorIpAuthorityAction extends AbstractCRUDAction<InfoDistributorIpAuthority>{

	@Resource
	private InfoDistributorIpAuthorityService infoDistributorIpAuthorityService;
	
	@Override
	public ICRUDService<InfoDistributorIpAuthority> getService() {
		return infoDistributorIpAuthorityService;
	}
	
}
