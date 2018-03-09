package com.shziyuan.flow.irs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.common.Constant.RedisKey;
import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;
import com.shziyuan.flow.global.domain.flow.wraped.InfoDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.InfoSupplierCodetableBean;
import com.shziyuan.flow.global.util.JsonUtil;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.irs.dao.BindDistributorDao;
import com.shziyuan.flow.irs.dao.BindSupplierDao;
import com.shziyuan.flow.irs.dao.InfoDistributorDao;
import com.shziyuan.flow.irs.dao.InfoSkuDao;
import com.shziyuan.flow.irs.dao.InfoSupplierCodetableDao;
import com.shziyuan.flow.irs.dao.InfoSupplierDao;

@Service
public class BuildRedisService {
	@Autowired
	private BindDistributorDao bindDistributorDao;
	
	@Autowired
	private InfoDistributorDao infoDistributorDao;
	
	@Autowired
	private InfoSkuDao infoSkuDao;
	
	@Autowired
	private BindSupplierDao bindSupplierDao;
	
	@Autowired
	private InfoSupplierDao infoSupplierDao;
	
	@Autowired
	private InfoSupplierCodetableDao infoSupplierCodetableDao;
	
	@Autowired
	private InfoCitySubmobileService infoCitySubmobileService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	
	/**
	 * 加载所有渠道数据并缓存
	 */
	public void loadBindDistributor() {
		// 加载绑定关系
		List<BindDistributor> bindlist = bindDistributorDao.selectAll();
		// 加载渠道信息
		Map<String,InfoDistributorBean> disMap = infoDistributorDao.selectAll();
		// 缓存渠道信息
		redisTemplate.opsForHash().putAll(RedisKey.INFO_DIS_KEY.val, disMap);
		// 另存一份渠道(键名-code)信息
		Map<String,InfoDistributorBean> disMapByCode = disMap.values().stream().collect(Collectors.toMap(InfoDistributorBean::getCode, v->v));
		redisTemplate.opsForHash().putAll(RedisKey.INFO_DIS_BYCODE_KEY.val, disMapByCode);
		// 加载产品信息
		Map<String,InfoSku> skuMap = infoSkuDao.selectAll();
		// 缓存产品信息
		redisTemplate.opsForHash().putAll(RedisKey.INFO_SKU_KEY.val, skuMap);
		
		Map<String,BindDistributor> bindMap = bindlist.stream()
				.filter(bind -> checkBindKey(bind, disMap, skuMap))
				.collect(Collectors.toMap(
				bind -> getBindKey(bind, disMap, skuMap),
				bind -> bind));
		// 缓存绑定关系 键 {distributorCode}_{sku}
		redisTemplate.opsForHash().putAll(RedisKey.BIND_DIS_KEY.val, bindMap);
		
		// 打印加载情况日志
		LoggerUtil.sys.info("已加载到缓存的渠道绑定信息:");
		bindMap.entrySet().stream().forEach(entry -> {
			BindDistributor bind = entry.getValue();
			LoggerUtil.sys.info("{} - {}|{}|{}|{}",entry.getKey(),bind.getDistributor_id(),bind.getPid(),bind.getDiscount(),bind.getPrice());
		});
	}
	
	/**
	 * 加载所有供应商信息并缓存
	 */
	public void loadBindSupplier() {
		// 加载绑定关系
		List<BindSupplier> bindListNormal = bindSupplierDao.selectAllNormal();
		List<BindSupplier> bindListPresent = bindSupplierDao.selectAllPresent();
		// 分组绑定
		Map<Integer,List<BindSupplier>> groupBindSupplierNormal = groupBindSupplier(bindListNormal);
		Map<Integer,List<BindSupplier>> groupBindSupplierPresent = groupBindSupplier(bindListPresent);
		// 加载供应商信息
		Map<String,InfoSupplier> supMap = infoSupplierDao.selectAll();
		// 缓存供应商信息
		redisTemplate.opsForHash().putAll(RedisKey.INFO_SUPPLIER_KEY.val, supMap);
		// 缓存供应商接口参数
		buildSupplierAttrMap(supMap);
		// 加载供应商产品信息
		Map<String,InfoSupplierCodetable> codeMap = infoSupplierCodetableDao.selectAll();
		// 缓存供应商产品信息
		redisTemplate.opsForHash().putAll(RedisKey.INFO_SUPPLIER_CODE_KEY.val, codeMap);
		// 计算转换绑定关系		
		Map<String,BindSupplierBean> bindMapNormal = convertBindSupplier(groupBindSupplierNormal, supMap, codeMap);
		Map<String,BindSupplierBean> bindMapPresent = convertBindSupplier(groupBindSupplierPresent, supMap, codeMap);
		
		// 缓存绑定关系 键 {sku_id}
		redisTemplate.opsForHash().putAll(RedisKey.BIND_SUPPLIER_NORMAL_KEY.val, bindMapNormal);
		redisTemplate.opsForHash().putAll(RedisKey.BIND_SUPPLIER_PRESENT_KEY.val, bindMapPresent);
		
		// 打印加载情况日志
		LoggerUtil.sys.info("已加载到缓存的供应商绑定信息:");
		bindMapNormal.entrySet().stream().forEach(entry -> {
			BindSupplierBean bind = entry.getValue();
			LoggerUtil.sys.info("{} ->",entry.getKey());
			bind.getCodeList().forEach(code -> 
				LoggerUtil.sys.info("\t{}|{}|{}|{}",code.getName(),code.getCode(),code.getDiscount(),code.getPrice())
					);
		});
		bindMapPresent.entrySet().stream().forEach(entry -> {
			BindSupplierBean bind = entry.getValue();
			LoggerUtil.sys.info("{} ->",entry.getKey());
			bind.getCodeList().forEach(code -> 
				LoggerUtil.sys.info("\t{}|{}|{}|{}",code.getName(),code.getCode(),code.getDiscount(),code.getPrice())
					);
		});
	}
	
	private void buildSupplierAttrMap(Map<String,InfoSupplier> supMap) {
		
		Map<String,?> attrMaps = supMap.entrySet().stream().collect(Collectors.toMap(
				e -> e.getKey(), 
				e -> {
					InfoSupplier sup = e.getValue();
					String attrStr = sup.getIf_attribute();
					if(attrStr != null && !attrStr.isEmpty()) {
						try {
							Map<String,Object> attrMap = JsonUtil.parse(attrStr, Map.class);
							return attrMap;
						} catch (IOException ex) {
							LoggerUtil.error.error("解析供应商接口参数失败 供应商:{}-{} 源数据:{} 异常:{}-{}",sup.getId(),sup.getName(),attrStr,ex.getMessage(),ex.getStackTrace()[0]);
							return new HashMap<String,Object>(0);
						}
					} else 
						return new HashMap<String,Object>(0);
				}
			)
		);
		
		redisTemplate.opsForHash().putAll(RedisKey.INFO_SUPPLIER_KEY_ATTRMAP.val, attrMaps);
	}
	
	public void loadBindSupplierByChangeSupplier(int supplierId) {
		// 加载绑定关系
		List<BindSupplier> bindListNormal = bindSupplierDao.selectNormalByChangeSupplier(supplierId);
		List<BindSupplier> bindListPresent = bindSupplierDao.selectPresentByChangeSupplier(supplierId);
		// 分组绑定
		Map<Integer,List<BindSupplier>> groupBindSupplierNormal = groupBindSupplier(bindListNormal);
		Map<Integer,List<BindSupplier>> groupBindSupplierPresent = groupBindSupplier(bindListPresent);
		// 加载供应商信息
		InfoSupplier newSupplier = infoSupplierDao.selectById(supplierId);
		Map<String,InfoSupplier> supMap = new HashMap<>(1);
		supMap.put(Integer.toString(supplierId), newSupplier);
		buildSupplierAttrMap(supMap);
		// 缓存供应商信息
		redisTemplate.opsForHash().put(RedisKey.INFO_SUPPLIER_KEY.val,Integer.toString(supplierId), newSupplier);
		
		Map<String,InfoSupplierCodetable> codeMap = redisTemplate.opsForHash().entries(RedisKey.INFO_SUPPLIER_CODE_KEY.val)
				.entrySet().stream().collect(Collectors.toMap(e->(String)e.getKey(), e->(InfoSupplierCodetable)e.getValue()));
		// 计算转换绑定关系		
		Map<String,BindSupplierBean> bindMapNormal = convertBindSupplier(groupBindSupplierNormal, supMap, codeMap);
		Map<String,BindSupplierBean> bindMapPresent = convertBindSupplier(groupBindSupplierPresent, supMap, codeMap);
		
		// 缓存绑定关系 键 {sku_id}
		redisTemplate.opsForHash().putAll(RedisKey.BIND_SUPPLIER_NORMAL_KEY.val, bindMapNormal);
		redisTemplate.opsForHash().putAll(RedisKey.BIND_SUPPLIER_PRESENT_KEY.val, bindMapPresent);
		
		// 打印加载情况日志
		LoggerUtil.sys.info("已加载到缓存的供应商绑定信息:");
		bindMapNormal.entrySet().stream().forEach(entry -> {
			BindSupplierBean bind = entry.getValue();
			LoggerUtil.sys.info("{} ->",entry.getKey());
			bind.getCodeList().forEach(code -> 
				LoggerUtil.sys.info("\t{}|{}|{}|{}",code.getName(),code.getCode(),code.getDiscount(),code.getPrice())
					);
		});
		bindMapPresent.entrySet().stream().forEach(entry -> {
			BindSupplierBean bind = entry.getValue();
			LoggerUtil.sys.info("{} ->",entry.getKey());
			bind.getCodeList().forEach(code -> 
				LoggerUtil.sys.info("\t{}|{}|{}|{}",code.getName(),code.getCode(),code.getDiscount(),code.getPrice())
					);
		});
	}
	
	/**
	 * 检查Redis是否已经建立了submobile数据,没有则建立
	 */
	public void checkAndBuildRedisFirst() {
		if(!redisTemplate.hasKey(RedisKey.INFO_CITY_SUBMOBILE_KEY.val))
			infoCitySubmobileService.buildRedis();
	}

	private Map<Integer,List<BindSupplier>> groupBindSupplier(List<BindSupplier> bindList) {
		Map<Integer,List<BindSupplier>> map = new HashMap<>(100);
		// 遍历绑定表
		for(BindSupplier bind : bindList) {
			List<BindSupplier> bindBySku = map.get(bind.getSku_id());
			if(bindBySku == null) {
				bindBySku = new ArrayList<>(20);
				map.put(bind.getSku_id(), bindBySku);
			}
			// 添加数据到 针对skuid的列表
			bindBySku.add(bind);
		}
		// 排序列表
		Map<Integer,List<BindSupplier>> sortMap = new HashMap<>(map.size());
		Comparator<BindSupplier> comparator = new Comparator<BindSupplier>() {
			@Override
			public int compare(BindSupplier o1, BindSupplier o2) {
				if(o1.getSorting() > o2.getSorting())
					return 1;
				else
					return -1;
			}
		};
		for(Entry<Integer,List<BindSupplier>> entry : map.entrySet()) {
			List<BindSupplier> list = entry.getValue();
			Collections.sort(list, comparator);
			sortMap.put(entry.getKey(), list);
		}
		
		return sortMap;
	}
	private Map<String,BindSupplierBean> convertBindSupplier(Map<Integer,List<BindSupplier>> groupBindSupplier,
			Map<String,InfoSupplier> supMap,Map<String,InfoSupplierCodetable> codeMap) {
		Map<String,BindSupplierBean> map = new HashMap<>(groupBindSupplier.size());
		// 遍历已skuid分组的列表
		for(Entry<Integer,List<BindSupplier>> entry : groupBindSupplier.entrySet()) {
			// 此skuid下的绑定关系
			List<BindSupplier> list = entry.getValue();
			// 建立绑定对象
			BindSupplierBean bean = new BindSupplierBean();
			// 绑定的skuid
			bean.setSku_id(entry.getKey());
			// 转换绑定关系到供应商产品列表
			List<InfoSupplierCodetableBean> codelist = list.stream()
				.filter(bind -> checkCode(bind,supMap,codeMap))	
				.map(bind -> {
					InfoSupplierCodetableBean code = new InfoSupplierCodetableBean(codeMap.get(Integer.toString(bind.getSupplier_code_id())));
					code.setSupplier(supMap.get(Integer.toString(bind.getSupplier_id())));
					code.setBindEnabled(bind.isEnabled());
					return code;
				}).collect(Collectors.toList());
			bean.setCodeList(codelist);
			// 加入数据对象
			map.put(entry.getKey().toString(), bean);
		}
		return map;
	}
	
	public void resetBindDistributor() {
		// 加载绑定关系
		List<BindDistributor> bindlist = bindDistributorDao.selectAll();
		
		Map<String,BindDistributor> bindMap = bindlist.stream().collect(Collectors.toMap(
				bind -> {
					InfoDistributorBean dis = (InfoDistributorBean) redisTemplate.opsForHash().get(RedisKey.INFO_DIS_KEY.val, Integer.toString(bind.getDistributor_id()));
					InfoSku sku = (InfoSku) redisTemplate.opsForHash().get(RedisKey.INFO_SKU_KEY.val, Integer.toString(bind.getPid()));
					return dis.getCode() + "_" + sku.getSku();
				},
				bind -> bind));
		
		redisTemplate.opsForHash().putAll(RedisKey.BIND_DIS_KEY.val, bindMap);
	}
	
	private boolean checkCode(BindSupplier bind,Map<String,InfoSupplier> supMap,Map<String,InfoSupplierCodetable> codeMap) {
		boolean hasKey = codeMap.containsKey(Integer.toString(bind.getSupplier_code_id())) 
				&& supMap.containsKey(Integer.toString(bind.getSupplier_id()));
		if(!hasKey) {
			LoggerUtil.error.error("警告: 供应商ID:{} 供应商产品ID:{} SKUID:{} 获取渠道绑定数据失败,检查配置表",bind.getSupplier_id(),bind.getSupplier_code_id(),bind.getSku_id());
		}
		return hasKey;
	}
	private boolean checkBindKey(BindDistributor bind,Map<String,InfoDistributorBean> disMap,Map<String,InfoSku> skuMap) {
		boolean hasKey = disMap.containsKey(Integer.toString(bind.getDistributor_id())) && skuMap.containsKey(Integer.toString(bind.getPid()));
		if(!hasKey) {
			LoggerUtil.error.error("警告: 渠道ID:{} SKUID:{} 获取渠道绑定数据失败,检查配置表",bind.getDistributor_id(),bind.getPid());
		}
		return hasKey;
	}
	private String getBindKey(BindDistributor bind,Map<String,InfoDistributorBean> disMap,Map<String,InfoSku> skuMap) {
		try {
			return disMap.get(Integer.toString(bind.getDistributor_id())).getCode() + "_" + skuMap.get(Integer.toString(bind.getPid())).getSku();
		} catch (Exception e) {
			LoggerUtil.error.error("警告: 渠道ID:{} SKUID:{} 获取渠道绑定数据失败,检查配置表",bind.getDistributor_id(),bind.getPid());
			throw e;
		}
	}
	
}
