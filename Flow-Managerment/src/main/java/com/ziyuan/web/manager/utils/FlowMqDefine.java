package com.ziyuan.web.manager.utils;

/**
 * 管理发送不同的MQ消息
 * @author user
 *
 */
public class FlowMqDefine {
	//改变供应商信息
	public static final String CHANGE_SUPPLIER = "changeSupplier";
	//改变供应商产品信息
	public static final String CHANGE_SUPPLIER_CODE = "changeSuppliercode";
	//改变渠道商信息
	public static final String CHANGE_DISTRIBUTOR = "changeDistributor";
	//改变平台产品信息
	public static final String CHANGE_SKU = "changeSku";
	//改变平台产品绑定供应商产品信息
	public static final String CHANGE_BIND_SUPPLIER = "changeBindSupplier";
	//改变渠道商绑定平台产品信息
	public static final String CHANGE_BIND_DISTRIBUTOR = "changeBindDistributor";

}
