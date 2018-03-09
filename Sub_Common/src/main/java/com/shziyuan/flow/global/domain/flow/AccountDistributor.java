package com.shziyuan.flow.global.domain.flow;

public class AccountDistributor extends BaseDomain{
	private static final long serialVersionUID = -6714225867482354862L;
	
	private int distributor_id;
	private double banlance;
	private double credit;
	private double receivable;
	private double donation;
	private double banlance_alert;
	private double present_banlance;
	
	public AccountDistributor() {
	
	}
	
	public double getPresent_banlance() {
		return present_banlance;
	}
	public void setPresent_banlance(double present_banlance) {
		this.present_banlance = present_banlance;
	}

	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public double getBanlance() {
		return banlance;
	}
	public void setBanlance(double banlance) {
		this.banlance = banlance;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getReceivable() {
		return receivable;
	}
	public void setReceivable(double receivable) {
		this.receivable = receivable;
	}
	public double getDonation() {
		return donation;
	}
	public void setDonation(double donation) {
		this.donation = donation;
	}
	public double getBanlance_alert() {
		return banlance_alert;
	}
	public void setBanlance_alert(double banlance_alert) {
		this.banlance_alert = banlance_alert;
	}
}
