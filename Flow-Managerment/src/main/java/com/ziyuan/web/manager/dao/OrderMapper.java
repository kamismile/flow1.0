package com.ziyuan.web.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ziyuan.web.manager.domain.ChartLineQueueOrderBean;
import com.ziyuan.web.manager.domain.CountTodayBean;
import com.ziyuan.web.manager.domain.OrderBean;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.OrderStatisticBean;

public interface OrderMapper extends ICRUDMapper<OrderReportBean>{
	
	@Override
	public List<OrderReportBean> select(@Param("param") Map<String,String> param);
	
	public List<OrderReportBean> export(@Param("param") Map<String,String> param);
	
	public OrderReportBean selectByOrderNo(String order_no);

	List<OrderReportBean> listDistributorStaticsReport(Map<String,String> param);
	
	List<OrderReportBean> listSupplierStaticsReport(Map<String,String> param);
	
	List<OrderReportBean> listAccountStatics(Map<String,String> param);
	
	public List<ChartLineQueueOrderBean> selectIndexChartLineToday(Map<String,String> param);
	
	public List<ChartLineQueueOrderBean> selectIndexChartLineYesterday(Map<String,String> param);
	
	public List<Map<String,Object>> selectIndexChartPieGroupByStatusPush(Map<String,String> param);

	public int totalCount(@Param("param") Map<String, String> param);
	
	public List<OrderReportBean> selectByPage(@Param("param") Map<String, String> map);
	
	public List<Map<String,Object>> selectOrderDistributorPrice(@Param("starttime") String starttime,@Param("endtime") String endtime);
	
	OrderStatisticBean statisticByTime(@Param("param") Map<String,String> param);
	
	public CountTodayBean selectCountToday(Map<String, String> param);

	public List<OrderBean> listDistributorStaticsReportDaily(Map<String, String> param);

	public List<OrderBean> listDistributorStaticsReportMonthly(Map<String, String> param);

	public List<OrderBean> listSupplierStaticsReportDaily(Map<String, String> param);

	public List<OrderBean> listSupplierStaticsReportMonthly(Map<String, String> param);

}
