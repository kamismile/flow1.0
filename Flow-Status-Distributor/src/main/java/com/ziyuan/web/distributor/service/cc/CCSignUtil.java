package com.ziyuan.web.distributor.service.cc;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.ziyuan.web.distributor.domain.DistributorStatus;

@Component
public class CCSignUtil {

    private Decrypt signUtil = new Decrypt();
    
    /* 
     *  签名计算
     */
    public String sign(DistributorStatus status, String key) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("apiKey").append(status.getClientCode())
    		.append("timeStamp").append(status.getTimestamp())
    		.append(key);

    	return signUtil.shaEncrypt(sb.toString());
    	
    }
    
        
    public String sign(String distributor_code,String order_no,String timeStamp,String key) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("apiKey").append(distributor_code)
    		.append("order_no").append(order_no)
    		.append("timeStamp").append(timeStamp)
    		.append(key);

    	return signUtil.shaEncrypt(sb.toString());
    }
    
    public String signGetBanlance(String distributor_code,String key) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("apiKey").append(distributor_code).append(key);
    	return signUtil.shaEncrypt(sb.toString());
    }
    
    public String stringNeedSign(DistributorOrder order, String key) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("apiKey").append(order.getClientCode())
    		.append("cstmOrderNo").append(order.getClientOrderNo())
    		.append("notifyUrl").append(order.getNotifyUrl())
    		.append("phone").append(order.getPhone())
    		.append("productCode").append(order.getProductCode())
    		.append("timeStamp").append(order.getTimestamp())
    		.append(key);
    	return sb.toString();
    }
    
    public String sign(String str) {
    	return signUtil.shaEncrypt(str);
    }
    

    private class Decrypt {
    	public final String KEY_SHA = "SHA";
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f'};

        /**
         * SHA加密
         */
        public String shaEncrypt(String inputStr) {
            byte[] inputData = inputStr.getBytes();
            String returnString = "";
            try {
                inputData = encryptSHA(inputData);
                for (int i = 0; i < inputData.length; i++) {
                    returnString += byteToHexString(inputData[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnString;
        }


        private String byteToHexString(byte ib) {
            char[] ob = new char[2];
            ob[0] = Digit[(ib >>> 4) & 0X0F];
            ob[1] = Digit[ib & 0X0F];

            String s = new String(ob);

            return s;
        }
        /**
         * SHA加密字节
         */
        public byte[] encryptSHA(byte[] data) throws Exception {
            MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
            sha.update(data);
            return sha.digest();
        }
    }
}
