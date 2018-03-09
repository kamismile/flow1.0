package com.shziyuan.flow.global.domain.flow;

public class InfoSupplierCodetable extends BaseDomain{

	private static final long serialVersionUID = -4527865842954743925L;
	
	private int id;
	private int supplier_id;
	private int provinceid;
	private String operator;
	private int scope_cid;
	private String via_attribute;
	private String code;
	private String name;
	private double discount;
	private double price;
	private String if_attribute;
	private boolean enabled;
	
	private int pkgsize;
	private String info_ptype;
	private String info_net;
	private int rent_type;
	private boolean cache_mode= false;
	private int code_type;
	private String code_param;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getScope_cid() {
		return scope_cid;
	}
	public void setScope_cid(int scope_cid) {
		this.scope_cid = scope_cid;
	}
	public String getVia_attribute() {
		return via_attribute;
	}
	public void setVia_attribute(String via_attribute) {
		this.via_attribute = via_attribute;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIf_attribute() {
		return if_attribute;
	}
	public void setIf_attribute(String if_attribute) {
		this.if_attribute = if_attribute;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getPkgsize() {
		return pkgsize;
	}
	public void setPkgsize(int pkgsize) {
		this.pkgsize = pkgsize;
	}
	public String getInfo_ptype() {
		return info_ptype;
	}
	public void setInfo_ptype(String info_ptype) {
		this.info_ptype = info_ptype;
	}
	public String getInfo_net() {
		return info_net;
	}
	public void setInfo_net(String info_net) {
		this.info_net = info_net;
	}
	public int getRent_type() {
		return rent_type;
	}
	public void setRent_type(int rent_type) {
		this.rent_type = rent_type;
	}
	public boolean isCache_mode() {
		return cache_mode;
	}
	public void setCache_mode(boolean cache_mode) {
		this.cache_mode = cache_mode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InfoSupplierCodetable [id=").append(id).append(", supplier_id=").append(supplier_id)
				.append(", provinceid=").append(provinceid).append(", operator=").append(operator)
				.append(", scope_cid=").append(scope_cid).append(", via_attribute=").append(via_attribute)
				.append(", code=").append(code).append(", name=").append(name).append(", discount=").append(discount)
				.append(", price=").append(price).append(", if_attribute=").append(if_attribute).append(", enabled=")
				.append(enabled).append(", pkgsize=").append(pkgsize).append(", info_ptype=").append(info_ptype)
				.append(", info_net=").append(info_net).append(", rent_type=").append(rent_type).append(", cache_mode=")
				.append(cache_mode).append("]");
		return builder.toString();
	}
	public String getCode_param() {
		return code_param;
	}
	public void setCode_param(String code_param) {
		this.code_param = code_param;
	}
	public int getCode_type() {
		return code_type;
	}
	public void setCode_type(int code_type) {
		this.code_type = code_type;
	}

}
