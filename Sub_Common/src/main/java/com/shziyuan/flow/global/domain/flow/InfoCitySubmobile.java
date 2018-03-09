package com.shziyuan.flow.global.domain.flow;

//省份城市与手机号对应信息
public class InfoCitySubmobile extends BaseDomain{
	private static final long serialVersionUID = -2735583899888989369L;

	private int id;
    
    //号段
    private String submobile;
    //省份
    private  int provinceid;
    private  String province;
    //地市
    private int cityid;
    private String city;
    //运营商
    private String operator;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubmobile() {
		return submobile;
	}
	public void setSubmobile(String submobile) {
		this.submobile = submobile;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
    
}
