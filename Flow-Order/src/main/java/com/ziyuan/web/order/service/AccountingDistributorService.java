package com.ziyuan.web.order.service;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.domain.flow.PendingAccountDistributor;

public interface AccountingDistributorService {

	ActionResponse pendingInsert(String user, int field, PendingAccountDistributor domain);
}
