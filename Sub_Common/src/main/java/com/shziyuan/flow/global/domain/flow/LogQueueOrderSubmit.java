package com.shziyuan.flow.global.domain.flow;
//日志——下游提交

import java.sql.Timestamp;

public class LogQueueOrderSubmit extends BaseDomain{

	private static final long serialVersionUID = -4944240780621605708L;

	//插入时间
    private Timestamp insert_time;
    //客户自定义订单号
    private String clientOrderNo;
    //产品代码
    private String productCode;
    //渠道标识
    private String clientCode;
    //手机号
    private String phone;
    //签名
    private String sign;
    //远程IP
    private String remote_ip;
    // 省份
    private int provinceid;
    // 响应状态码
    private String resp_status;
    private String resp_message;
    
    private String orderNo;
    
    private String notify_url;
    
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getResp_status() {
		return resp_status;
	}
	public void setResp_status(String resp_status) {
		this.resp_status = resp_status;
	}
	public int getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}
	public Timestamp getInsert_time() {
        return insert_time;
    }
    public void setInsert_time(Timestamp insert_time) {
        this.insert_time = insert_time;
    }
    public String getClientOrderNo() {
        return clientOrderNo;
    }
    public void setClientOrderNo(String clientOrderNo) {
        this.clientOrderNo = clientOrderNo;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getClientCode() {
        return clientCode;
    }
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getRemote_ip() {
        return remote_ip;
    }
    public void setRemote_ip(String remote_ip) {
        this.remote_ip = remote_ip;
    }
	public String getResp_message() {
		return resp_message;
	}
	public void setResp_message(String resp_message) {
		this.resp_message = resp_message;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
    
}
