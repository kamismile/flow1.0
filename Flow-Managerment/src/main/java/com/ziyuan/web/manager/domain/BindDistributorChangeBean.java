package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.BindDistributor;

public class BindDistributorChangeBean {
	private List<BindDistributor> newBind;
	private List<BindDistributor> removeBind;
	private List<BindDistributor> editBind;
	
	public List<BindDistributor> getRemoveBind() {
		return removeBind;
	}
	public void setRemoveBind(List<BindDistributor> removeBind) {
		this.removeBind = removeBind;
	}
	public List<BindDistributor> getEditBind() {
		return editBind;
	}
	public void setEditBind(List<BindDistributor> editBind) {
		this.editBind = editBind;
	}
	public List<BindDistributor> getNewBind() {
		return newBind;
	}
	public void setNewBind(List<BindDistributor> newBind) {
		this.newBind = newBind;
	}
}
