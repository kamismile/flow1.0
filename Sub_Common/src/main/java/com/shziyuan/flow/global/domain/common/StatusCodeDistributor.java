package com.shziyuan.flow.global.domain.common;

public class StatusCodeDistributor {
	private Status reportSuccess;
	private Status reportFaild;
	private Status reportHold;
	public Status getReportSuccess() {
		return reportSuccess;
	}
	public void setReportSuccess(Status reportSuccess) {
		this.reportSuccess = reportSuccess;
	}
	public Status getReportFaild() {
		return reportFaild;
	}
	public void setReportFaild(Status reportFaild) {
		this.reportFaild = reportFaild;
	}
	public Status getReportHold() {
		return reportHold;
	}
	public void setReportHold(Status reportHold) {
		this.reportHold = reportHold;
	}
}
