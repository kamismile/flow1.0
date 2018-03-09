package com.ziyuan.web.manager.wrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.LogWebOrderPush;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.InfoSupplierMapper;
import com.ziyuan.web.manager.dao.LogWebOrderPushMapper;
import com.ziyuan.web.manager.dao.OrderMapper;
import com.ziyuan.web.manager.dao.QueueOrderMapper;
import com.ziyuan.web.manager.dao.QueueReportPushMapper;
import com.ziyuan.web.manager.dao.QueueSupplierReportActiveMapper;
import com.ziyuan.web.manager.dao.QueueSupplierReportPassiveMapper;
import com.ziyuan.web.manager.domain.ChartLineQueueOrderBean;
import com.ziyuan.web.manager.domain.CountTodayBean;
import com.ziyuan.web.manager.domain.OrderBean;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.OrderStatisticBean;
import com.ziyuan.web.manager.domain.QueueReportPush;
import com.ziyuan.web.manager.utils.StringUtil;

@Repository
public class OrderWrap extends AbstractCRUDWrap<OrderReportBean>{

	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private LogWebOrderPushMapper logWebOrderPushMapper;
	
	@Resource
	private QueueReportPushMapper queueReportPushMapper;
	
	@Resource
	private InfoSupplierMapper infoSupplierMapper;
	
	@Resource
	private QueueOrderMapper queueOrderMapper;
	
	@Resource
	private QueueSupplierReportActiveMapper queueSupplierReportActiveMapper;
	@Resource
	private QueueSupplierReportPassiveMapper queueSupplierReportPassiveMapper;
	
	@Override
	public ICRUDMapper<OrderReportBean> getMapper() {
		// TODO Auto-generated method stub
		return orderMapper;
	}
	
	@Override
	@Transactional(readOnly = true)
	public JEasyuiData select(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		Map<String,String> param = bean.getParam();
		param.remove("rows");
		param.remove("_method");
		param.remove("page");
		List<OrderReportBean> list = orderMapper.select(bean.getParam());
		PageInfo<OrderReportBean> pageInfo = new PageInfo<>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public OrderReportBean selectOrder(JEasyuiRequestBean bean) {
		OrderReportBean order = orderMapper.selectByOrderNo(bean.getParam().get("order_no"));
		
		return order;
	}
	
	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderReportBean> listDistributorStaticsReport(JEasyuiRequestBean bean) {
		List<OrderReportBean> list = orderMapper.listDistributorStaticsReport(bean.getParam());
		return list;
	}
	
	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderReportBean> listSupplierStaticsReport(JEasyuiRequestBean bean) {
		List<OrderReportBean> list = orderMapper.listSupplierStaticsReport(bean.getParam());
		return list;
	}
	
	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderReportBean> listAccountStatics(JEasyuiRequestBean bean) {
		List<OrderReportBean> list = orderMapper.listAccountStatics(bean.getParam());
		return list;
	}

	@Transactional(readOnly = true)
	public List<ChartLineQueueOrderBean> selectIndexChartLineToday(Map<String,String> param) {
		return orderMapper.selectIndexChartLineToday(param);
	}
	@Transactional(readOnly = true)
	public List<ChartLineQueueOrderBean> selectIndexChartLineYesterday(Map<String,String> param) {
		return orderMapper.selectIndexChartLineYesterday(param);
	}
	@Transactional(readOnly = true)
	public List<Map<String,Object>> selectIndexChartPieGroupByStatusPush(Map<String,String> param) {
		return orderMapper.selectIndexChartPieGroupByStatusPush(param);
	}
	
	@Transactional(readOnly = true)
	public OrderReportBean selectByOrderNo(String order_no) {
		return orderMapper.selectByOrderNo(order_no);
	}
	
	@Transactional(readOnly = true)
	public List<OrderReportBean> selectByOrderNoBatch(List<String> order_nos) {
		return order_nos.stream().map(order_no -> orderMapper.selectByOrderNo(order_no))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly=false)
	public void submitQueueReport(QueueReportPush push, LogWebOrderPush logPush, OrderReportBean order) {
		// TODO Auto-generated method stub
		InfoSupplier supplier = infoSupplierMapper.selectById(push.getSupplier_id());
		push.setConsumer(supplier.getPlatform_mark());
		//记录日志
		logWebOrderPushMapper.insert(logPush);
		//更新order
		orderMapper.update(order);
		//插入渠道推送
		queueReportPushMapper.insert(push);
		
		//删除存档记录
		queueSupplierReportActiveMapper.deleteByOrderNo(push.getOrder_no());
		queueSupplierReportPassiveMapper.deleteByOrderNo(push.getOrder_no());
	}

	public void submitQueueOrder(QueueOrder queueOrder, LogWebOrderPush push, OrderReportBean order) {
//		// TODO Auto-generated method stub
//		InfoSupplier supplier = infoSupplierMapper.selectById(queueOrder.getSupplier_id());
//		queueOrder.setConsumer(supplier.getPlatform_mark());
//		//记录日志
//		logWebOrderPushMapper.insert(push);
//		//更新order
//		orderMapper.update(order);
//		//删除存档记录
//		queueSupplierReportActiveMapper.deleteByOrderNo(queueOrder.getOrder_no());
//		queueSupplierReportPassiveMapper.deleteByOrderNo(queueOrder.getOrder_no());
//		//插入订单队列
//		queueOrderMapper.insert(queueOrder);
	}
	@Transactional(readOnly=true)
	public int totalCount(JEasyuiRequestBean bean) {
		return orderMapper.totalCount(bean.getParam());
	}
	
	@Transactional(readOnly=true)
	public List<OrderReportBean> selectByPage(JEasyuiRequestBean bean) {
		return orderMapper.selectByPage(bean.getParam());
	}
	
	@Transactional(readOnly = true)
	public List<Map<String,Object>> selectOrderDistributorPrice(String starttime,String endtime) {
		return orderMapper.selectOrderDistributorPrice(starttime, endtime);
	}

	@Transactional(readOnly = true)
	public OrderStatisticBean statisticByTime(JEasyuiRequestBean bean) {
		if(StringUtil.isEmpty(bean.getParam().get("startTime")) && StringUtil.isEmpty(bean.getParam().get("endTime"))){
			OrderStatisticBean orderStatisticBean = new OrderStatisticBean();
			orderStatisticBean.setCount("0");
			orderStatisticBean.setDistributor_price("0");
			orderStatisticBean.setStandard_price("0");
			orderStatisticBean.setSupplier_price("0");
			return orderStatisticBean;
		}
		return orderMapper.statisticByTime(bean.getParam());
	}

	@Transactional(readOnly = true)
	public CountTodayBean selectCountToday(Map<String, String> param) {
		// TODO Auto-generated method stub
		return orderMapper.selectCountToday(param);
	}

	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderBean> listDistributorStaticsReportDaily(JEasyuiRequestBean bean) {
		List<OrderBean> list = orderMapper.listDistributorStaticsReportDaily(bean.getParam());
		return list;
	}

	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderBean> listDistributorStaticsReportMonthly(JEasyuiRequestBean bean) {
		List<OrderBean> list = orderMapper.listDistributorStaticsReportMonthly(bean.getParam());
		return list;
	}

	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderBean> listSupplierStaticsReportDaily(JEasyuiRequestBean bean) {
		List<OrderBean> list = orderMapper.listSupplierStaticsReportDaily(bean.getParam());
		return list;
	}

	@Transactional(readOnly=true)
//	@Cacheable("listDistributorStaticsReportDaily")
	public List<OrderBean> listSupplierStaticsReportMonthly(JEasyuiRequestBean bean) {
		List<OrderBean> list = orderMapper.listSupplierStaticsReportMonthly(bean.getParam());
		return list;
	}

}
