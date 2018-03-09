package org.james.common.dao.domain;

import java.util.List;

public class JChartPieDatasets implements IJChartDatasets{
	private String label;
	private List<String> backgroundColor;
	private List<Number> data;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public List<Number> getData() {
		return data;
	}
	public void setData(List<Number> data) {
		this.data = data;
	}
	public List<String> getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(List<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
}
