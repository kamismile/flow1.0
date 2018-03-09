package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import com.shziyuan.flow.global.common.CacheDefine;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.domain.InfoDistributorBean;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoDistributorWrap;


@Service("infoDistributorService")
public class InfoDistributorService extends AbstractCRUDService<InfoDistributor> {

	@Resource
	private InfoDistributorWrap infoDistributorWrap;
	
	@Override
	public ICRUDWrap<InfoDistributor> getWrap() {
		return infoDistributorWrap;
	}
	
	@Override
	protected String getMQCahceName() {
		return FlowMqDefine.CHANGE_DISTRIBUTOR;
	}
	
	public int selectCountByCode(String code) {
//		//DynamicDataSourceHolder.useSlave();
		return infoDistributorWrap.selectCountByCode(code);
	}
	
	private String generateKey() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < 8; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	}
	@Override
	public JEasyuiData insert(InfoDistributor domain) {
		domain.setCreate_time(TimestampUtil.now());
		domain.setEnabled(true);
		domain.setKey(generateKey());
		int count = selectCountByCode(domain.getCode());
		if (count > 0) {
			return new JEasyuiData(false, "渠道代码已存在");
		}
		return super.insert(domain);
	}
	
	public JEasyuiData verifySuccess(int id) {
//		
		try {
			infoDistributorWrap.verifySuccess(id);
			return new JEasyuiData(true,"");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	public JEasyuiData verifyFaild(int id) {
//		
		try {
			infoDistributorWrap.verifyFaild(id);
			return new JEasyuiData(true,"");
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	public JEasyuiData updateUrl(InfoDistributor domain) {
//		
		try {
			infoDistributorWrap.updateUrl(domain);
			return new JEasyuiData(domain);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean bean) {
//		//DynamicDataSourceHolder.useSlave();
		
		List<InfoDistributor> dataList = infoDistributorWrap.selectAll(bean);
		String sheetname = "渠道商列表";
		String[] headers = {"名称","渠道代码","余额","信用额","应收款","赠送额度","公司名","联系人","电话","邮件",
				"创建时间","更新时间","更新用户","状态报告推送地址","渠道接口加密key","总消费","当日消费","当月消费","是否启用"};
		RowFunction<InfoDistributor> rowAction = (distributor,row) -> {
			InfoDistributorBean dis = (InfoDistributorBean)distributor;
			row.add(dis.getName());
			row.add(dis.getCode());
			row.add(dis.getAccountDistributor().getBanlance());
			row.add(dis.getAccountDistributor().getCredit());
			row.add(dis.getAccountDistributor().getReceivable());
			row.add(dis.getAccountDistributor().getDonation());
			row.add(dis.getInfo_company_name());
			row.add(dis.getInfo_contact());
			row.add(dis.getInfo_tel());
			row.add(dis.getInfo_email());
			row.add(dis.getCreate_time());
			row.add(dis.getUpdate_time());
			row.add(dis.getUpdate_user());
			row.add(dis.getReport_url());
			row.add(dis.getKey());
			row.add(dis.getStatisticsDistributor().getConsumer_total());
			row.add(dis.getStatisticsDistributor().getConsumer_daily());
			row.add(dis.getStatisticsDistributor().getConsumer_monthly());
			row.add(dis.isEnabled());
			return 0;
		};
		NormallyExcelExporter<InfoDistributor> exporter = new NormallyExcelExporter<>(sheetname, headers, rowAction);
		try {
			return exporter.export(dataList);
		} catch (IOException e) {
			logger.error("导出渠道商列表错误",e);
			return null;
		}
		
	}

	public List<InfoDistributor> selectDistributorNames() {
		return infoDistributorWrap.selectDistributorNames();
	}

	public ByteArrayOutputStream exportFund(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		
		List<InfoDistributor> dataList = infoDistributorWrap.selectAll(bean);
		String sheetname = "渠道商消费列表";
		String[] headers = {"名称","渠道代码","余额","信用额","应收款","赠送额度",
				"总消费","当日消费","当月消费"};
		RowFunction<InfoDistributor> rowAction = (distributor,row) -> {
			InfoDistributorBean dis = (InfoDistributorBean)distributor;
			row.add(dis.getName());
			row.add(dis.getCode());
			row.add(dis.getAccountDistributor().getBanlance());
			row.add(dis.getAccountDistributor().getCredit());
			row.add(dis.getAccountDistributor().getReceivable());
			row.add(dis.getAccountDistributor().getDonation());
			row.add(dis.getStatisticsDistributor().getConsumer_total());
			row.add(dis.getStatisticsDistributor().getConsumer_daily());
			row.add(dis.getStatisticsDistributor().getConsumer_monthly());
			return 0;
		};
		NormallyExcelExporter<InfoDistributor> exporter = new NormallyExcelExporter<>(sheetname, headers, rowAction);
		try {
			return exporter.export(dataList);
		} catch (IOException e) {
			logger.error("导出渠道商列表错误",e);
			return null;
		}
		
	}
	
	/**
	 * 根据供应商名称查询供应商id
	 * @param name
	 * @return
	 */
	public int selectIDByName(String name){
		return infoDistributorWrap.selectIDByName(name);
	}

	/**
	 * 测试reportURL
	 * @param reportURL
	 * @return
	 * @throws MalformedURLException 
	 */
	public JEasyuiData testReportUrl(String reportURL) throws MalformedURLException {
		int status = 404;
		try{
			URL url = new URL(reportURL);
			HttpURLConnection oc = (HttpURLConnection) url.openConnection();
			oc.setUseCaches(false);
			oc.setConnectTimeout(3000); // 设置超时时间
			status = oc.getResponseCode();// 请求状态
			if (200 == status) {
				// 200是请求地址顺利连通。。
				return new JEasyuiData(true, "测试成功");
			}
	  	} catch (Exception e) {
	  		return new JEasyuiData(false, e.getMessage());
	  	}
		return new JEasyuiData(false, "测试失败");
	}

}
