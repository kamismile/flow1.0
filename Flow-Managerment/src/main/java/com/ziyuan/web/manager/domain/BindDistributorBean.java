package com.ziyuan.web.manager.domain;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.OptDistributorDiscountChange;

public class BindDistributorBean extends BindDistributor{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8844169417050570370L;
	//省份
    private int provinceid;
    private String province;
    
    //使用范围
    private int scope_cid;
    private String scope_name;
    
    //产品信息
    private int sku_id;
    private String sku;
    private String name;
    private boolean sku_enabled;
    private int sku_pkgsize;
    private String sku_info_ptype;
    private String sku_info_net;
    private int sku_rent_type;
    private int sku_type;
    private String sku_param;
    //运营商
    private String operator;
    
    //标准价
    private double standard_price;

    private InfoDistributor distributor;
    private InfoSku skus;
    private OptDistributorDiscountChange change;
    
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public double getStandard_price() {
		return standard_price;
	}

	public void setStandard_price(double standard_price) {
		this.standard_price = standard_price;
	}

	public int getScope_cid() {
		return scope_cid;
	}

	public void setScope_cid(int scope_cid) {
		this.scope_cid = scope_cid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	public String getScope_name() {
		return scope_name;
	}
	public void setScope_name(String scope_name) {
		this.scope_name = scope_name;
	}

	public int getSku_id() {
		return sku_id;
	}

	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}

	public InfoDistributor getDistributor() {
		return distributor;
	}

	public void setDistributor(InfoDistributor distributor) {
		this.distributor = distributor;
	}

	public boolean isSku_enabled() {
		return sku_enabled;
	}

	public void setSku_enabled(boolean sku_enabled) {
		this.sku_enabled = sku_enabled;
	}

	public InfoSku getSkus() {
		return skus;
	}

	public void setSkus(InfoSku skus) {
		this.skus = skus;
	}

	public OptDistributorDiscountChange getChange() {
		return change;
	}

	public void setChange(OptDistributorDiscountChange change) {
		this.change = change;
	}

	public int getSku_pkgsize() {
		return sku_pkgsize;
	}

	public void setSku_pkgsize(int sku_pkgsize) {
		this.sku_pkgsize = sku_pkgsize;
	}

	public String getSku_info_ptype() {
		return sku_info_ptype;
	}

	public void setSku_info_ptype(String sku_info_ptype) {
		this.sku_info_ptype = sku_info_ptype;
	}

	public String getSku_info_net() {
		return sku_info_net;
	}

	public void setSku_info_net(String sku_info_net) {
		this.sku_info_net = sku_info_net;
	}

	public int getSku_rent_type() {
		return sku_rent_type;
	}

	public void setSku_rent_type(int sku_rent_type) {
		this.sku_rent_type = sku_rent_type;
	}

	public String getSku_param() {
		return sku_param;
	}

	public void setSku_param(String sku_param) {
		this.sku_param = sku_param;
	}

	public int getSku_type() {
		return sku_type;
	}

	public void setSku_type(int sku_type) {
		this.sku_type = sku_type;
	}
    
    
}
