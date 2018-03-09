package com.ziyuan.web.manager.service.impl.report;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.domain.report.OrderTimesharingBean;
import com.ziyuan.web.manager.domain.report.OrderTimesharingBeanReturn;
import com.ziyuan.web.manager.domain.report.OrderTimesharingResultset;
import com.ziyuan.web.manager.utils.ExcelData;
import com.ziyuan.web.manager.utils.ExcelRow;
import com.ziyuan.web.manager.utils.ExcelUtil;
import com.ziyuan.web.manager.wrap.report.OrderReportWrap;

@Service
public class OrderReportService {
	@Resource
	private OrderReportWrap orderReportWrap;
	
	public OrderTimesharingBean selectOrderTimesharing(JEasyuiRequestBean requestBean) {
		OrderTimesharingBean bean = new OrderTimesharingBean();
		
		List<OrderTimesharingResultset> dbResultset = orderReportWrap.selectOrderTimesharing(requestBean.getParam());
		dbResultset.forEach(resultset -> bean.add(resultset));
		
		return bean;
	}
	
	public List<OrderTimesharingBeanReturn> selectOrderTimesharingTotal(JEasyuiRequestBean requestBean){
		List<OrderTimesharingBeanReturn> dataList = selectOrderTimesharing(requestBean).toList();
		for (OrderTimesharingBeanReturn res : dataList) {
			double tem = 0;
			Collection<Double> coll = res.getPrices().values();
			for (Double price : coll) {
				if(price != null){
					tem += price;
					
				}
			}
			res.getPrices().put("总计", tem);
		}
		return dataList;
	}
	
	public ByteArrayOutputStream export(JEasyuiRequestBean bean) {
		try {
			List<OrderTimesharingBeanReturn> dataList = selectOrderTimesharing(bean).toList();
			ExcelUtil util = new ExcelUtil("渠道商分时统计");
			
			if(dataList.size() == 0)
				return null;
			
			int columnSize = dataList.get(0).getPrices().size() + 2;
			OrderTimesharingBeanReturn firstList = dataList.get(0);
			ExcelRow header = new ExcelRow(columnSize);
			header.add("日期");
			header.add("截止到");
			firstList.getPrices().keySet().forEach(p -> header.add(p));
			util.setHeader(header);
			
			ExcelData rows = new ExcelData(dataList.size());
			dataList.forEach(row -> {
				ExcelRow line = new ExcelRow(columnSize);
				line.add(row.getDay());
				line.add(row.getHour());
				row.getPrices().values().forEach(price -> line.add(price != null ? price.doubleValue() : 0));
				rows.add(line);
			});
			util.setRows(rows);
			
			return util.writeToXLSX();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
