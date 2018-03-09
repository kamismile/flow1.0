package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;

public class InfoSkuBean extends InfoSku {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9094725518026484649L;
	private String distributor_name;
	private double price;
	private double discount;
	private String key;
	//关联的解释字段
	private String province;
	private String scope_name;
	private String sku_type_name;
	
	private List<InfoSupplier> relate_common_infoSupplier;
	private List<InfoSupplier> relate_present_infoSupplier;
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getDistributor_name() {
		return distributor_name;
	}
	public void setDistributor_name(String distributor_name) {
		this.distributor_name = distributor_name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getScope_name() {
		return scope_name;
	}
	public void setScope_name(String scope_name) {
		this.scope_name = scope_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public List<InfoSupplier> getRelate_common_infoSupplier() {
		return relate_common_infoSupplier;
	}
	public void setRelate_common_infoSupplier(List<InfoSupplier> relate_common_infoSupplier) {
		this.relate_common_infoSupplier = relate_common_infoSupplier;
	}
	public List<InfoSupplier> getRelate_present_infoSupplier() {
		return relate_present_infoSupplier;
	}
	public void setRelate_present_infoSupplier(List<InfoSupplier> relate_present_infoSupplier) {
		this.relate_present_infoSupplier = relate_present_infoSupplier;
	}
	public String getSku_type_name() {
		return sku_type_name;
	}
	public void setSku_type_name(String sku_type_name) {
		this.sku_type_name = sku_type_name;
	}
     
}
