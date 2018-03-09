package com.ziyuan.web.manager.service.ext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant;
import com.shziyuan.flow.global.domain.flow.AccountDistributorPresentRole;
import com.shziyuan.flow.global.domain.flow.LogAccountDistributorPresent;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.wrap.AccountDistributorPresentRoleWrap;
import com.ziyuan.web.manager.wrap.AccountDistributorWrap;
import com.ziyuan.web.manager.wrap.LogAccountDistributorPresentWrap;
import com.ziyuan.web.manager.wrap.OrderWrap;



@Service
public class ExtAutoPresentService {
	@Resource
	private OrderWrap orderWrap;
	
	@Resource
	private AccountDistributorPresentRoleWrap accountDistributorPresentRoleWrap;
	
	@Resource
	private AccountDistributorWrap accountDistributorWrap;
	
	@Resource
	private LogAccountDistributorPresentWrap logAccountDistributorPresentWrap;
	
	private DateTimeFormatter monthFirstDayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-01 00:00:00");
	
	public JEasyuiData autoPresent() {
		Calendar now = Calendar.getInstance();
		Timestamp insert_time = new Timestamp(now.getTimeInMillis());
		String year = Integer.toString(now.get(Calendar.YEAR));
		String month = Integer.toString(now.get(Calendar.MONTH) + 1);
		// TODO 检查是否已加款
		//DynamicDataSourceHolder.useSlave();
		int alreadyCount = logAccountDistributorPresentWrap.selectAlreadyPresent(year, month);

		// TODO 自动加款
		if(alreadyCount == 0)
			return chargingPresent(Constant.DEFAULT_SYSTEM_USER,insert_time,year,month);
		else
			return new JEasyuiData(false, "此月额度已加款");
	}
	
	public JEasyuiData chargingPresent(String update_user,Timestamp insert_time,String year,String month) {
		//DynamicDataSourceHolder.useSlave();
		
		LocalDateTime starttime = LocalDateTime.now().minusMonths(1);
		LocalDateTime endtime = LocalDateTime.now();
		
		List<Map<String,Object>> sumList = orderWrap
				.selectOrderDistributorPrice(starttime.format(monthFirstDayFormatter), endtime.format(monthFirstDayFormatter));
		
		List<AccountDistributorPresentRole> roles = accountDistributorPresentRoleWrap.selectOrderDesc();
		
		List<LogAccountDistributorPresent> results = new ArrayList<>(sumList.size());
		
		Calendar now = Calendar.getInstance();
		
		sumList.forEach(sum -> {
			LogAccountDistributorPresent present = presentCheck((BigDecimal) sum.get("price"),roles);
			if(present != null) {
				present.setDistributor_id(((Long) sum.get("distributor_id")).intValue());
//				present.setDistributor_name(distributor_name);
				
				present.setInsert_time(new Timestamp(now.getTimeInMillis()));
				present.setMonth(Integer.toString(now.get(Calendar.MONTH) + 1));
				present.setYear(Integer.toString(now.get(Calendar.YEAR)));
				present.setUpdate_user(update_user);
				
				results.add(present);
			}
		});
		
		
		accountDistributorWrap.autoPresent(results);
		
		
		return new JEasyuiData(true);
	}
	
	private LogAccountDistributorPresent presentCheck(BigDecimal sumPrice,List<AccountDistributorPresentRole> roles) {
		for(AccountDistributorPresentRole role : roles) {
			if(sumPrice.doubleValue() >= role.getConsumer()) {
				LogAccountDistributorPresent present = new LogAccountDistributorPresent();
				present.setConsumer(sumPrice.doubleValue());
				present.setPresent(present.getConsumer() * (1-role.getPresent()));
				return present;
			}
		}
		
		return null;
	}
}
