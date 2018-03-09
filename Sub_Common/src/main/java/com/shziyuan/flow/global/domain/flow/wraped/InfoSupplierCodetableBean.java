package com.shziyuan.flow.global.domain.flow.wraped;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;

public class InfoSupplierCodetableBean extends InfoSupplierCodetable{

	private static final long serialVersionUID = 1034484978470236716L;

	private InfoSupplier supplier;
	private boolean bindEnabled;

	public InfoSupplierCodetableBean() {
	
	}
	
	public InfoSupplierCodetableBean(InfoSupplierCodetable code) {
		setId(code.getId());
		setSupplier_id(code.getSupplier_id());
		setProvinceid(code.getProvinceid());
		setOperator(code.getOperator());
		setScope_cid(code.getScope_cid());
		setVia_attribute(code.getVia_attribute());
		setCode(code.getCode());
		setName(code.getName());
		setDiscount(code.getDiscount());
		setPrice(code.getPrice());
		setIf_attribute(code.getIf_attribute());
		setEnabled(code.isEnabled());
		setPkgsize(code.getPkgsize());
		setInfo_ptype(code.getInfo_ptype());
		setInfo_net(code.getInfo_net());
		setRent_type(code.getRent_type());
		setCache_mode(code.isCache_mode());
	}
	
	public InfoSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(InfoSupplier supplier) {
		this.supplier = supplier;
	}

	public boolean isBindEnabled() {
		return bindEnabled;
	}

	public void setBindEnabled(boolean bindEnabled) {
		this.bindEnabled = bindEnabled;
	}
	
	@Override
	public String showname() {
		return getName();
	}
}
