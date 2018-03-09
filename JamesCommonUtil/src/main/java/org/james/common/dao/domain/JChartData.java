package org.james.common.dao.domain;

import java.util.List;

public class JChartData {
	private List<String> labels;
	private List<IJChartDatasets> datasets;

	public List<IJChartDatasets> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<IJChartDatasets> datasets) {
		this.datasets = datasets;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
}
