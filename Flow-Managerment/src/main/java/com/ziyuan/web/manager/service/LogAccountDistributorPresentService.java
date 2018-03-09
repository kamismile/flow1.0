package com.ziyuan.web.manager.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributorPresent;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.service.impl.AbstractCRUDService;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.LogAccountDistributorPresentWrap;



@Service
public class LogAccountDistributorPresentService extends AbstractCRUDService<LogAccountDistributorPresent>{

	@Resource
	private LogAccountDistributorPresentWrap logAccountDistributorPresentWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		//DynamicDataSourceHolder.useSlave();
		
		List<LogAccountDistributorPresent> datalist = logAccountDistributorPresentWrap.export(domain);
		
		String sheetname = "月末赠送管理";
		String[] header = {"插入时间","渠道ID","渠道名称","插入时间-年份","插入时间-月份","上月消耗","赠送额度","更新人"};
		RowFunction<LogAccountDistributorPresent> rowAction = (log,row) -> {
			row.add(log.getInsert_time());
			row.add(log.getDistributor_id());
			row.add(log.getDistributor_name());
			row.add(log.getYear());
			row.add(log.getMonth());
			row.add(log.getConsumer());
			row.add(log.getPresent());
			row.add(log.getUpdate_user());
			return 0;
		};
		
		NormallyExcelExporter<LogAccountDistributorPresent> exporter = new NormallyExcelExporter<>(sheetname, header, rowAction);
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
	public ICRUDWrap<LogAccountDistributorPresent> getWrap() {
		return logAccountDistributorPresentWrap;
	}

}
