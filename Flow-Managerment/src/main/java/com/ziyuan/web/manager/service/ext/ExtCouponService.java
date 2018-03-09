package com.ziyuan.web.manager.service.ext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.LogWebOrderPush;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.global.domain.flow.QueueOrder;
import com.shziyuan.flow.global.domain.flow.QueueReportPush;
import com.ziyuan.web.manager.domain.OrderBean;
import com.ziyuan.web.manager.domain.ext.ECoupon;
import com.ziyuan.web.manager.service.impl.AbstractCRUDService;
import com.ziyuan.web.manager.utils.DateUtils;
import com.ziyuan.web.manager.utils.ExcelData;
import com.ziyuan.web.manager.utils.ExcelRow;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.StringUtil;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.OrderWrap;
import com.ziyuan.web.manager.wrap.ext.ExtCouponWrap;





@Service
public class ExtCouponService extends AbstractCRUDService<ECoupon> {

	@Resource
	private ExtCouponWrap extCouponWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		//DynamicDataSourceHolder.useSlave();
//		String shellName = domain.getParam().get("filename");
//		ExcelRow heads = ExcelTools.excelHeaders(
//				"商户订单号","平台订单号","渠道名称","渠道编码","供应商名称","供应商产品名称",
//				"平台产品名称","操作时间");
//		ExcelData data = null;
//		List<OrderBean> list = selectAll(domain);
//		System.out.println(list.size()+"-----------");
//		if (list!=null && list.size() !=0) {
//			data = new ExcelData(list.size());
//			for (int i = 0; i < list.size(); i++) {
//				OrderBean orderBean = list.get(i);
//				ExcelRow row = new ExcelRow();
//				row.add(orderBean.getClient_order_no());
//				row.add(orderBean.getOrder_no());
//				row.add(orderBean.getDistributor_name());
//				row.add(orderBean.getDistributor_code());
//				row.add(orderBean.getSupplier_name());
//				row.add(orderBean.getSupplier_code_name());
//				row.add(orderBean.getSku());
//				row.add(orderBean.getProcess_time());
//				
//				data.add(row);
//			}
//		}
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		try {
//			ExcelTools.writeToXLS(heads, data, shellName, out);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return null;
	}
	
	@Override
	protected String getMQCahceName() {
		return null;
	}

	@Override
	protected void sendMQ() {
		// TODO Auto-generated method stub
	}
	@Override
	public ICRUDWrap<ECoupon> getWrap() {
		return extCouponWrap;
	}
	
}
