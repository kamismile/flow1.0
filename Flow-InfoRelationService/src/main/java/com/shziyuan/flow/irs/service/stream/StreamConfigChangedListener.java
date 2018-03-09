package com.shziyuan.flow.irs.service.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.shziyuan.flow.irs.service.BindSupplierService;
import com.shziyuan.flow.mq.stream.consumer.StreamConfigInputService;

@Service
public class StreamConfigChangedListener extends StreamConfigInputService{

	@Autowired
	private BindSupplierService bindSupplierService;

	@Override
	public String showQueueName() {
		return "[IRS]配置更新监听器";
	}

	@Override
	protected void doChangeSupplier(ConfigRefreshDomain domain) {
		if(domain.getIds() == null) {
			bindSupplierService.reloadSupplier(domain.getId(),true);
		} else {
			for(int id : domain.getIds()) {
				bindSupplierService.reloadSupplier(id,true);
			}
		}
	}

	@Override
	protected void doChangeSuppliercode(ConfigRefreshDomain domain) {
		System.out.println("do suppliercode");
		System.out.println(domain);
	}

	@Override
	protected void doChangeDistributor(ConfigRefreshDomain domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doChangeSku(ConfigRefreshDomain domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doChangeBindSupplier(ConfigRefreshDomain domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doChangeBindDistributor(ConfigRefreshDomain domain) {
		// TODO Auto-generated method stub
		
	}

	
//	@Override
//	public void onCfgSupplier(ConfigRefreshDomain domain) {
//		if(domain.getIds() == null) {
//			bindSupplierService.reloadSupplier(domain.getId(),true);
//		} else {
//			for(int id : domain.getIds()) {
//				bindSupplierService.reloadSupplier(id,true);
//			}
//		}
//	}

}
