package org.james.common.dao.domain;

import java.util.List;

public class JChartLineDatasets implements IJChartDatasets{
	private String label;
	private String backgroundColor;
	private String borderColor;
	private List<Number> data;
	private boolean fill;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public List<Number> getData() {
		return data;
	}
	public void setData(List<Number> data) {
		this.data = data;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
}
