package com.ziyuan.web.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.ziyuan.web.manager.domain.InfoConstant;
import com.ziyuan.web.manager.wrap.BindDistributorWrap;
import com.ziyuan.web.manager.wrap.BindSupplierPresentWrap;
import com.ziyuan.web.manager.wrap.BindSupplierWrap;
import com.ziyuan.web.manager.wrap.InfoConstantWrap;

/**
 * 检查绑定关系
 * @author user
 *
 */
@Service
public class CheckBindRelationService {
	
	@Autowired
	private BindDistributorWrap bindDistributorWrap;
	
	@Autowired
	private BindSupplierWrap bindSupplierWrap;
	
	@Autowired
	private InfoConstantWrap infoConstantWrap;
	
	@Autowired
	private BindSupplierPresentWrap bindSupplierPresentWrap;

	public JEasyuiData check() {
		//检查bind_distributor表是否有无效数据
		boolean bindDistributorIsNUll = bindDistributorWrap.checkNull();
		//检查bind_supplier表是否有无效数据
		boolean bindSupplierIsNull = bindSupplierWrap.checkNull();
		//检查bind_supplier_present表是否有无效数据
		boolean bindSupplierPresentIsNull = bindSupplierPresentWrap.checkNull();
		//检查bind_distributor表是否有重复数据
		boolean bindDistributorRepetition = bindDistributorWrap.checkRepetion();
		//检查bind_supplier表是否有重复数据
		boolean bindSupplierRepetion = bindSupplierWrap.checkRepetion();
		//检查bind_supplier_present表是否有重复数据
		boolean bindSupplierPresentRepetion = bindSupplierPresentWrap.checkRepetion();
		
		List<String> list = new ArrayList<>();
		
		if(bindDistributorIsNUll){
			list.add("渠道绑定产品存在无效数据,请联系技术部门");
		}
		if(bindSupplierIsNull){
			list.add("产品绑定普通供应商产品存在无效数据,请联系技术部门");
		}
		if(bindSupplierPresentIsNull){
			list.add("产品绑定赠送供应商产品存在无效数据,请联系技术部门");
		}
		if(bindDistributorRepetition){
			list.add("渠道绑定产品存在重复数据,请联系技术部门");
		}
		if(bindSupplierRepetion){
			list.add("产品绑定普通供应商产品存在重复数据,请联系技术部门");
		}
		if(bindSupplierPresentRepetion){
			list.add("产品绑定赠送供应商产品存在重复数据,请联系技术部门");
		}
		if(list.size() == 0){
			list.add("没有无效数据");
		}
		return new JEasyuiData(list);
	}

	public JEasyuiData getSkuType() {
		List<InfoConstant> list = infoConstantWrap.selectSkuType();
		return new JEasyuiData(list);
	}

}
