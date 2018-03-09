package com.shziyuan.flow.queue.base.interactive;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.wraped.PassiveSupplierReportMQWrap;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.queue.base.supplier.AttrCacheManager;
import com.shziyuan.flow.redis.base.service.RedisBindSupplierService;

/**
 * 供应商接口管理器
 * @author james.hu
 *
 */
@Service
@ConditionalOnBean(value = RedisBindSupplierService.class)
public class SupplierInterfaceManager implements ApplicationContextAware{
	@Autowired
	private AttrCacheManager attrCacheManager;			// 供应商参数缓存
	
	@Autowired
	private RedisBindSupplierService redisBindSupplierService;
	
	private ApplicationContext applicationContext;		// spring上下文
	
	/**
	 * 根据名称加载实际处理接口
	 * 处理接口由groovy加载
	 * @param beanName
	 * @param supplier_id
	 * @return
	 */
	public BaseInterfaceRequestResponse submit(QueueOrderMQWrap wrap) {
		InfoSupplier sup = wrap.getConfiguredBindBean().getBindSupplier().getCurrentCode().getSupplier();
		// 获取处理接口
		ISupplierInterface supplierInterface = applicationContext.getBean(sup.getPclass(),ISupplierInterface.class);
		// 读取对应供应商参数
		Map<String,Object> attrMap = attrCacheManager.getAttr(sup.getId());
		BaseInterfaceRequestResponse resp = supplierInterface.submit(wrap,attrMap);
		LoggerUtil.request.info("[{}]url:{} | data:{} | resp:{}",wrap.getOrder().getOrder_no(),resp.getSubmitRaw(),resp.getResultRaw());
		return resp;
	}
	
	/**
	 * 根据名称加载实际处理接口
	 * 处理接口由groovy加载
	 * @param beanName
	 * @param supplier_id
	 * @return
	 */
	public BaseInterfaceRequestResponse report(QueueOrderMQWrap wrap) {
		InfoSupplier sup = wrap.getConfiguredBindBean().getBindSupplier().getCurrentCode().getSupplier();
		// 获取处理接口
		ISupplierInterface supplierInterface = applicationContext.getBean(sup.getPclass(),ISupplierInterface.class);
		// 读取对应供应商参数
		Map<String,Object> attrMap = attrCacheManager.getAttr(sup.getId());
		BaseInterfaceRequestResponse resp = supplierInterface.report(wrap,attrMap);
		LoggerUtil.request.info("[{}]url:{} | data:{} | resp:{}",wrap.getOrder().getOrder_no(),resp.getSubmitRaw(),resp.getResultRaw());
		return resp;
	}
	
	/**
	 * 根据名称加载实际处理接口
	 * 处理接口由groovy加载
	 * @param beanName
	 * @param supplier_id
	 * @return
	 */
	public BaseInterfaceRequestResponse passiveReport(QueueOrderMQWrap wrap,String reportSource) {
		InfoSupplier sup = wrap.getConfiguredBindBean().getBindSupplier().getCurrentCode().getSupplier();
		// 获取处理接口
		ISupplierInterface supplierInterface = applicationContext.getBean(sup.getPclass(),ISupplierInterface.class);
		// 读取对应供应商参数
		Map<String,Object> attrMap = attrCacheManager.getAttr(sup.getId());
		return supplierInterface.passiveReportResult(wrap,reportSource,attrMap);
	}
	
	public String passiveReportKey(PassiveSupplierReportMQWrap wrap) {
		InfoSupplier sup = redisBindSupplierService.getSupplier(wrap.getSupplierId());
		// 获取处理接口
		ISupplierInterface supplierInterface = applicationContext.getBean(sup.getPclass(),ISupplierInterface.class);
		return supplierInterface.parsePassiveReportKey(wrap.getReportSource());
	}
			
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
