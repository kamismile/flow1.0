package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.AccountSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.StatisticsSupplier;

public class InfoSupplierBean extends InfoSupplier {

	private static final long serialVersionUID = 6983929510683178525L;

	// 绑定关系
	private StatisticsSupplier statisticsSupplier;

	private AccountSupplier accountSupplier;

	private List<InfoSupplierCodetableBean> codetable;
	
	public StatisticsSupplier getStatisticsSupplier() {
		return statisticsSupplier;
	}

	public void setStatisticsSupplier(StatisticsSupplier statisticsSupplier) {
		this.statisticsSupplier = statisticsSupplier;
	}

	public AccountSupplier getAccountSupplier() {
		return accountSupplier;
	}

	public void setAccountSupplier(AccountSupplier accountSupplier) {
		this.accountSupplier = accountSupplier;
	}

	public List<InfoSupplierCodetableBean> getCodetable() {
		return codetable;
	}

	public void setCodetable(List<InfoSupplierCodetableBean> codetable) {
		this.codetable = codetable;
	}

}
