package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.BindSupplier;

public class BindSupplierChangeBean {
	private List<BindSupplier> newBind;
	private List<BindSupplier> removeBind;
	private List<BindSupplier> editBind;
	
	public List<BindSupplier> getNewBind() {
		return newBind;
	}
	public void setNewBind(List<BindSupplier> newBind) {
		this.newBind = newBind;
	}
	public List<BindSupplier> getRemoveBind() {
		return removeBind;
	}
	public void setRemoveBind(List<BindSupplier> removeBind) {
		this.removeBind = removeBind;
	}
	public List<BindSupplier> getEditBind() {
		return editBind;
	}
	public void setEditBind(List<BindSupplier> editBind) {
		this.editBind = editBind;
	}
	
}
