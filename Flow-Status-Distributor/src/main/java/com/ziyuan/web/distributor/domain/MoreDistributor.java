package com.ziyuan.web.distributor.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;

public class MoreDistributor extends InfoDistributor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> ipAuthoritys;

	public List<String> getIpAuthoritys() {
		return ipAuthoritys;
	}

	public void setIpAuthoritys(List<String> ipAuthoritys) {
		this.ipAuthoritys = ipAuthoritys;
	}
	
	

}
