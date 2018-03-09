package com.shziyuan.flow.global.domain.stream;

import java.util.List;

public class ConfigRefreshDomain {
	private int id;
	private List<Integer> ids;
	private List<String> targets;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public List<String> getTargets() {
		return targets;
	}
	public void setTargets(List<String> targets) {
		this.targets = targets;
	}
	
	@Override
	public String toString() {
		return "ConfigRefreshDomain [id=" + id + ", ids=" + ids + ", targets=" + targets + "]";
	}
}
