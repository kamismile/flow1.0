package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;

public class InfoSupplierWithCodetableBean extends InfoSupplier{
	private static final long serialVersionUID = 1L;
	
	private List<InfoSupplierCodetableBean> codetable;

	public List<InfoSupplierCodetableBean> getCodetable() {
		return codetable;
	}

	public void setCodetable(List<InfoSupplierCodetableBean> codetable) {
		this.codetable = codetable;
	}
}
