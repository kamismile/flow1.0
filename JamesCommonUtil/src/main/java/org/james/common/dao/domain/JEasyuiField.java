package org.james.common.dao.domain;

public class JEasyuiField {
	private String field;
	private String tooltips;
	
	public JEasyuiField() {
	}
	
	public JEasyuiField(String field, String tooltips) {
		this.field = field;
		this.tooltips = tooltips;
	}

	public String getTooltips() {
		return tooltips;
	}
	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
}
