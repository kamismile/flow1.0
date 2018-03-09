package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.domain.flow.InfoSku;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.common.CacheDefine;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.domain.InfoSkuBean;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.service.fmt.Formatter;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoSkuWrap;

@Service
public class InfoSkuService extends AbstractCRUDService<InfoSku> {
	
	@Resource
	private InfoSkuWrap infoSkuWrap;
	
	@Resource
	private Formatter formatter;
	
	@Override
	public ICRUDWrap<InfoSku> getWrap() {
		return infoSkuWrap;
	}
	
	@Override
	protected String getMQCahceName() {
		return FlowMqDefine.CHANGE_SKU;
	}
	
	public int getCountBySKU(String sku) {
		
		return infoSkuWrap.getCountBySKU(sku);
	}
	@Override
	public JEasyuiData insert(InfoSku domain) {
		
		domain.setUpdate_time(TimestampUtil.now());
		int count = getCountBySKU(domain.getSku());
		if (count > 0) {
			return new JEasyuiData(false,"已存在该SKU");
		}
		JEasyuiData result = super.insert(domain);
//		activeMQTextSender.updateCacheName(CacheDefine.BIND_SUPPLIER);
		return result;
	}
	
	@Override
	public JEasyuiData update(InfoSku domain) {
		JEasyuiData result = super.update(domain);
//		activeMQTextSender.updateCacheName(CacheDefine.BIND_SUPPLIER);
		return result;
	}
	
	@Override
	public JEasyuiData delete(int id) {
		JEasyuiData result = super.delete(id);
//		activeMQTextSender.updateCacheName(CacheDefine.BIND_SUPPLIER);
		return result;
	}

	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean bean) {
		
		List<InfoSku> datalist = infoSkuWrap.export(bean);
		
		String sheetname = "产品信息";
		String[] header = {"名称","运营商","省份","价格","备注","范围","更新时间","更新人"};
		RowFunction<InfoSku> rowAction = (sku,row) -> {
			row.add(sku.getName());
			row.add(sku.getOperator());
			row.add(formatter.getProvince(sku.getProvinceid()));
			row.add(sku.getStandard_price());
			row.add(sku.getRemark());
			row.add(formatter.getScope(sku.getScope_cid()));
			row.add(sku.getUpdate_time());
			row.add(sku.getUpdate_user());
			return 0;
		};
		
		NormallyExcelExporter<InfoSku> exporter = new NormallyExcelExporter<>(sheetname, header, rowAction);
		try {
			return exporter.export(datalist);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	public JEasyuiData selectTreeInSku() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(infoSkuWrap.selectTreeInSku());
	}
	public JEasyuiData selectTreeInSku2() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(infoSkuWrap.selectTreeInSku2());
	}
	
	public JEasyuiData selectAllSku(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<InfoSkuBean> list = infoSkuWrap.selectAllSku(bean);
		PageInfo<InfoSkuBean> pageInfo = new PageInfo<InfoSkuBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}

	public JEasyuiData updateSkuParam(InfoSku sku) {
		// TODO Auto-generated method stub
		try {
			infoSkuWrap.updateSkuParam(sku);
			return new JEasyuiData(true);
		} catch (Exception e) {
			return new JEasyuiData(false, e.getMessage());
		}
	} 
}
