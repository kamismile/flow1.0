package com.ziyuan.web.manager.domain;

import java.util.List;

import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.StatisticsDistributor;
import com.shziyuan.flow.global.domain.flow.WebAccountDistributorAuthority;

public class InfoDistributorBean extends InfoDistributor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5086631070177424418L;
	// 账务信息
    private AccountDistributor accountDistributor;
    private StatisticsDistributor statisticsDistributor;
    private List<NoticeDistributorBean> noticeDistributorBean;
    private List<WebAccountDistributorAuthority> authority;
    
	public AccountDistributor getAccountDistributor() {
		return accountDistributor;
	}
	public void setAccountDistributor(AccountDistributor accountDistributor) {
		this.accountDistributor = accountDistributor;
	}
	public StatisticsDistributor getStatisticsDistributor() {
		return statisticsDistributor;
	}
	public void setStatisticsDistributor(StatisticsDistributor statisticsDistributor) {
		this.statisticsDistributor = statisticsDistributor;
	}
	public List<WebAccountDistributorAuthority> getAuthority() {
		return authority;
	}
	public void setAuthority(List<WebAccountDistributorAuthority> authority) {
		this.authority = authority;
	}
	public List<NoticeDistributorBean> getNoticeDistributorBean() {
		return noticeDistributorBean;
	}
	public void setNoticeDistributorBean(List<NoticeDistributorBean> noticeDistributorBean) {
		this.noticeDistributorBean = noticeDistributorBean;
	}
	
}
