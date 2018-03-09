package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.LogWebDistributorDiscountChange;
import com.shziyuan.flow.global.domain.flow.LogWebSupplierCodetableDiscountChange;
import com.ziyuan.web.manager.dao.LogWebSupplierCodetableDiscountChangeMapper;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.LogWebSupplierCodetableDiscountChangeWrap;



@Service
public class LogWebSupplierCodetableDiscountChangeService extends AbstractCRUDService<LogWebSupplierCodetableDiscountChange>{

	@Resource
	private LogWebSupplierCodetableDiscountChangeWrap logWebSupplierCodetableDiscountChangeWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		//DynamicDataSourceHolder.useSlave();
		List<LogWebSupplierCodetableDiscountChange> datalist = logWebSupplierCodetableDiscountChangeWrap.export(domain);
		String sheetname = "渠道商折扣信息";
		String[] header = {"创建时间","更新人","供应商产品ID","供应商产品名称","供应商产品代码","调整前价格","调整前折扣","调整后价格","调整后折扣","生效时间"};
		RowFunction<LogWebSupplierCodetableDiscountChange> rowAction = (supp,row) -> {
			row.add(supp.getInsert_time());
			row.add(supp.getUpdate_user());
			row.add(supp.getSupplier_code_id());
			row.add(supp.getSupplier_code_name());
			row.add(supp.getSupplier_code_code());
			row.add(supp.getBefore_price());
			row.add(supp.getBefore_discount());
			row.add(supp.getAfter_price());
			row.add(supp.getAfter_discount());
			row.add(supp.getEffective());
			return 0;
		};
		
		NormallyExcelExporter<LogWebSupplierCodetableDiscountChange> exporter = new NormallyExcelExporter<>(sheetname, header, rowAction);
		try {
			return exporter.export(datalist);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	@Override
	protected void sendMQ() {
	}
	@Override
	protected String getMQCahceName() {
		return null;
	}

	@Override
	public ICRUDWrap<LogWebSupplierCodetableDiscountChange> getWrap() {
		return logWebSupplierCodetableDiscountChangeWrap;
	}

	public JEasyuiData batchInsert(List<LogWebSupplierCodetableDiscountChange> domains) {
		
		logWebSupplierCodetableDiscountChangeWrap.batchInsert(
				LogWebSupplierCodetableDiscountChangeMapper.class.getName(),domains);
		return new JEasyuiData(true, "");
	}
}
