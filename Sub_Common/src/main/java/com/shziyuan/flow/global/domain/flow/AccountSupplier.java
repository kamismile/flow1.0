package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class AccountSupplier implements Serializable{

	private static final long serialVersionUID = 2108502725429824569L;
	private int id;
	private double banlance;
	private double banlance_alert;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getBanlance() {
		return banlance;
	}
	public void setBanlance(double banlance) {
		this.banlance = banlance;
	}
	public double getBanlance_alert() {
		return banlance_alert;
	}
	public void setBanlance_alert(double banlance_alert) {
		this.banlance_alert = banlance_alert;
	}
}
