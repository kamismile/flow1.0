package com.shziyuan.flow.global.domain.flow;

public class BindSupplier extends BaseDomain{

	private static final long serialVersionUID = -141791767629100410L;
	
	private int id;
	private int sku_id;
	private int pkg_id;
	private int supplier_id;
	private int supplier_code_id;
	private boolean enabled;
	private int sorting;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public int getPkg_id() {
		return pkg_id;
	}
	public void setPkg_id(int pkg_id) {
		this.pkg_id = pkg_id;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getSorting() {
		return sorting;
	}
	public void setSorting(int sorting) {
		this.sorting = sorting;
	}
	public int getSupplier_code_id() {
		return supplier_code_id;
	}
	public void setSupplier_code_id(int supplier_code_id) {
		this.supplier_code_id = supplier_code_id;
	}
}
