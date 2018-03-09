package com.shziyuan.flow.global.domain.flow.dwi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shziyuan.flow.global.common.Constant.FEE_TYPE;

//接收渠道订单参数
public class DistributorOrder implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//id
    private int id;
    
    //订单号
    private String clientOrderNo;
    
    //产品代码（sku）
    private String productCode; 
    
    //参数签名
    private String sign;
    
    //手机号
    private String phone;
   
    //本平台分配的客户标识（渠道代码）
    private String clientCode;
    
    /**
     * 附加信息
     */
    
    // 记录远程IP
    @JsonIgnore
    private String remote_ip;
    // 记录省份
    @JsonIgnore
    private int provinceid;
    
    @JsonIgnore
    private FEE_TYPE feeType= FEE_TYPE.PRESENT;

    public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

	public DistributorOrder(Integer id, String clientOrderNo, String productCode, String sign, String phone,
            String clientCode) {
        super();
        this.id = id;
        this.clientOrderNo = clientOrderNo;
        this.productCode = productCode;
        this.sign = sign;
        this.phone = phone;
        this.clientCode = clientCode;
    }
    
    public DistributorOrder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DistributorOrder [").append("clientOrderNo=").append(clientOrderNo)
				.append(", productCode=").append(productCode).append(", sign=").append(sign).append(", phone=")
				.append(phone).append(", clientCode=").append(clientCode).append(", timeStamp=").append(timestamp)
				.append(", remote_ip=").append(remote_ip).append("]");
		return builder.toString();
	}
    
	

	/**
	 * 匹配桔宝接口描述
	 * apiKey,  cstmOrderNo, notifyUrl,phone, productCode, timeStamp
	 * clientCode,clientOrderNo,notifyUrl,phone,productCode,timeStamp
	 */
	private String timestamp;
	private String notifyUrl;
	public void setApiKey(String apiKey) {
		this.clientCode = apiKey;
	}
	public void setTimeStamp(String timeStamp) {
		this.timestamp = timeStamp;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setCstmOrderNo(String cstmOrderNo) {
		this.clientOrderNo = cstmOrderNo;
	}

	public String getNotifyUrl() {
		return notifyUrl == null ? "" : notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	private String serialno;
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public FEE_TYPE getFeeType() {
		return feeType;
	}

	public void setFeeType(FEE_TYPE feeType) {
		this.feeType = feeType;
	}
    
}
