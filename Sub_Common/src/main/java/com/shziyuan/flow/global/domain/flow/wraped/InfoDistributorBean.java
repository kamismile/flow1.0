package com.shziyuan.flow.global.domain.flow.wraped;

import java.util.ArrayList;
import java.util.List;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;

public class InfoDistributorBean extends InfoDistributor{

	private static final long serialVersionUID = 1L;

	private List<String> ipAuthoritys;
	
	private AccountDistributor accountDistributor;

	public List<String> getIpAuthoritys() {
		return ipAuthoritys;
	}

	public void setIpAuthoritys(List<String> ipAuthoritys) {
		this.ipAuthoritys = ipAuthoritys;
	}
	
	public void addIpAuthority(String ip) {
		if(ipAuthoritys == null)
			ipAuthoritys = new ArrayList<>();
		ipAuthoritys.add(ip);
	}
	
	public boolean containIp(String ip) {
		return ipAuthoritys.contains(ip);
	}

	public AccountDistributor getAccountDistributor() {
		return accountDistributor;
	}

	public void setAccountDistributor(AccountDistributor accountDistributor) {
		this.accountDistributor = accountDistributor;
	}
}
