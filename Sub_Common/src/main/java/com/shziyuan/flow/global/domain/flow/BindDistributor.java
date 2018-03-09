package com.shziyuan.flow.global.domain.flow;

//渠道绑定信息
public class BindDistributor extends BaseDomain{
   
	private static final long serialVersionUID = 172607302159926416L;
	
	//id
	private int id;
    //产品ID
	private int pid;
    //包裹ID(与skuid互斥,只应有一个)
    private int pkg_id;
    //渠道ID
    private int distributor_id;
    //折扣
    private double discount;
    // 出货价
    private double price;
    //是否启用
    private boolean enabled;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getPkg_id() {
		return pkg_id;
	}
	public void setPkg_id(int pkg_id) {
		this.pkg_id = pkg_id;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
   
    
}
