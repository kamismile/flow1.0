package com.shziyuan.flow.global.domain.flow.wraped;

import java.io.Serializable;

import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;

public class QueueOrderWrap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfiguredBindBean configuredBindBean;
	private DistributorOrder distributorOrder;
	
	public ConfiguredBindBean getConfiguredBindBean() {
		return configuredBindBean;
	}
	public void setConfiguredBindBean(ConfiguredBindBean configuredBindBean) {
		this.configuredBindBean = configuredBindBean;
	}
	public DistributorOrder getDistributorOrder() {
		return distributorOrder;
	}
	public void setDistributorOrder(DistributorOrder distributorOrder) {
		this.distributorOrder = distributorOrder;
	}
	public QueueOrderWrap(ConfiguredBindBean configuredBindBean, DistributorOrder distributorOrder) {
		super();
		this.configuredBindBean = configuredBindBean;
		this.distributorOrder = distributorOrder;
	}
	public QueueOrderWrap() {
		super();
	}
	
	

}
