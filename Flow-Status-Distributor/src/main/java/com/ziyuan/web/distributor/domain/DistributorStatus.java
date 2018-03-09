package com.ziyuan.web.distributor.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shziyuan.flow.global.common.Constant.FEE_TYPE;

//接收渠道查询
public class DistributorStatus implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//id
    private int id;
    
    //订单号
    private String order_no;
    
    //参数签名
    private String sign;
   
    //本平台分配的客户标识（渠道代码）
    private String clientCode;
    
    private String timestamp;
    
    /**
     * 附加信息
     */
    
    // 记录远程IP
    @JsonIgnore
    private String remote_ip;

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getRemote_ip() {
		return remote_ip;
	}

	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}
	
    public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public DistributorStatus() {
		// TODO Auto-generated constructor stub
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
    
}
