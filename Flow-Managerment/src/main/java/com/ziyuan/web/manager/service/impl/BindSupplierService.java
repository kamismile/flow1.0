package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.producer.StreamConfigOutputService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.CacheDefine;
import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.ziyuan.web.manager.dao.BindSupplierMapper;
import com.ziyuan.web.manager.domain.BindSupplierChangeBean;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.wrap.BindSupplierPresentWrap;
import com.ziyuan.web.manager.wrap.BindSupplierWrap;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoSkuWrap;
import com.ziyuan.web.manager.wrap.InfoSupplierCodetableWrap;



@Service("BindSupplierService")
public class BindSupplierService extends AbstractCRUDService<BindSupplier> {

	@Resource
	private BindSupplierWrap bindSupplierWrap;
	
	@Resource
	private BindSupplierPresentWrap bindSupplierPresentWrap;
	
	@Resource
	private InfoSkuWrap infoSkuWrap;
	
	@Resource
	private InfoSupplierCodetableWrap infoSupplierCodetableWrap;
	
	@Autowired
	private StreamConfigOutputService streamConfigOutputService;
	
	@Override
	public ICRUDWrap<BindSupplier> getWrap() {
		return bindSupplierWrap;
	}
		
	@Override
	protected String getMQCahceName() {
		return FlowMqDefine.CHANGE_BIND_SUPPLIER;
	}
	
	public JEasyuiData selectCodetable(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		InfoSku sku = infoSkuWrap.selectOne(Integer.parseInt(bean.getParam().get("sku_id")));
		bean.getParam().put("sku_provinceid", sku.getProvinceid()+"");
		bean.getParam().put("sku_operator", sku.getOperator());
		bean.getParam().put("sku_pkgsize", sku.getPkgsize()+"");
		bean.getParam().put("sku_type", sku.getSku_type()+"");
		bean.getParam().put("rent_type", sku.getRent_type()+"");
		return bindSupplierWrap.selectCodetable(bean);
	}
	
	public JEasyuiData selectBindedSorting(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindSupplierWrap.selectBindedSorting(bean);
	}

	public JEasyuiData changeBinding(Principal user, BindSupplierChangeBean data) {
		
		ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
		
		List<BindSupplier> newlist = data.getNewBind();
		List<BindSupplier> editlist = data.getEditBind();
		List<BindSupplier> removelist = data.getRemoveBind();

		newlist.forEach(bind -> bind.setEnabled(true));
		
		if(removelist.size() > 0){
			LoggerUtil.request.info("用户[{}], 进行了产品绑定供应商产品-删除操作, 服务类[{}], 数据[{}]", user.getName(), getClass(), data.getRemoveBind());
			
			for (BindSupplier bindSupplierList : removelist) {
				//修改应为删除绑定关系而造成的的供应商排序混乱
				BindSupplier bindBean = bindSupplierWrap.selectById(bindSupplierList.getId());
				List<BindSupplier> bindList = bindSupplierWrap.selectBySkuId(bindBean.getSku_id());
				for (BindSupplier bindSupplier : bindList) {
					if(bindSupplier.getSorting() > bindBean.getSorting()){
						bindSupplier.setSorting(bindSupplier.getSorting() - 1);
						bindSupplierWrap.updateSortingById(bindSupplier);
					}
				}
			}
			
			bindSupplierWrap.batchDelete(BindSupplierMapper.class.getName(),data.getRemoveBind(),(domain) -> domain.getId());
			List<Integer> ids = new ArrayList<>();
			for (BindSupplier remove : removelist) {
				ids.add(remove.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindSupplier(configRefreshDomain);
		}
		if(editlist.size() > 0){
			LoggerUtil.request.info("用户[{}], 进行了产品绑定供应商产品-编辑操作, 服务类[{}], 数据[{}]", user.getName(), getClass(), editlist);
			bindSupplierWrap.batchUpdate(BindSupplierMapper.class.getName(),editlist);
			List<Integer> ids = new ArrayList<>();
			for (BindSupplier edit : editlist) {
				ids.add(edit.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindSupplier(configRefreshDomain);
		}
		if(newlist.size() > 0){
			LoggerUtil.request.info("用户[{}], 进行了产品绑定供应商产品-新建操作, 服务类[{}], 数据[{}]", user.getName(), getClass(), newlist);
			bindSupplierWrap.batchInsert(BindSupplierMapper.class.getName(),newlist);
			List<Integer> ids = new ArrayList<>();
			for (BindSupplier list : newlist) {
				ids.add(list.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindSupplier(configRefreshDomain);
		}
		
		return new JEasyuiData(true,"");
	}
	
	public JEasyuiData batchChangeBinding(JEasyuiRequestBean param) {
		
		
		List<Integer> ids = Arrays.stream(param.getParam().get("ids").split(","))
				.map(id -> Integer.valueOf(id))
				.collect(Collectors.toList());
		int supplier_id_first = Integer.parseInt(param.getParam().get("supplier_id_first"));
		int supplier_id_second = Integer.parseInt(param.getParam().get("supplier_id_second"));
		
		// 获取所有对应sku的数据
		List<InfoSku> skuList = infoSkuWrap.selectByIds(ids);
		// 获取供应商对应产品
		JEasyuiRequestBean param_first = new JEasyuiRequestBean(1);
		param_first.getParam().put("supplier_id", Integer.toString(supplier_id_first));
		List<InfoSupplierCodetable> codeFirstList = infoSupplierCodetableWrap.selectAll(param_first);
		
		List<Map<String,Object>> changes_first = filterBatchChangeBindingList(skuList, codeFirstList,"优先");
		JEasyuiData result_first = bindSupplierWrap.batchChangeBinding(changes_first,true);
		
		if(result_first.isSuccess() && supplier_id_second > 0) {
			JEasyuiRequestBean param_second = new JEasyuiRequestBean(1);
			param_second.getParam().put("supplier_id", Integer.toString(supplier_id_second));
			List<InfoSupplierCodetable> codeSecondList = infoSupplierCodetableWrap.selectAll(param_second);
			
			List<Map<String,Object>> changes_second = filterBatchChangeBindingList(skuList, codeSecondList,"备用");
			JEasyuiData result_second = bindSupplierWrap.batchChangeBinding(changes_second,false);
			if(!result_second.isSuccess())
				result_first.setSuccess(false);
			((List<Map<String,Object>>)result_first.getData()).addAll((List<Map<String,Object>>)result_second.getData());
			
			sendMQ();
			return result_first;
		} else {
			sendMQ();
			return result_first;
		} 
	}
	
	private List<Map<String,Object>> filterBatchChangeBindingList(List<InfoSku> skuList,List<InfoSupplierCodetable> codeList,String mark) {
		// MAP<名称,值>
		List<Map<String,Object>> results = new ArrayList<>(skuList.size());
		
		final String KEY_RET = "success";
		final String KEY_MSG = "errorMsg";
		final String KEY_SKU = "sku";
		final String KEY_CODE = "code";
		
		// 便利sku表,筛选出sku和code相同参数的数据
		for(InfoSku sku : skuList) {
			Stream<InfoSupplierCodetable> stream = 
			codeList.stream().filter(code -> {
				return code.getProvinceid() == sku.getProvinceid() &&
						code.getOperator() != null && code.getOperator().equals(sku.getOperator()) &&
						code.getScope_cid() == sku.getScope_cid() &&
						code.getPkgsize() == sku.getPkgsize() &&
						code.getInfo_ptype() != null && code.getInfo_ptype().equals(sku.getInfo_ptype()) &&
						code.getRent_type() == sku.getRent_type();
				});
			
			Map<String,Object> ret = new HashMap<>(3);
			InfoSupplierCodetable sameCode;
			try {
				sameCode = stream.findFirst().get();
			} catch (Exception e) {
				ret.put(KEY_RET,false);
				ret.put(KEY_MSG, mark + "供应商下,找不到匹配产品");
				ret.put(KEY_SKU, sku);
				results.add(ret);
				continue;
			}
			
			ret.put(KEY_RET, true);
			ret.put(KEY_SKU, sku);
			ret.put(KEY_CODE, sameCode);
			results.add(ret);
		};
		
		return results;
	}

	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * TREE
	 */
	public JEasyuiData selectTreeBySupplier() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(bindSupplierWrap.selectTreeBySupplier());
	}
	public JEasyuiData selectTreeBySku() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(bindSupplierWrap.selectTreeBySku());
	}
	
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindSupplierWrap.selectBind2_binded(bean);
	}
	
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindSupplierWrap.selectBind2_nonbinded(bean);
	}
	
	
	public JEasyuiData selectPresent_binded(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		InfoSku sku = infoSkuWrap.selectOne(Integer.parseInt(bean.getParam().get("sku_id")));
		bean.getParam().put("sku_provinceid", sku.getProvinceid()+"");
		bean.getParam().put("sku_operator", sku.getOperator());
		bean.getParam().put("sku_pkgsize", sku.getPkgsize()+"");
		bean.getParam().put("sku_type", sku.getSku_type()+"");
		bean.getParam().put("rent_type", sku.getRent_type()+"");
		return bindSupplierPresentWrap.selectBind2_binded(bean);
	}
	
	public JEasyuiData selectPresent_nonbinded(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindSupplierPresentWrap.selectBind2_nonbinded(bean);
	}
	
	public JEasyuiData changePresent(BindSupplierChangeBean data) {
		
		ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
		
		List<BindSupplier> newlist = data.getNewBind();
		List<BindSupplier> removeBind = data.getRemoveBind();
		newlist.forEach(bind -> bind.setEnabled(true));
		
		bindSupplierPresentWrap.bindChange(newlist, removeBind);
		
		if(newlist.size() > 0){
			List<Integer> ids = new ArrayList<>();
			for (BindSupplier list : newlist) {
				ids.add(list.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindDistributor(configRefreshDomain);
		}
		if(removeBind.size() > 0){
			List<Integer> ids = new ArrayList<>();
			for (BindSupplier remove : removeBind) {
				ids.add(remove.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindDistributor(configRefreshDomain);
		}
		
		return new JEasyuiData(true,"");
	}
}
