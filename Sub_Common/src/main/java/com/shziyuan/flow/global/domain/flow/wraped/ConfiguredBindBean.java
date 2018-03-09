package com.shziyuan.flow.global.domain.flow.wraped;

import com.shziyuan.flow.global.domain.flow.InfoCitySubmobile;

public class ConfiguredBindBean {

	private BindDistributorBean bindDistributor;
	private BindSupplierBean bindSupplierNormal;
	private BindSupplierBean bindSupplierPresent;
	private BindSupplierBean bindSupplier;
	private InfoCitySubmobile submobile;
	
	private boolean success;
	private Throwable error;
	
	public ConfiguredBindBean() {
		
	}
	
	public static ConfiguredBindBean success(BindDistributorBean bindDistributor,
			BindSupplierBean bindSupplierNormal, BindSupplierBean bindSupplierPresent,InfoCitySubmobile submobile) {
		ConfiguredBindBean bean = new ConfiguredBindBean();
		bean.setSuccess(true);
		bean.setBindDistributor(bindDistributor);
		bean.setBindSupplierNormal(bindSupplierNormal);
		bean.setBindSupplierPresent(bindSupplierPresent);
		bean.setSubmobile(submobile);
		return bean;
	}
	
	public static ConfiguredBindBean success() {
		ConfiguredBindBean bean = new ConfiguredBindBean();
		bean.setSuccess(true);
		return bean;
	}
	
	public static ConfiguredBindBean faild(Throwable e) {
		ConfiguredBindBean bean = new ConfiguredBindBean();
		bean.setSuccess(false);
		bean.setError(e);
		return bean;
	}
	public BindDistributorBean getBindDistributor() {
		return bindDistributor;
	}
	public void setBindDistributor(BindDistributorBean bindDistributor) {
		this.bindDistributor = bindDistributor;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Throwable getError() {
		return error;
	}
	public void setError(Throwable error) {
		this.error = error;
	}

	public InfoCitySubmobile getSubmobile() {
		return submobile;
	}

	public void setSubmobile(InfoCitySubmobile submobile) {
		this.submobile = submobile;
	}

	public BindSupplierBean getBindSupplierNormal() {
		return bindSupplierNormal;
	}

	public void setBindSupplierNormal(BindSupplierBean bindSupplierNormal) {
		this.bindSupplierNormal = bindSupplierNormal;
	}

	public BindSupplierBean getBindSupplierPresent() {
		return bindSupplierPresent;
	}

	public void setBindSupplierPresent(BindSupplierBean bindSupplierPresent) {
		this.bindSupplierPresent = bindSupplierPresent;
	}

	public BindSupplierBean getBindSupplier() {
		return bindSupplier;
	}

	public void setBindSupplier(BindSupplierBean bindSupplier) {
		this.bindSupplier = bindSupplier;
	}
}
