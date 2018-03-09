package com.ziyuan.web.manager.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributor;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.conf.security.InMemoryOAuthParam;
import com.ziyuan.web.manager.feign.AccountFeign;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.wrap.AccountDistributorWrap;
import com.ziyuan.web.manager.wrap.LogAccountDistributorWrap;
import com.ziyuan.web.manager.wrap.PendingAccountDistirbutorWrap;




@Service
public class AccountingDistributorService {
	private Logger logger = LoggerFactory.getLogger(AccountingDistributorService.class);
	
	@Resource
	private AccountDistributorWrap accountDistributorWrap;
	
	@Resource
	private PendingAccountDistirbutorWrap pendingAccountDistirbutorWrap;
	
	@Resource
	private LogAccountDistributorWrap logAccountDistributorWrap;
	
	public static final int FIELD_BANLANCE = 0;
	public static final int FIELD_CREDIT = 1;
	public static final int FIELD_DONATION = 2;
	public JEasyuiData selectLog(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return logAccountDistributorWrap.select(bean);
	}
	
	/**
	 * 待审核数据
	 */
	public JEasyuiData pendingInsert(Principal user,PendingAccountDistributor domain,int field) {
		
		try {
			domain.setUpdate_user(user.getName());
			domain.setInsert_time(TimestampUtil.now());
			domain.setField(field);
			pendingAccountDistirbutorWrap.insert(domain);
			return new JEasyuiData(domain);
		} catch (RuntimeException e) {
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
	public JEasyuiData pendingSelect(JEasyuiRequestBean bean) {
		return pendingAccountDistirbutorWrap.select(bean);
	}
	public JEasyuiData pendingVerify(Principal user,int pending_id,boolean ispass) {
		
		PendingAccountDistributor pending = pendingAccountDistirbutorWrap.selectOne(pending_id);
		AccountDistributor result;
		try {
			if(pending.getField() == 0)
				result = accountDistributorWrap.addFunds(user.getName(), pending, ispass);
			else if(pending.getField() == 1)
				result = accountDistributorWrap.addCredit(user.getName(), pending, ispass);
			else
				result = accountDistributorWrap.addDonation(user.getName(), pending, ispass);
		
			return new JEasyuiData(result);
		} catch (RuntimeException e) {
			return new JEasyuiData(false, e.getMessage());
		}
	}

	public ByteArrayOutputStream export(JEasyuiRequestBean bean) {
		//使用从数据库
		//DynamicDataSourceHolder.useSlave();
		//从数据库获取对应的数据
		//LogAccountDistributor表示从数据库中查询出来的数据的保存bean
		List<LogAccountDistributor> datalist = logAccountDistributorWrap.export(bean);
		
		//设置sheetname
		String sheetname = "渠道商加款日志";
		//设置每一列的标题
		String[] header = {"操作时间","渠道商ID","渠道商名称","更新人","更新前余额","更新前信用额",
					"更新前待付款","更新前赠送额","更新后余额","更新后信用额","更新后待付款","更新后赠送额","变动额度","变动原因","备注"};
		//用java8中的新特性实现RowFunction接口的apply方法
		RowFunction<LogAccountDistributor> rowAction = (log,row) -> {
			row.add(log.getInsert_time());
			row.add(log.getDistributor_id());
			row.add(log.getDistributor_name());
			row.add(log.getUpdate_user());
			row.add(log.getBefore_banlance());
			row.add(log.getBefore_credit());
			row.add(log.getBefore_receivable());
			row.add(log.getBefore_donation());
			row.add(log.getAfter_banlance());
			row.add(log.getAfter_credit());
			row.add(log.getAfter_receivable());
			row.add(log.getAfter_donation());
			row.add(log.getPrice());
			row.add(log.getAction());
			row.add(log.getRemark());
			return 0;
		};
		
		NormallyExcelExporter<LogAccountDistributor> exporter = new NormallyExcelExporter<>(sheetname, header, rowAction);
		try {
			//调用NormallyExcelExporter的export方法将数据转换为流
			return exporter.export(datalist);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
}
