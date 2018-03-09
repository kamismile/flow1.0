package com.shziyuan.flow.queue.base.interactive;

import java.util.Map;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;

/**
 * 供应商接口
 * @author james.hu
 *
 */
public interface ISupplierInterface {
	/**
	 * 主动提交供应商订单请求
	 * @param orderPackage
	 * @return
	 */
	public BaseInterfaceRequestResponse submit(QueueOrderMQWrap wrap,Map<String,Object> attrMap);
	
	/**
	 * 主动查询供应商状态报告
	 * @param orderPackage
	 * @return
	 */
	public BaseInterfaceRequestResponse report(QueueOrderMQWrap wrap,Map<String,Object> attrMap);
	
	/**
	 * 供应商推送状态报告
	 * @return
	 */
	public BaseInterfaceRequestResponse passiveReportResult(QueueOrderMQWrap wrap,String reportSource,Map<String,Object> attrMap);
	
	/**
	 * 根据供应商推送状态报告 获取缓存key
	 * @param reportSource
	 * @return
	 */
	public String parsePassiveReportKey(String reportSource);
}
