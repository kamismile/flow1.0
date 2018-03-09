package com.ziyuan.web.manager.service.impl;

import java.util.List;
import java.util.function.Function;

import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.mq.stream.producer.StreamConfigOutputService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ziyuan.web.manager.service.ICRUDService;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.wrap.ICRUDWrap;

public abstract class AbstractCRUDService<DOMAIN> implements ICRUDService<DOMAIN>,InitializingBean{

	protected final Logger logger = LoggerFactory.getLogger(ICRUDService.class);
	
	private ICRUDWrap<DOMAIN> wrap;
	
	@Autowired
	private StreamConfigOutputService streamConfigOutputService;

	/**
	 * 实现类通过该方法存入MQ名称
	 * @return
	 */
	protected abstract String getMQCahceName();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setWrap(getWrap());
	}
	
	@Override
	public JEasyuiData select(JEasyuiRequestBean bean) {
		
		if(bean.getPage() > 0)
			return wrap.select(bean);
		else
			return new JEasyuiData(wrap.selectAll(bean));
	}
	
	protected void sendMQ() {
		
	}
	
	protected void sendDeleteMQ(int id) {
		try {
			getMQCahceName().equals("");
		} catch (Exception e) {
			return;
		}
		ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
		//供应商信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_SUPPLIER)){
			configRefreshDomain.setId(id);
			streamConfigOutputService.changeSupplier(configRefreshDomain);
		}
		//供应商产品信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_SUPPLIER_CODE)){
			configRefreshDomain.setId(id);
			streamConfigOutputService.changeSuppliercode(configRefreshDomain);
		}
		//平台产品信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_SKU)){
			configRefreshDomain.setId(id);
			streamConfigOutputService.changeSku(configRefreshDomain);
		}
		//渠道商信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_DISTRIBUTOR)){
			configRefreshDomain.setId(id);
			streamConfigOutputService.changeDistributor(configRefreshDomain);
		}

	}
	
	/**
	 * 根据不同的MQ名称发送MQ消息
	 */
	protected void sendUpdateMQ(DOMAIN domain) {
		try {
			getMQCahceName().equals("");
		} catch (Exception e) {
			return;
		}
		
		ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
		
		//供应商信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_SUPPLIER)){
			configRefreshDomain.setId(((InfoSupplier) domain).getId());
			streamConfigOutputService.changeSupplier(configRefreshDomain);
		}
		//供应商产品信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_SUPPLIER_CODE)){
			configRefreshDomain.setId(((InfoSupplierCodetable)domain).getId());
			streamConfigOutputService.changeSuppliercode(configRefreshDomain);
		}
		//平台产品信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_SKU)){
			configRefreshDomain.setId(((InfoSku) domain).getId());
			streamConfigOutputService.changeSku(configRefreshDomain);
		}
		//渠道商信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_DISTRIBUTOR)){
			configRefreshDomain.setId(((InfoDistributor) domain).getId());
			streamConfigOutputService.changeDistributor(configRefreshDomain);
		}
		//绑定供应商信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_BIND_SUPPLIER)){
			configRefreshDomain.setId(((BindSupplier) domain).getId());
			streamConfigOutputService.changeBindSupplier(configRefreshDomain);
		}
		//绑定渠道商信息变更
		if(getMQCahceName().equals(FlowMqDefine.CHANGE_BIND_DISTRIBUTOR)){
			configRefreshDomain.setId(((BindDistributor) domain).getId());
			streamConfigOutputService.changeBindDistributor(configRefreshDomain);
		}
	}
	
	@Override
	public JEasyuiData selectOne(int id) {
		try {
			DOMAIN result = wrap.selectOne(id);
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

	@Override
	public JEasyuiData update(DOMAIN domain) {
		
		try {
			DOMAIN result = wrap.update(domain);
			sendUpdateMQ(domain);
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

	@Override
	public JEasyuiData insert(DOMAIN domain) {
		
		try {
			DOMAIN result = wrap.insert(domain);
			sendUpdateMQ(domain);
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

	@Override
	public JEasyuiData delete(int id) {
		
		try {
			wrap.delete(id);
			
			sendDeleteMQ(id);
			
			return new JEasyuiData(true,"");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

	public abstract ICRUDWrap<DOMAIN> getWrap();

	public void setWrap(ICRUDWrap<DOMAIN> wrap) {
		this.wrap = wrap;
	}

	
	
	//没有被调用
	@Override
	public JEasyuiData batchInsert(String mapperName,List<DOMAIN> datas) {
		
		try {
			wrap.batchInsert(mapperName,datas);
			sendMQ();
			return new JEasyuiData(true,"");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	//没有被调用
	@Override
	public JEasyuiData batchUpdate(String mapperName,List<DOMAIN> datas) {
		
		try {
			wrap.batchUpdate(mapperName,datas);
			sendMQ();
			return new JEasyuiData(true,"");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	//没有被调用
	@Override
	public JEasyuiData batchDelete(String mapperName,List<DOMAIN> datas,Function<DOMAIN,Integer> idSupplier) {
		
		try {
			wrap.batchDelete(mapperName,datas,idSupplier);
			sendMQ();
			return new JEasyuiData(true,"");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
}
