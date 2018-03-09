package com.ziyuan.web.distributor.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.ziyuan.web.distributor.dao.AccountDao;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.MoreDistributor;
import com.ziyuan.web.distributor.service.IAccountService;
import com.ziyuan.web.distributor.service.IConfigService;
import com.ziyuan.web.distributor.service.ResponseBuilder;
import com.ziyuan.web.distributor.service.filter.FilterChain;
import com.ziyuan.web.distributor.service.filter.LoadedGroovyFilter;

@Service
public class AccountService implements IAccountService{

	@Resource
	private AccountDao accountDao;
	
	@Autowired
	private IConfigService configService;		// 配置缓存模块接口
	
	@Autowired
	private LoadedGroovyFilter loadedGroovyFilter;		// 已加载的groovy filter类
	
	@Autowired
	private StatusCode statusCode;
	
	@Autowired
	private ResponseBuilder responseBuilder;		// 响应结果构造器接口 由groovy实现

	@Override
	public Object getBalance(HttpServletRequest request, HttpServletResponse response, DistributorStatus config) {
		//加载渠道所有配置信息
		MoreDistributor more =  configService.getDistributorConfiguration(config.getClientCode());
		if(more != null) {
			// 获取配置成功,进行过滤器校验
			FilterChain fc = new FilterChain(loadedGroovyFilter.getLoadedFilters());
			fc.doFilter(request, response, config, more);
			
			// 校验失败,返回错误结果
			if(fc.isCheckFaild()) {
				return returnFaild(config,fc.getErrorStatus());
			}
		} else {
			// 
			Status status = statusCode.getDwi().getParamError();
			status.setMsg(status.getMsg());
			return returnFaild(config, status);
		}
		//查询账户余额
		AccountDistributor account = accountDao.getAccountByDistributorId(more.getId());
		return responseBuilder.success(account);
	}
	
	private Object returnFaild(DistributorStatus config,Status status) {
		return responseBuilder.faild(status);
	}

	
}
