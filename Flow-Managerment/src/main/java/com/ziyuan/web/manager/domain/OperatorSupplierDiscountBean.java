package com.ziyuan.web.manager.domain;

import java.util.List;

public class OperatorSupplierDiscountBean<OPT,LOG> {
	private List<OPT> datas;
	private List<LOG> logs;
	
	public List<OPT> getDatas() {
		return datas;
	}
	public void setDatas(List<OPT> datas) {
		this.datas = datas;
	}
	public List<LOG> getLogs() {
		return logs;
	}
	public void setLogs(List<LOG> logs) {
		this.logs = logs;
	}
}
