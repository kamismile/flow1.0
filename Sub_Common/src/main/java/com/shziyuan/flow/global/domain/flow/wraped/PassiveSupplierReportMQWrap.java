package com.shziyuan.flow.global.domain.flow.wraped;

public class PassiveSupplierReportMQWrap {
	private String reportSource;
	private int supplierId;
	
	public String getReportSource() {
		return reportSource;
	}

	public void setReportSource(String reportSource) {
		this.reportSource = reportSource;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
}
