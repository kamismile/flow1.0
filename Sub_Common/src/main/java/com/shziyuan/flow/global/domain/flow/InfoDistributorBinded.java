package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class InfoDistributorBinded extends InfoDistributor implements Serializable{

	private static final long serialVersionUID = -2114181599809487336L;
    
    // 绑定关系
    private BindDistributor bind;
    
    // 绑定的SKU列表
    private InfoSku sku;
    
    // 账务信息
    private AccountDistributor accountDistributor;

	public BindDistributor getBind() {
		return bind;
	}

	public void setBind(BindDistributor bind) {
		this.bind = bind;
	}

	public InfoSku getSku() {
		return sku;
	}

	public void setSku(InfoSku sku) {
		this.sku = sku;
	}

	public AccountDistributor getAccountDistributor() {
		return accountDistributor;
	}

	public void setAccountDistributor(AccountDistributor accountDistributor) {
		this.accountDistributor = accountDistributor;
	}

}
