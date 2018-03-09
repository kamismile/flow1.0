package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.CacheDefine;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.shziyuan.flow.mq.stream.producer.StreamConfigOutputService;
import com.ziyuan.web.manager.domain.InfoSupplierBean;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoSupplierWrap;

@Service
public class InfoSupplierService extends AbstractCRUDService<InfoSupplier> {
	@Resource
	private InfoSupplierWrap infoSupplierWrap;
	
	@Autowired
	private StreamConfigOutputService streamConfigOutputService;
	
	@Override
	public ICRUDWrap<InfoSupplier> getWrap() {
		return infoSupplierWrap;
	}
	
	@Override
	protected String getMQCahceName() {
		return FlowMqDefine.CHANGE_SUPPLIER;
	}
	
	public JEasyuiData selectUseCache(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return infoSupplierWrap.selectUseCache(bean);
	}
	
	public JEasyuiData updateCacheTable(InfoSupplier domain) {
		
		try {
			infoSupplierWrap.updateCacheTable(domain);
			
			ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
			configRefreshDomain.setId(domain.getId());
			streamConfigOutputService.changeSupplier(configRefreshDomain);
			return new JEasyuiData(domain);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	@Override
	public JEasyuiData insert(InfoSupplier domain) {
		domain.setEnabled(true);
		domain.setCreate_time(TimestampUtil.now());
		return super.insert(domain);
	}

	private static final String[] PROCESS_MODE = {"多线程","单线程"};
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		
		List<InfoSupplierBean> dataList = infoSupplierWrap.export(bean.getParam());
		
		String[] header = {"名称","创建时间","更新时间","更新用户","处理模式",
				"日成功率","总消费额","日销量","月销量","是否启用",
				"代码名称","供应商代码","省份","运营商","使用范围","折扣","进价","代码启用"};
		
		RowFunction<InfoSupplierBean> rowAction = (supplier,row) -> {
			row.add(supplier.getName());
			row.add(supplier.getCreate_time());
			row.add(supplier.getUpdate_time());
			row.add(supplier.getUpdate_user());
			row.add(PROCESS_MODE[supplier.getProcess_mode()]);
			row.add(supplier.getStatisticsSupplier().getDaily_rate());
			row.add(supplier.getStatisticsSupplier().getSales_total());
			row.add(supplier.getStatisticsSupplier().getSales_daily());
			row.add(supplier.getStatisticsSupplier().getSales_monthly());
			row.add(supplier.isEnabled());
			
			if(supplier.getCodetable() != null) {
				List<InfoSupplierCodetableBean> codetables = supplier.getCodetable();
				for(InfoSupplierCodetableBean codetable : codetables) {
					row.newRow();
					for(int k = 0; k < 11 ; ++k)
						row.add("");
					row.add(codetable.getName());
					row.add(codetable.getCode());
					row.add(codetable.getProvince());
					row.add(codetable.getOperator());
					row.add(codetable.getScope_name());
					row.add(codetable.getDiscount());
					row.add(codetable.getPrice());
					row.add(codetable.isEnabled());
				}
				return codetables.size();
			}
			
			return 0;
		};
		
		NormallyExcelExporter<InfoSupplierBean> exporter = new NormallyExcelExporter<InfoSupplierBean>("供应商信息",
				header,rowAction);
		try {
			return exporter.export(dataList);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	

	public List<InfoSupplier> selectName() {
		//DynamicDataSourceHolder.useSlave();
		return infoSupplierWrap.selectName();	
	}

	public JEasyuiData updateSupplierInfoAttribute(InfoSupplier infoSupplier) {
		
		try {
			InfoSupplier result = infoSupplierWrap.updateSupplierInfoAttribute(infoSupplier);
//			activeMQTextSender.updateCacheName(CacheDefine.INFO_SUPPLIER);
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

}
