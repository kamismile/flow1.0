package com.ziyuan.web.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JChartLineDatasets;
import com.shziyuan.flow.global.jeasyui.JChartPieDatasets;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.jeasyui.JChartData;

import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant;
import com.ziyuan.web.manager.domain.ChartLineQueueOrderBean;
import com.ziyuan.web.manager.domain.CountTodayBean;
import com.ziyuan.web.manager.wrap.LogQueueOrderSubmitWrap;
import com.ziyuan.web.manager.wrap.LogQueueSupplierReportWrap;
import com.ziyuan.web.manager.wrap.OrderWrap;



@Service
public class IndexService {
	@Resource
	private LogQueueOrderSubmitWrap logQueueOrderSubmitWrap;
	
	@Resource
	private LogQueueSupplierReportWrap logQueueSupplierReportWrap;
	
	@Resource
	private OrderWrap orderWrap;
	
	public JEasyuiData selectLogQueueOrderSubmit(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(logQueueOrderSubmitWrap.select(bean.getParam()));
	}
	
	public JEasyuiData selectLogQueueSupplierReport(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(logQueueSupplierReportWrap.select(bean.getParam()));
	}
	
	public JChartData selectOrderChartLine(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		List<ChartLineQueueOrderBean> todays = orderWrap.selectIndexChartLineToday(bean.getParam());
		List<ChartLineQueueOrderBean> yesterdays = orderWrap.selectIndexChartLineYesterday(bean.getParam());
		
		int maxHour = todays.stream().max((b1,b2) -> b1.getHour() > b2.getHour() ? 1 : -1)
				.get().getHour() + 1;
		List<ChartLineQueueOrderBean> fullTodays = Stream.iterate(0, hour -> hour + 1).limit(maxHour)
			.map(hour -> new ChartLineQueueOrderBean(hour,0))
			.collect(Collectors.toList());
		List<ChartLineQueueOrderBean> fullYesterdays = Stream.iterate(0, hour -> hour + 1).limit(maxHour)
				.map(hour -> new ChartLineQueueOrderBean(hour,0))
				.collect(Collectors.toList());
		for(ChartLineQueueOrderBean item : todays) {
			fullTodays.get(item.getHour()).setCounts(item.getCounts());
		}
		for(ChartLineQueueOrderBean item : yesterdays) {
			if(item.getHour() < maxHour)
				fullYesterdays.get(item.getHour()).setCounts(item.getCounts());
		}
		
		JChartData result = new JChartData();
		result.setLabels(fullTodays.stream()
				.map(item -> Integer.toString(item.getHour())).collect(Collectors.toList()));
		JChartLineDatasets datasetsToday = new JChartLineDatasets();
		datasetsToday.setLabel("当日订单量");
		datasetsToday.setBackgroundColor("red");
		datasetsToday.setBorderColor("red");
		datasetsToday.setFill(false);
		datasetsToday.setData(fullTodays.stream()
				.map(item -> item.getCounts()).collect(Collectors.toList()));
		JChartLineDatasets datasetsYesterday = new JChartLineDatasets();
		datasetsYesterday.setLabel("昨日订单量");
		datasetsYesterday.setBackgroundColor("blue");
		datasetsYesterday.setBorderColor("blue");
		datasetsYesterday.setFill(false);
		datasetsYesterday.setData(fullYesterdays.stream()
				.map(item -> item.getCounts()).collect(Collectors.toList()));
		
		result.setDatasets(Stream.of(datasetsToday,datasetsYesterday).collect(Collectors.toList()));
		
		return result;
	}
	
	private static final String[] DEFAULT_PIE_COLOR = {"#cce173","#fae575","#8acbe0","#db7bfd","#f5957e"};
	private static final Map<Integer,String> PROCESS_MAP;
	static {
		PROCESS_MAP = new HashMap<>();
		PROCESS_MAP.put(Constant.ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_SUCCESS.val, "成功");
		PROCESS_MAP.put(Constant.ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_FAILD.val, "失败");
		PROCESS_MAP.put(Constant.ORDER_STATE.ACTIVEREPORT_SUPPLIER_RETURN_HOLD.val, "处理中");
	}
	public JChartData selectIndexChartPieGroupByStatusPush(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		List<Map<String,Object>> list = orderWrap.selectIndexChartPieGroupByStatusPush(bean.getParam());
		JChartData result = new JChartData();
		result.setLabels(list.stream()
				.map(item -> PROCESS_MAP.get((Integer)item.get("supplier_report_success"))).collect(Collectors.toList()));

		JChartPieDatasets datasets = new JChartPieDatasets();
		datasets.setLabel("订单量统计");
		datasets.setBackgroundColor(Stream.iterate(0, i->i+1).limit(list.size())
				.map(i -> DEFAULT_PIE_COLOR[i % 5]).collect(Collectors.toList()));
		datasets.setData(list.stream().map(map -> (Long)map.get("counts")).collect(Collectors.toList()));
		result.setDatasets(Stream.of(datasets).collect(Collectors.toList()));
		
		return result;
	}

	public CountTodayBean selectCountToday(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		return orderWrap.selectCountToday(bean.getParam());
	}
}
