package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.common.StatusCodeEnum;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.LogWebOrderPush;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.mq.stream.producer.OrderManualMessageProducer;
import com.ziyuan.web.manager.domain.DistributorDetailStatisticDaily;
import com.ziyuan.web.manager.domain.DistributorStatistics;
import com.ziyuan.web.manager.domain.DistributorSuccessStatistics;
import com.ziyuan.web.manager.domain.OrderBean;
import com.ziyuan.web.manager.domain.OrderReportBean;
import com.ziyuan.web.manager.domain.OrderStatisticBean;
import com.ziyuan.web.manager.domain.QueueReportPush;
import com.ziyuan.web.manager.domain.report.AtomicDouble;
import com.ziyuan.web.manager.feign.InfoRelationService;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowWrap;
import com.ziyuan.web.manager.service.fmt.Formatter;
import com.ziyuan.web.manager.utils.DateUtils;
import com.ziyuan.web.manager.utils.ExcelData;
import com.ziyuan.web.manager.utils.ExcelRow;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.StringUtil;
import com.ziyuan.web.manager.utils.ValidateUtils;
import com.ziyuan.web.manager.wrap.BindSupplierWrap;
import com.ziyuan.web.manager.wrap.DistributorStatisticsDetailWrap;
import com.ziyuan.web.manager.wrap.DistributorStatisticsSuccessWrap;
import com.ziyuan.web.manager.wrap.DistributorStatisticsWrap;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoDistributorWrap;
import com.ziyuan.web.manager.wrap.OrderWrap;
import com.ziyuan.web.manager.wrap.QueueSupplierReportActiveWrap;

@Service
public class OrderService extends AbstractCRUDService<OrderReportBean> {

	@Resource
	private OrderWrap orderWrap;
	
	@Resource
	private BindSupplierWrap bindSupplierWrap;
	
	@Resource
	private QueueSupplierReportActiveWrap queueSupplierReportActiveWrap;
	
	@Resource
	private Formatter formatter;
	private static final int EXCEL_SIZE = 500000;
	
	@Resource
	private MyBatisCursorItemReader myBatisCursorItemReader;
	
	@Resource
	private DistributorStatisticsWrap distributorStatisticsWrap;
	
	@Resource
	private DistributorStatisticsSuccessWrap distributorStatisticsSuccessWrap;
	
	@Resource
	private DistributorStatisticsDetailWrap distributorStatisticsDetailWrap;
	
	@Resource
	private InfoDistributorWrap infoDistributorWrap;
	
	@Autowired
	private OrderManualMessageProducer orderManualMessageProducer;
	
	@Resource
	private InfoRelationService infoRelationService;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		Map<String,Object> paramMap = new HashMap<>();
		Map<String, String> reqParam = domain.getParam();
		reqParam.remove("_method");
		reqParam.remove("filename");
		reqParam.remove("page");
		reqParam.remove("rows");
		paramMap.put("param", reqParam);
		//设置SQL语句执行的参数,paramMap包含参数的map
		myBatisCursorItemReader.setParameterValues(paramMap);
		//打开
		myBatisCursorItemReader.open(new ExecutionContext());

        //使用游标迭代获取每个记录
        Long count = 0L;
        OrderReportBean result;
        
        String sheetname = "订单表";
        String[] headers = {"商户订单号","平台订单号","渠道名称","渠道编码","供应商名称","供应商产品名称",
				"平台产品名称","创建时间","操作时间"};
        
        NormallyExcelExporter<OrderReportBean> exporter = new NormallyExcelExporter<>(sheetname, headers, null,1000);
        Sheet sheet = exporter.createHeaderSheet();
        int rowIndex = 1;
        try {
        	//循环遍历游标
			while ((result = (OrderReportBean) myBatisCursorItemReader.read()) != null) {
			   RowWrap row = new RowWrap(sheet, rowIndex ++);
			   row.add(result.getClient_order_no());
			   row.add(result.getOrder_no());
			   row.add(result.getDistributor_name());
			   row.add(result.getDistributor_code());
			   row.add(result.getSupplier_name());
			   row.add(result.getSupplier_code_name());
			   row.add(result.getSku());
			   row.add(result.getCreate_time());
			   row.add(result.getProcess_time());
			}
			
			return exporter.write();
		} catch (Exception e) {
			logger.error("导出订单表错误",e);
			return null;
		}finally {
			//关闭
			myBatisCursorItemReader.close();
		}
	}
	
	public List<OrderReportBean> selectAll(JEasyuiRequestBean bean) {
		//去除其他属性
		if (StringUtil.isNotNull(bean.getParam().get("filename"))) {
			bean.getParam().remove("filename");
		}
		if (StringUtil.isNotNull(bean.getParam().get("_method"))) {
			bean.getParam().remove("_method");
		}
		return orderWrap.selectAll(bean);
	}
	
	@Override
	protected String getMQCahceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sendMQ() {
		// TODO Auto-generated method stub
	}
	@Override
	public ICRUDWrap<OrderReportBean> getWrap() {
		return orderWrap;
	}
	
	public JEasyuiData listDistributorStaticsReportDaily(JEasyuiRequestBean bean) {
		String monthDate = bean.getParam().get("endTime");
		if (!"".equals(monthDate) && monthDate != null) {
			try {
				Calendar ca = Calendar.getInstance();
				ca.setTime(DateUtils.getDateByStr(monthDate));
				ca.add(Calendar.DAY_OF_MONTH, 1);
				monthDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(monthDate);
			bean.getParam().put("endTime", monthDate);
		}
		List<OrderBean> list = orderWrap.listDistributorStaticsReportDaily(bean);
		return addStaticsDistributor(list);
		
	}
	
	public JEasyuiData listDistributorStaticsReportMonth(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		String monthDate = bean.getParam().get("endTime");
		if (!"".equals(monthDate) && monthDate != null) {
			try {
				monthDate+="-01";
				Calendar ca = Calendar.getInstance();
				ca.setTime(DateUtils.getDateByStr(monthDate));
				ca.add(Calendar.MONTH, 1);
				monthDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(monthDate);
			bean.getParam().put("endTime", monthDate);
		}
		List<OrderBean> list = orderWrap.listDistributorStaticsReportMonthly(bean);
		return addStaticsDistributor(list);
		
	}
	
	public JEasyuiData listAccountStatics(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		String monthDate = bean.getParam().get("endTime");
		if (!"".equals(monthDate) && monthDate != null) {
			try {
				Calendar ca = Calendar.getInstance();
				ca.setTime(DateUtils.getDateByStr(monthDate));
				ca.add(Calendar.DAY_OF_MONTH, 1);
				monthDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(monthDate);
			bean.getParam().put("endTime", monthDate);
		}
		List<OrderReportBean> list = orderWrap.listAccountStatics(bean);
		return new JEasyuiData(list) ;
		
	}
	
	public ByteArrayOutputStream exportDaily(JEasyuiRequestBean bean) {
		
		ExcelRow heads = ExcelTools.excelHeaders(
				"时间","渠道商","省份","运营商","使用范围","月租类型","颗粒度","产品名称","成功笔数","进价","标准价",
				"销售额");
		ExcelData data = null;
		List<OrderBean> list = (List<OrderBean>) listDistributorStaticsReportDaily(bean).getRows();
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				OrderBean orderBean = list.get(i);
				ExcelRow row = new ExcelRow();
				if (orderBean.getCreate_time() == null) {
					row.add("");
				} else {
					row.add(orderBean.getCreate_time());
				}
				row.add(orderBean.getDistributor_name());
				row.add(orderBean.getProvince() == null ? "" : orderBean.getProvince());
				row.add(orderBean.getOperator() == null ? "" : orderBean.getOperator());
				row.add(orderBean.getScope_name() == null ? "":orderBean.getScope_name());
				row.add(orderBean.getRent_type() == null ? "":orderBean.getRent_type());
				row.add(orderBean.getPkgsize());
				row.add(orderBean.getSku_name());
				row.add(orderBean.getCount());
				row.add(orderBean.getBid());
				row.add(orderBean.getStandard());
				row.add(orderBean.getSales());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;		 
	}
	
	public ByteArrayOutputStream exportMonth(JEasyuiRequestBean bean) {
		ExcelRow heads = ExcelTools.excelHeaders(
				"时间","渠道商","产品","成功笔数","进价","标准价",
				"销售额");
		ExcelData data = null;
		List<OrderBean> list = (List<OrderBean>) listDistributorStaticsReportMonth(bean).getRows();
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				OrderBean orderBean = list.get(i);
				ExcelRow row = new ExcelRow();
				if (orderBean.getCreate_time() == null) {
					row.add("");
				} else {
					row.add(orderBean.getCreate_time());
				}
				row.add(orderBean.getDistributor_name());
				row.add(orderBean.getSku());
				row.add(orderBean.getCount());
				row.add(orderBean.getBid());
				row.add(orderBean.getStandard());
				row.add(orderBean.getSales());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
				 
	}
	
	public JEasyuiData listSupplierStaticsReportDaily(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		String monthDate = bean.getParam().get("endTime");
		if (!"".equals(monthDate) && monthDate != null) {
			try {
				Calendar ca = Calendar.getInstance();
				ca.setTime(DateUtils.getDateByStr(monthDate));
				ca.add(Calendar.DAY_OF_MONTH, 1);
				monthDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(monthDate);
			bean.getParam().put("endTime", monthDate);
		}
		List<OrderBean> list = orderWrap.listSupplierStaticsReportDaily(bean);
		return addStaticsSupplier(list);
		
	}
	
	public JEasyuiData listSupplierStaticsReportMonth(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		String monthDate = bean.getParam().get("endTime");
		
		if (!"".equals(monthDate) && monthDate != null) {
			try {
				monthDate+="-01";
				Calendar ca = Calendar.getInstance();
				ca.setTime(DateUtils.getDateByStr(monthDate));
				ca.add(Calendar.MONTH, 1);
				monthDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(monthDate);
			bean.getParam().put("endTime", monthDate);
		}
		List<OrderBean> list = orderWrap.listSupplierStaticsReportMonthly(bean);
		return addStaticsSupplier(list);
		
	}
	
	public ByteArrayOutputStream exportDailySupplier(JEasyuiRequestBean bean) {
		ExcelRow heads = ExcelTools.excelHeaders(
				"时间","供应商","产品","成功笔数","进价","标准价",
				"销售额");
		ExcelData data = null;
		List<OrderBean> list = (List<OrderBean>) listSupplierStaticsReportDaily(bean).getRows();
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				OrderBean orderBean = list.get(i);
				ExcelRow row = new ExcelRow();
				if (orderBean.getCreate_time() == null) {
					row.add("");
				} else {
					row.add(orderBean.getCreate_time());
				}
				row.add(orderBean.getSupplier_name());
				row.add(orderBean.getSku());
				row.add(orderBean.getCount());
				row.add(orderBean.getBid());
				row.add(orderBean.getStandard());
				row.add(orderBean.getSales());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
				 
	}
	
	public ByteArrayOutputStream exportMonthSupplier(JEasyuiRequestBean bean) {
		ExcelRow heads = ExcelTools.excelHeaders(
				"时间","供应商","产品","成功笔数","进价","标准价",
				"销售额");
		ExcelData data = null;
		List<OrderBean> list = (List<OrderBean>) listSupplierStaticsReportMonth(bean).getRows();
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				OrderBean orderBean = list.get(i);
				ExcelRow row = new ExcelRow();
				if (orderBean.getCreate_time() == null) {
					row.add("");
				} else {
					row.add(orderBean.getCreate_time());
				}
				row.add(orderBean.getSupplier_name());
				row.add(orderBean.getSku());
				row.add(orderBean.getCount());
				row.add(orderBean.getBid());
				row.add(orderBean.getStandard());
				row.add(orderBean.getSales());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
				 
	}
	
	public ByteArrayOutputStream exportOrder(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		//DynamicDataSourceHolder.useSlave();
		// 数据库中存储的数据行  
	    int page_size = 10000;
		domain.getParam().remove("_method");
		domain.getParam().remove("filename");
		// 在内存中保持100行，超过100行将被刷新到磁盘  
	    SXSSFWorkbook wb = new SXSSFWorkbook(100);  
	    Sheet sh = wb.createSheet(); // 建立新的sheet对象  
	    CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        Cell cell;
        Row first_row = sh.createRow(0);   // 创建第一行对象 
        
        ExcelRow heads = null;
		if (domain.getParam().get("distributor_id") != null ) {
			heads = ExcelTools.excelHeaders(
					"订单号","渠道订单号","渠道名称","手机号","省份","运营商","使用范围",
					"月租类型","颗粒度","订单状态","说明","创建时间","订单完成时间","标准价格","销售价格");
		} else {
			heads = ExcelTools.excelHeaders(
					"订单号","渠道订单号","渠道名称","手机号","省份","运营商","使用范围",
					"月租类型","颗粒度","订单状态","说明","创建时间","订单完成时间","标准价格","销售价格","供应商");
		}
		
		for (int i = 0; i < heads.size(); i++) {
            cell = first_row.createCell(i);
            cell.setCellValue(heads.get(i));
            cell.setCellStyle(style);
        }
		
		
        List<OrderReportBean> list = new ArrayList<OrderReportBean>();
        
        // 求数据库中待导出数据的行数  
	    int list_count = orderWrap.totalCount(domain);
	    System.out.println("总共需要导出的记录数"+list_count);
		
	    // 根据行数求数据提取次数  
	    int export_times = list_count % page_size > 0 ? list_count / page_size  
	            + 1 : list_count / page_size; 
	    System.out.println("需要提取的次数"+export_times);
	    
		ExcelData data = null;
		Map<String,Object> searchMap = new HashMap<String,Object>();
    	searchMap.put("search", domain.getParam());
	    for (int j = 0; j < export_times; j++) {  
	    	searchMap.put("offset", j*page_size);
	    	searchMap.put("pageSize", page_size);
	    	list = orderWrap.selectByPage(domain); 
	    	System.out.println("第"+j+"次提取的数据量"+list.size());
	    	//填充data
	    	if (list!=null && list.size() !=0) {
				data = new ExcelData(list.size());
				for (int i = 0; i < list.size(); i++) {
					OrderReportBean orderBean = list.get(i);
					ExcelRow row = new ExcelRow();
					row.add(orderBean.getOrder_no());
					row.add(orderBean.getClient_order_no());
					row.add(orderBean.getDistributor_name());
					row.add(orderBean.getPhone());
					InfoSku _tmpSku = orderBean.getInfoSku();
					if(_tmpSku == null)
						_tmpSku = new InfoSku();
					row.add(formatter.getProvince(_tmpSku.getProvinceid()));
					row.add(_tmpSku.getOperator());
					row.add(formatter.getScope(_tmpSku.getScope_cid()));
					row.add(formatter.getRentType(_tmpSku.getRent_type()));
					row.add(orderBean.getPkgsize());
					row.add(formatter.getSuccess(orderBean.getSupplier_report_success()));
					row.add(orderBean.getStatus_report_code() == null ? orderBean.getStatus_report_code():"");
					row.add(orderBean.getCreate_time());
					row.add(orderBean.getProcess_time());
					row.add(orderBean.getStandard_price());
					row.add(orderBean.getDistributor_price());
					if (domain.getParam().get("distributor_id") == null ) {
						row.add(orderBean.getSupplier_name());
					}
					data.add(row);
				}
			}
	    	
	    	if (data != null && data.size() != 0) {
	        	for (int n = 0; n < data.size(); n++) {
	        		Row eachrow = sh.createRow(j * page_size + n + 1); 
	                ExcelRow datarow = data.get(n);
	                for (int m = 0; m < datarow.size(); m++) {
	                    cell = eachrow.createCell(m);
	                    cell.setCellValue(datarow.get(m));
	                    cell.setCellStyle(style);
	                }
	             }
	        }
	    	list.clear(); // 每次存储len行，用完了将内容清空，以便内存可重复利用  
	    	
	    }
	    ByteArrayOutputStream out  = new ByteArrayOutputStream();
		try {
	    wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return out;
	}
	
	private List<ExcelData>  createList(ExcelData targe) {  
        List<ExcelData> listArr = new ArrayList<ExcelData>();  
        //获取每个数组的个数
        int arrSize = targe.size()%EXCEL_SIZE==0?targe.size()/EXCEL_SIZE:targe.size()/EXCEL_SIZE+1;  
        for(int i=0;i<arrSize;i++) {  
        	ExcelData  sub = new ExcelData();  
            //把指定索引数据放入到list中  
            for(int j=i*EXCEL_SIZE;j<=EXCEL_SIZE*(i+1)-1;j++) {  
                if(j<=targe.size()-1) {  
                    sub.add(targe.get(j));  
                }  
            }  
            listArr.add(sub);  
        }  
        return listArr;  
    }  
	
	public ByteArrayOutputStream exportDistributorAccount(Principal user, JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		int id = infoDistributorWrap.selectIDByName(user.getName());
		bean.getParam().put("distributor_id", String.valueOf(id));
		ExcelRow heads = ExcelTools.excelHeaders(
				"省份","产品","进价","标准价","销售额");
		ExcelData data = null;
		List<OrderReportBean> list = (List<OrderReportBean>) listAccountStatics(bean).getRows();
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				OrderReportBean orderBean = list.get(i);
				ExcelRow row = new ExcelRow();
				row.add(orderBean.getProvince());
				row.add(orderBean.getSku());
//				row.add(orderBean.getBid());
				row.add(orderBean.getStandard());
				row.add(orderBean.getSales());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;	
	}
	
	private JEasyuiData addStaticsDistributor(List<OrderBean> list) {
		if(list.size() == 0)
			return new JEasyuiData(list);
		
		// 用于比较渠道号的条件
		OrderBean condition = newCondition(list.get(0));
		
		OrderBean totalDistributor = new OrderBean();
		totalDistributor.setDistributor_name("总数");
		
		// 新的结果集合
		List<OrderBean> result = new ArrayList<OrderBean>(list.size() + (list.size() / 3));
		// 遍历
		for(OrderBean order : list) {
			// ID相等 进行汇总
			if(order.getDistributor_id() == condition.getDistributor_id()) {
				calculateCondition(condition,order);
				//计算所有数据
				calculateCondition(totalDistributor,order);
				result.add(order);
			} else {
				result.add(condition);
				condition = newCondition(order);
				calculateCondition(condition,order);
				//计算所有数据
				calculateCondition(totalDistributor,order);
				result.add(order);
			}
		}
		result.add(condition);
		result.add(totalDistributor);
		return new JEasyuiData(result);
	}
	
	private void calculateCondition(OrderBean condition,OrderBean order) {
		condition.setCount(condition.getCount() + order.getCount());
		condition.setBid(condition.getBid() + order.getBid());
		condition.setSales(condition.getSales() + order.getSales());
		condition.setStandard(condition.getStandard() + order.getStandard());
	}
	
	private OrderBean newCondition(OrderBean order) {
		OrderBean condition = new OrderBean();
		condition.setProcess_time(null);
		condition.setDistributor_id(order.getDistributor_id());
		condition.setDistributor_name(order.getDistributor_name());
		condition.setBid(0);
		condition.setSales(0);
		condition.setStandard(0);
		return condition;
	}
	
	private JEasyuiData addStaticsSupplier(List<OrderBean> list) {
		if(list.size() == 0)
			return new JEasyuiData(list);
		
		// 用于比较渠道号的条件
		OrderBean condition = newCondition1(list.get(0));
		
		// 新的结果集合
		List<OrderBean> result = new ArrayList<OrderBean>(list.size() + (list.size() / 3));
		// 遍历
		for(OrderBean order : list) {
			// ID相等 进行汇总
			if(order.getSupplier_id() == condition.getSupplier_id()) {
				calculateCondition(condition,order);
				result.add(order);
			} else {
				result.add(condition);
				condition = newCondition(order);
				calculateCondition(condition,order);
				result.add(order);
			}
		}
		result.add(condition);
		return new JEasyuiData(result);
	}
	
	private OrderBean newCondition1(OrderBean order) {
		OrderBean condition = new OrderBean();
		condition.setProcess_time(null);
		condition.setSupplier_id(order.getSupplier_id());
		condition.setSupplier_name(order.getSupplier_name());
		condition.setBid(0);
		condition.setSales(0);
		condition.setStandard(0);
		return condition;
	}
	
	private void addQueueReportPush(OrderReportBean order, String username) throws Exception {
		
		QueueReportPush push = new QueueReportPush();
		LogWebOrderPush logPush = new LogWebOrderPush();
		logPush.setInsert_time(DateUtils.getSysDateTimestamp());
		logPush.setUpdate_user(username);
		
		push.setClient_order_no(order.getClient_order_no());
		logPush.setClient_order_no(order.getClient_order_no());
		
		push.setSku_mask(order.getSku_mask());
		push.setDistributor_code(order.getDistributor_code());
		push.setPhone(order.getPhone());
		logPush.setPhone(order.getPhone());
		
		push.setPkg_type(order.getPkg_type());
		push.setProvinceid(order.getProvinceid());
		logPush.setProvinceid(order.getProvinceid());
		
		push.setOrder_no(order.getOrder_no());
		logPush.setOrder_no(order.getOrder_no());
		
//		push.setSource(Constant.SYSTEM_MARK);
		push.setConnection_id(Constant.DEFAULT_CONNECTION_ID);
		push.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
//		push.setConsumer("NOC");
		push.setSku_id(order.getSku_id());
		push.setSku(order.getSku());
		push.setSupplier_id(order.getSupplier_id());
		push.setSend_time(DateUtils.getCurTimestamp());
		logPush.setSupplier_id(order.getSupplier_id());
		
		push.setSupplier_name(order.getSupplier_name());
		push.setSupplier_code_id(order.getSupplier_code_id());
		push.setSupplier_code_name(order.getSupplier_code_name());
		push.setStandard_price(order.getStandard_price());
		push.setSupplier_price(order.getSupplier_price());
		push.setSupplier_discount(order.getSupplier_discount());
		push.setDistributor_price(order.getDistributor_price());
		push.setDistributor_discount(order.getDistributor_discount());
		push.setDistributor_id(order.getDistributor_id());
		push.setDistributor_name(order.getDistributor_name());
		push.setNotify_url(order.getNotify_url());
		push.setRetries(0);
		push.setSupplier_result(null);
		push.setResult_code(StatusCodeEnum.REPORT_SUCCESS.code);
		push.setResult_message(StatusCodeEnum.REPORT_SUCCESS.message);
		
		orderWrap.submitQueueReport(push, logPush, order);
	}
	
	public OrderReportBean loadOrder(String order_no) {
		//DynamicDataSourceHolder.useSlave();
		return orderWrap.selectByOrderNo(order_no);
	}

	public JEasyuiData pushAgain(Principal user, JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		try {
			OrderReportBean pushOrder = loadOrder(bean.getParam().get("order_no"));
			
			ConfiguredBindBean configuredBindBean = infoRelationService.getBind(pushOrder.getPhone(), 
					pushOrder.getDistributor_code(), pushOrder.getSku(), pushOrder.getSupplier_sort());
			
			QueueOrder queue = new QueueOrder();
			queue.setCreate_time(pushOrder.getCreate_time());
			queue.setSchedule_time(pushOrder.getProcess_time());
			queue.setRetries(0);//推送成功0，推送失败-1
			queue.setManualCommand(QueueOrder.CMD_ORDER_CACHE_RESUBMIT);
			
			Order order = orderWrap.selectByOrderNo(bean.getParam().get("order_no"));
			
			QueueOrderMQWrap wrap = new QueueOrderMQWrap();
			
			wrap.setConfiguredBindBean(configuredBindBean);
			wrap.setOrder(order);
			wrap.setQueueOrder(queue);
			
			orderManualMessageProducer.send(wrap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false,e.getMessage());
		}
		return new JEasyuiData(true,"");
	}

	public JEasyuiData sendSuccess(Principal user, JEasyuiRequestBean bean) {
		try {
			OrderReportBean pushOrder = loadOrder(bean.getParam().get("order_no"));
			
			ConfiguredBindBean configuredBindBean = infoRelationService.getBind(pushOrder.getPhone(), 
					pushOrder.getDistributor_code(), pushOrder.getSku(), pushOrder.getSupplier_sort());
			
			QueueOrder queue = new QueueOrder();
			queue.setCreate_time(pushOrder.getCreate_time());
			queue.setSchedule_time(pushOrder.getProcess_time());
			queue.setRetries(0);//推送成功0，推送失败-1
			queue.setManualCommand(QueueOrder.CMD_ORDER_PUSH_SUCCESS);
			
			Order order = orderWrap.selectByOrderNo(bean.getParam().get("order_no"));
			
			QueueOrderMQWrap wrap = new QueueOrderMQWrap();
			
			wrap.setConfiguredBindBean(configuredBindBean);
			wrap.setOrder(order);
			wrap.setQueueOrder(queue);
			
			orderManualMessageProducer.send(wrap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false,e.getMessage());
		}
		return new JEasyuiData(true,"");
	}
	
//	private List<QueueSupplierReportActive> transferQueue(List<Map<String,Object>> requests,String mark) {
//		return requests.stream().map(req -> {
//			InfoSupplierBinded supplier = bindSupplierWrap.selectByOrder(req);
//			if(supplier == null)
//				throw new RuntimeException("不存在对应的绑定信息" + req.toString());
//			
//			QueueSupplierReportActive queue = new QueueSupplierReportActive();
//			queue.setSource(Constant.SYSTEM_MARK);
//			queue.setConsumer(supplier.getPlatform_mark());
//			queue.setIf_attribute(supplier.getIf_attribute());
//			queue.setConnection_id(0);
//			queue.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
//			queue.setSend_time(TimestampUtil.now());
//			queue.setRetries(0);
//			queue.setMark(mark);
//			queue.setSupplier_sort(1);
//			queue.setOrder_no((String) req.get("order_no"));
//			return queue;
//		}).collect(Collectors.toList());
//	}
	public JEasyuiData sendSuccessBatch(List<Map<String,Object>> requests) {
		
		try {

			return new JEasyuiData(true,"");
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false,e.getMessage());
		}
	}
	
	public JEasyuiData sendFaildBatch(List<Map<String,Object>> requests) {
		
		try {

			return new JEasyuiData(true,"");
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false,e.getMessage());
		}
	}

	public void updateOrder(OrderReportBean order) {
		
		orderWrap.update(order);
	}
	
	public JEasyuiData sendFail(Principal user, JEasyuiRequestBean bean) {
		try {
			OrderReportBean pushOrder = loadOrder(bean.getParam().get("order_no"));
			
			ConfiguredBindBean configuredBindBean = infoRelationService.getBind(pushOrder.getPhone(), 
					pushOrder.getDistributor_code(), pushOrder.getSku(), pushOrder.getSupplier_sort());
			
			QueueOrder queue = new QueueOrder();
			queue.setCreate_time(pushOrder.getCreate_time());
			queue.setSchedule_time(pushOrder.getProcess_time());
			queue.setRetries(-1);//推送成功0，推送失败-1
			queue.setManualCommand(QueueOrder.CMD_ORDER_PUSH_FAILD);
			
			Order order = orderWrap.selectByOrderNo(bean.getParam().get("order_no"));
			
			QueueOrderMQWrap wrap = new QueueOrderMQWrap();
			
			wrap.setConfiguredBindBean(configuredBindBean);
			wrap.setOrder(order);
			wrap.setQueueOrder(queue);
			
			orderManualMessageProducer.send(wrap);
		} catch (Exception e) {
			e.printStackTrace();
			return new JEasyuiData(false,e.getMessage());
		}
		return new JEasyuiData(true,"");
	}
	
	public void addQueueOrder(OrderReportBean order, String username) {
		
//		QueueOrder queueOrder = new QueueOrder();
//		LogWebOrderPush push = new LogWebOrderPush();
//		push.setInsert_time(DateUtils.getSysDateTimestamp());
//		push.setUpdate_user(username);
//		
//		queueOrder.setClient_order_no(order.getClient_order_no());
//		push.setClient_order_no(order.getClient_order_no());
//		
//		queueOrder.setSku_mask(order.getSku_mask());
//		queueOrder.setDistributor_code(order.getDistributor_code());
//		queueOrder.setPhone(order.getPhone());
//		push.setPhone(order.getPhone());
//		
//		queueOrder.setPkg_type(order.getPkg_type());
//		queueOrder.setProvinceid(order.getProvinceid());
//		push.setProvinceid(order.getProvinceid());
//		
//		queueOrder.setOrder_no(order.getOrder_no());
//		push.setOrder_no(order.getOrder_no());
//		
//		queueOrder.setSource(Constant.SYSTEM_MARK);
//		queueOrder.setConnection_id(Constant.DEFAULT_CONNECTION_ID);
//		queueOrder.setStatus(Constant.QUEUE_STATUS.WAIT_FOR_PROCESS.val);
//		queueOrder.setRetries(-1);
//		queueOrder.setNotify_url(order.getNotify_url());
//		queueOrder.setSupplier_id(order.getSupplier_id());
//		push.setSupplier_id(order.getSupplier_id());
//		orderWrap.submitQueueOrder(queueOrder, push, order);
	}

	public ByteArrayOutputStream exportTimeStatistics(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();	
		String start = bean.getParam().get("startTime");
			String end = bean.getParam().get("endTime");
			List<String> betweens;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				betweens = DateUtils.getBetweenDates(DateUtils.parseDate(start, "yyyy-MM-dd"),DateUtils.parseDate(end, "yyyy-MM-dd"));
				
				List<DistributorStatistics> data_rows = distributorStatisticsWrap.selectTimeStatistics(bean);
				if (ValidateUtils.isEmpty(data_rows)) {
					return null;
				}
				List<List<Object>> result = new ArrayList<>();
				List<String> distrinameNames = distributorStatisticsWrap.selectDistinctDistributorName(bean);
				for(String dname : distrinameNames) {
					//定义每一行的数据
					List<Object> temp_row = new ArrayList<Object>();
					//定义一段日期内的总销量
					double totals=0;
					//定义渠道最后余额
					double balance=0;
					temp_row.add(dname);
					for(String each_date : betweens) {
						//用于判断某个日期下是否有数据
						boolean isExist = false;
						for(DistributorStatistics ds : data_rows) {
							if (dname.equals(ds.getDistributor_name())&& each_date.equals(DateUtils.formatDate(ds.getCreate_time(), "yyyy-MM-dd"))) {
								isExist = true;
								temp_row.add(ds.getSales());
								totals += ds.getSales();
								balance = ds.getBalance();
							}
						}
						if (!isExist) {
							temp_row.add(0.0);
						}
						
					}
					temp_row.add(totals);
					temp_row.add(balance);
					result.add(temp_row);
				}
				//最后一行统计
				List<Object> last_row = new ArrayList<>();
				last_row.add("合计");
				int index = result.get(0).size();
				//index表示某个列
				for (int i = 1; i< index; i++) {
					double day_total=0;
					for (List<Object> each_row:result) {
						day_total += (double)each_row.get(i);
					}
					last_row.add(day_total);
				}
				result.add(last_row);
				//最后填充表头
				betweens.add(0, "日期");
				betweens.add("合计");
				betweens.add("账户余额");
				ExcelRow heads = ExcelTools.excelHeadersList(betweens);
				ExcelTools.writeToXLSX(heads, result, bean.getParam().get("filename"), out);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		return out;
	}
	
	public Map<String,Object> getTimeStatistics(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();	
		Map<String,Object> map = new HashMap<String,Object>();
			String start = bean.getParam().get("startTime").split(" ")[0];
			String end = bean.getParam().get("endTime").split(" ")[0];
			List<String> betweens;
			try {
				betweens = DateUtils.getBetweenDates(DateUtils.parseDate(start, "yyyy-MM-dd"),DateUtils.parseDate(end, "yyyy-MM-dd"));
				
				List<DistributorStatistics> data_rows = distributorStatisticsWrap.selectTimeStatistics(bean);
				if (ValidateUtils.isEmpty(data_rows)) {
					return null;
				}
				List<List<Object>> result = new ArrayList<>();
				List<String> distrinameNames = distributorStatisticsWrap.selectDistinctDistributorName(bean);
				for(String dname : distrinameNames) {
					//定义每一行的数据
					List<Object> temp_row = new ArrayList<Object>();
					//定义一段日期内的总销量
					double totals=0;
					//定义渠道最后余额
					double balance=0;
					temp_row.add(dname);
					for(String each_date : betweens) {
						//用于判断某个日期下是否有数据
						boolean isExist = false;
						for(DistributorStatistics ds : data_rows) {
							if (dname.equals(ds.getDistributor_name())&& each_date.equals(DateUtils.formatDate(ds.getCreate_time(), "yyyy-MM-dd"))) {
								isExist = true;
								temp_row.add(ds.getSales());
								totals += ds.getSales();
								balance = ds.getBalance();
							}
						}
						if (!isExist) {
							temp_row.add(0.0);
						}
						
					}
					temp_row.add(totals);
					temp_row.add(balance);
					result.add(temp_row);
				}
				//最后一行统计
				List<Object> last_row = new ArrayList<>();
				last_row.add("合计");
				int index = result.get(0).size();
				//index表示某个列
				for (int i = 1; i< index; i++) {
					double day_total=0;
					for (List<Object> each_row:result) {
						day_total += (double)each_row.get(i);
					}
					last_row.add(day_total);
				}
				result.add(last_row);
				//最后填充表头
				betweens.add(0, "日期");
				betweens.add("合计");
				betweens.add("账户余额");
				map.put("head", betweens);
				map.put("content", result);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		return map;
	}
	
	public ByteArrayOutputStream exportTimeSuccessStatistics(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();	
		String start = bean.getParam().get("startTime");
		String end = bean.getParam().get("endTime");
		java.text.DecimalFormat df   =new java.text.DecimalFormat("#.0000");  
		List<String> betweens;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			betweens = DateUtils.getBetweenDates(DateUtils.parseDate(start, "yyyy-MM-dd"),DateUtils.parseDate(end, "yyyy-MM-dd"));
			List<DistributorSuccessStatistics> data_rows = distributorStatisticsSuccessWrap.selectTimeStatistics(bean);
			if (ValidateUtils.isEmpty(data_rows)) {
				return null;
			}
			//定义第二行头的数据
			List<List<Object>> result = new ArrayList<>();
			List<String> distrinameNames = distributorStatisticsSuccessWrap.selectDistinctDistributorName(bean);
			for(String dname : distrinameNames) {
				//定义每一行的数据
				List<Object> temp_row = new ArrayList<Object>();
				temp_row.add(dname);
				for(String each_date : betweens) {
					//用于判断某个日期下是否有数据
					boolean isExist = false;
					for(DistributorSuccessStatistics ds : data_rows) {
						if (dname.equals(ds.getDistributor_name())&& each_date.equals(DateUtils.formatDate(ds.getCreate_time(), "yyyy-MM-dd"))) {
							isExist = true;
							temp_row.add(ds.getSuccess());
							temp_row.add(ds.getTotal());
							BigDecimal b = new BigDecimal((double)ds.getSuccess()/(double)ds.getTotal());  
							double success_rate = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
							temp_row.add(success_rate*100+"%");
						}
					}
					if (!isExist) {
						temp_row.add(0);
						temp_row.add(0);
						temp_row.add(0);
					}
				}
				result.add(temp_row);
			}
			//最后一行统计
			List<Object> last_row = new ArrayList<>();
			last_row.add("总计");
			int index = result.get(0).size();
			//index表示某个列
			for (int i = 1; i< index; i++) {
				//去掉成功率的相加
				if (i%3 != 0) {
					int day_total=0;
					for (List<Object> each_row:result) {
						day_total += (int)each_row.get(i);
					}
					last_row.add(day_total);
				} else {
					last_row.add("");

				}
				
			}
			result.add(last_row);
			//第二行统计，所有中文类行放到最后放入，方便前面纯数字进行统计
			List<Object> second_row = new ArrayList<>();
			second_row.add("");
			for(String each_date : betweens) {
				second_row.add("成功数");	
				second_row.add("总数");	
				second_row.add("成功率");	
			}
			result.add(0,second_row);
			//构造表头
			List<String> headers = new ArrayList<String>();
			headers.add("日期");
			for (String each:betweens) {
				headers.add("");
				headers.add(each);
				headers.add("");
			}
			ExcelRow heads = ExcelTools.excelHeadersList(headers);
			ExcelTools.writeToXLSX(heads, result, bean.getParam().get("filename"), out);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return out;
	}
	
	public Map<String,Object> getTimeSuccessStatistics(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();	
		Map<String,Object> map = new HashMap<String,Object>();
		String start = bean.getParam().get("startTime");
		String end = bean.getParam().get("endTime");
		java.text.DecimalFormat df   =new java.text.DecimalFormat("#.0000");  
		List<String> betweens;
		try {
			betweens = DateUtils.getBetweenDates(DateUtils.parseDate(start, "yyyy-MM-dd"),DateUtils.parseDate(end, "yyyy-MM-dd"));
			List<DistributorSuccessStatistics> data_rows = distributorStatisticsSuccessWrap.selectTimeStatistics(bean);
			if (ValidateUtils.isEmpty(data_rows)) {
				return null;
			}
			//定义第二行头的数据
			List<List<Object>> result = new ArrayList<>();
			List<String> distrinameNames = distributorStatisticsSuccessWrap.selectDistinctDistributorName(bean);
			for(String dname : distrinameNames) {
				//定义每一行的数据
				List<Object> temp_row = new ArrayList<Object>();
				temp_row.add(dname);
				for(String each_date : betweens) {
					//用于判断某个日期下是否有数据
					boolean isExist = false;
					for(DistributorSuccessStatistics ds : data_rows) {
						if (dname.equals(ds.getDistributor_name())&& each_date.equals(DateUtils.formatDate(ds.getCreate_time(), "yyyy-MM-dd"))) {
							isExist = true;
							temp_row.add(ds.getSuccess());
							temp_row.add(ds.getTotal());
							BigDecimal b = new BigDecimal((double)ds.getSuccess()/(double)ds.getTotal());  
							double success_rate = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
							temp_row.add(success_rate*100+"%");
						}
					}
					if (!isExist) {
						temp_row.add(0);
						temp_row.add(0);
						temp_row.add(0);
					}
				}
				result.add(temp_row);
			}
			//最后一行统计
			List<Object> last_row = new ArrayList<>();
			last_row.add("总计");
			int index = result.get(0).size();
			//index表示某个列
			for (int i = 1; i< index; i++) {
				//去掉成功率的相加
				if (i%3 != 0) {
					int day_total=0;
					for (List<Object> each_row:result) {
						day_total += (int)each_row.get(i);
					}
					last_row.add(day_total);
				} else {
					last_row.add("");

				}
				
			}
			result.add(last_row);
			//第二行统计，所有中文类行放到最后放入，方便前面纯数字进行统计
			List<Object> second_row = new ArrayList<>();
			second_row.add("");
			for(String each_date : betweens) {
				second_row.add("成功数");	
				second_row.add("总数");	
				second_row.add("成功率");	
			}
			result.add(0,second_row);
			//构造表头
			List<String> headers = new ArrayList<String>();
			headers.add("日期");
			for (String each:betweens) {
				headers.add("");
				headers.add(each);
				headers.add("");
			}
			map.put("header", headers);
			map.put("content", result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	public JEasyuiData statisticByTime(JEasyuiRequestBean bean) {
		List<OrderStatisticBean> list = new ArrayList<>();
		list.add(orderWrap.statisticByTime(bean));
		return new JEasyuiData(list);
	}

	public Map<String, Object> getTotalDetail(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		//DynamicDataSourceHolder.useSlave();	
		Map<String,Object> map = new HashMap<String,Object>();
		//定义第二行头的数据
		List<List<Object>> result = new ArrayList<>();
		List<String> skuNames = distributorStatisticsDetailWrap.selectDistinctSkuName(bean);
		List<DistributorDetailStatisticDaily> data_rows = distributorStatisticsDetailWrap.selectTimeDetail(bean);
		if (ValidateUtils.isEmpty(data_rows)) {
			return null;
		}
		for(String sku : skuNames) {
			//定义成功数
			int success=0;
			//定义失败数
			int fail=0;
			int total=0;//定义总数
			double standand_price = 0;//产品价格
			double sales = 0;//销售额
			List<Object> temp_row = new ArrayList<Object>();
			temp_row.add(sku);
			for(DistributorDetailStatisticDaily each_row : data_rows) {
				if (sku.equals(each_row.getSku_name())) {
					if (each_row.getSupplier_report_success() == 80) {
						success += each_row.getTotal();
						total += each_row.getTotal();
						standand_price += each_row.getTotal()*each_row.getStandard_price();
						sales += each_row.getTotal()*each_row.getDistributor_price();
					} else if (each_row.getSupplier_report_success() == 81) {
						fail += each_row.getTotal();
						total += each_row.getTotal();
					} else {
						total += each_row.getTotal();
					}
				} 
			}
			temp_row.add(total);
			temp_row.add(success);
			temp_row.add(fail);
			BigDecimal b = new BigDecimal((double)success/(double)total);  
			double success_rate = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
			temp_row.add(success_rate*100+"%");
			//成功面值和销售金额
			temp_row.add(standand_price);
			temp_row.add(sales);
			result.add(temp_row);
		}
		//最后一行统计
		List<Object> last_row = new ArrayList<>();
		last_row.add("总计");
		int index = result.get(0).size();
		//index表示某个列
		for (int i = 1; i< index; i++) {
			//去掉成功率的相加
			if (i != 4) {
				int day_total=0;
				for (List<Object> each_row:result) {
					try {
					day_total += (int)each_row.get(i);
					} catch(Exception e) {
						day_total += Double.parseDouble(each_row.get(i).toString());
					}
				}
				last_row.add(day_total);
			} else {
				last_row.add("");
			}	
		}
		result.add(last_row);
		//最后填充表头
		List<String> headers = new ArrayList<String>();
		headers.add("产品名称");
		headers.add("订单数");
		headers.add("成功订单数");
		headers.add("失败订单数");
		headers.add("成功率");
		headers.add("成功面值");
		headers.add("销售金额");
		map.put("head", headers);
		map.put("content", result);
		return map;
	}
	
	public ByteArrayOutputStream exportTotalDetail(JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		//DynamicDataSourceHolder.useSlave();	
		//定义第二行头的数据
		List<List<Object>> result = new ArrayList<>();
		List<String> skuNames = distributorStatisticsDetailWrap.selectDistinctSkuName(bean);
		List<DistributorDetailStatisticDaily> data_rows = distributorStatisticsDetailWrap.selectTimeDetail(bean);
		if (ValidateUtils.isEmpty(data_rows)) {
			return null;
		}
		for(String sku : skuNames) {
			//定义成功数
			int success=0;
			//定义失败数
			int fail=0;
			int total=0;//定义总数
			double standand_price = 0;//产品价格
			double sales = 0;//销售额
			List<Object> temp_row = new ArrayList<Object>();
			temp_row.add(sku);
			for(DistributorDetailStatisticDaily each_row : data_rows) {
				if (sku.equals(each_row.getSku_name())) {
					if (each_row.getSupplier_report_success() == 80) {
						success += each_row.getTotal();
						total += each_row.getTotal();
						standand_price += each_row.getTotal()*each_row.getStandard_price();
						sales += each_row.getTotal()*each_row.getDistributor_price();
					} else if (each_row.getSupplier_report_success() == 81) {
						fail += each_row.getTotal();
						total += each_row.getTotal();
					} else {
						total += each_row.getTotal();
					}
				} 
			}
			temp_row.add(total);
			temp_row.add(success);
			temp_row.add(fail);
			BigDecimal b = new BigDecimal((double)success/(double)total);  
			double success_rate = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
			temp_row.add(success_rate*100+"%");
			//成功面值和销售金额
			temp_row.add(standand_price);
			temp_row.add(sales);
			result.add(temp_row);
		}
		//最后一行统计
		List<Object> last_row = new ArrayList<>();
		last_row.add("总计");
		int index = result.get(0).size();
		//index表示某个列
		for (int i = 1; i< index; i++) {
			//去掉成功率的相加
			if (i != 4) {
				int day_total=0;
				for (List<Object> each_row:result) {
					try {
					day_total += (int)each_row.get(i);
					} catch(Exception e) {
						day_total += Double.parseDouble(each_row.get(i).toString());
					}
				}
				last_row.add(day_total);
			} else {
				last_row.add("");
			}	
		}
		result.add(last_row);
		//最后填充表头
		List<String> headers = new ArrayList<String>();
		headers.add("产品名称");
		headers.add("订单数");
		headers.add("成功订单数");
		headers.add("失败订单数");
		headers.add("成功率");
		headers.add("成功面值");
		headers.add("销售金额");	
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ExcelRow heads = ExcelTools.excelHeadersList(headers);
		try {
			ExcelTools.writeToXLSX(heads, result, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	public List<String> getDistributorNames(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return distributorStatisticsWrap.selectDistinctDistributorName(bean);
	}

}
