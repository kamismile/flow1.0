package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;


//产品信息
public class InfoSku extends BaseDomain {
    private static final long serialVersionUID = 4904390475758184661L;
	
    //id
    private int id;
    //代码类型
    private int type_cid;
    //备注
    private String remark;
    //SKU
    private String sku;
    private String name;
    //运营商
    private String operator;
    //省份
    private int provinceid;
    //标准价
    private double standard_price;
    //使用范围
    private int scope_cid;
    //是否启用
    private boolean enabled;
    //更新时间
    private Timestamp update_time;
    //更新操作人
    private String update_user;
    private String via_attribute;
    
    private int pkgsize;
    private String info_ptype;
    private String info_net;
    private Timestamp info_expiry;
    private Timestamp info_effective;
    private String info_limit;
    private int rent_type;
    private int sku_type;
    private String sku_param;
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getType_cid() {
		return type_cid;
	}
	public void setType_cid(int type_cid) {
		this.type_cid = type_cid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getVia_attribute() {
		return via_attribute;
	}
	public void setVia_attribute(String via_attribute) {
		this.via_attribute = via_attribute;
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
	public Timestamp getInfo_expiry() {
		return info_expiry;
	}
	public void setInfo_expiry(Timestamp info_expiry) {
		this.info_expiry = info_expiry;
	}
	public Timestamp getInfo_effective() {
		return info_effective;
	}
	public void setInfo_effective(Timestamp info_effective) {
		this.info_effective = info_effective;
	}
	public String getInfo_limit() {
		return info_limit;
	}
	public void setInfo_limit(String info_limit) {
		this.info_limit = info_limit;
	}
	public int getPkgsize() {
		return pkgsize;
	}
	public void setPkgsize(int pkgsize) {
		this.pkgsize = pkgsize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRent_type() {
		return rent_type;
	}
	public void setRent_type(int rent_type) {
		this.rent_type = rent_type;
	}
	
	@Override
	public String showname() {
		return sku;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InfoSku [id=").append(id).append(", type_cid=").append(type_cid).append(", remark=")
				.append(remark).append(", sku=").append(sku).append(", name=").append(name).append(", operator=")
				.append(operator).append(", provinceid=").append(provinceid).append(", standard_price=")
				.append(standard_price).append(", scope_cid=").append(scope_cid).append(", enabled=").append(enabled)
				.append(", update_time=").append(update_time).append(", update_user=").append(update_user)
				.append(", via_attribute=").append(via_attribute)
				.append(", pkgsize=").append(pkgsize).append(", info_ptype=").append(info_ptype).append(", info_net=")
				.append(info_net).append(", info_expiry=").append(info_expiry).append(", info_effective=")
				.append(info_effective).append(", info_limit=").append(info_limit).append(", rent_type=")
				.append(rent_type).append("]");
		return builder.toString();
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
