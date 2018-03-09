package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.domain.flow.LogWebDistributorDiscountChange;

import org.springframework.stereotype.Service;

import com.ziyuan.web.manager.dao.LogWebDistributorDiscountChangeMapper;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.service.fmt.Formatter;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.LogWebDistributorDiscountChangeWrap;


@Service
public class LogWebDistributorDiscountChangeService extends AbstractCRUDService<LogWebDistributorDiscountChange>{

	@Resource
	private LogWebDistributorDiscountChangeWrap logWebDistributorDiscountChangeWrap;
	@Resource
	private Formatter formatter;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		//DynamicDataSourceHolder.useSlave();
		List<LogWebDistributorDiscountChange> datalist = logWebDistributorDiscountChangeWrap.export(domain);
		String sheetname = "渠道商折扣信息";
		String[] header = {"创建时间","更新人","绑定表ID","渠道商ID","渠道商名称","产品ID","产品代码","产品标准价","调整前折扣","调整前价格","调整后折扣","调整后价格","生效时间"};
		RowFunction<LogWebDistributorDiscountChange> rowAction = (dis,row) -> {
			row.add(dis.getInsert_time());
			row.add(dis.getUpdate_user());
			row.add(dis.getBind_id());
			row.add(dis.getDistributor_id());
			row.add(dis.getDistributor_name());
			row.add(dis.getSku_id());
			row.add(dis.getSku_sku());
			row.add(dis.getSku_standard_price());
			row.add(dis.getBefore_discount());
			row.add(dis.getBefore_price());
			row.add(dis.getAfter_discount());
			row.add(dis.getAfter_price());
			row.add(dis.getEffective().toString());
			return 0;
		};
		
		NormallyExcelExporter<LogWebDistributorDiscountChange> exporter = new NormallyExcelExporter<>(sheetname, header, rowAction);
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
	public ICRUDWrap<LogWebDistributorDiscountChange> getWrap() {
		return logWebDistributorDiscountChangeWrap;
	}

	public JEasyuiData batchInsert(List<LogWebDistributorDiscountChange> domains) {
		
		logWebDistributorDiscountChangeWrap.batchInsert(
				LogWebDistributorDiscountChangeMapper.class.getName(),domains);
		return new JEasyuiData(true, "");
	}
}
