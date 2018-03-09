package com.shziyuan.flow.global.domain.flow;

public class InfoDistributorIpAuthority extends BaseDomain{
	
	private static final long serialVersionUID = 5712738660202016445L;
	private int id;
	private int distributor_id;
	
	private String ip;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistributor_id() {
		return distributor_id;
	}

	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}