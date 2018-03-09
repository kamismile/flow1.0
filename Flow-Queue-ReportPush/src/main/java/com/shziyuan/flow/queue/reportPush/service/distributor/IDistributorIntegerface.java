package com.shziyuan.flow.queue.reportPush.service.distributor;

import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.Order;
import com.shziyuan.flow.queue.base.interactive.BaseInterfaceRequestResponse;
import com.shziyuan.flow.queue.reportPush.domain.DistributorPush;

public interface IDistributorIntegerface {
	public BaseInterfaceRequestResponse push(Order order,DistributorPush pushData,InfoDistributor distributor);
}
