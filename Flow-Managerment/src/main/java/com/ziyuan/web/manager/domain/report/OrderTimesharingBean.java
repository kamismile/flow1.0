package com.ziyuan.web.manager.domain.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class OrderTimesharingBean extends LinkedHashMap<String, OrderTimesharingBeanDay> implements Serializable{
	private static final long serialVersionUID = 1L;

	public void add(OrderTimesharingResultset resultset) {
		OrderTimesharingBeanDay day = get(resultset.getP_day());
		if(day == null) {
			day = new OrderTimesharingBeanDay();
			put(resultset.getP_day(), day);
		}
		
		day.forEach(hour -> {
			Integer resultHore = resultset.getP_hour();
			if(resultHore < hour.getFlag()) {
				AtomicDouble price = hour.get(resultset.getDistributor_name());
				if(price == null) {
					price = new AtomicDouble(resultset.getPrice());
					hour.put(resultset.getDistributor_name(), price);
				} else {
					price.addAndGet(resultset.getPrice());
				}
			}
		});
	}
	
	public List<OrderTimesharingBeanReturn> toList() {
		List<OrderTimesharingBeanReturn> list = new ArrayList<>(entrySet().size() * 4);
		
		// 收集省份map
		Map<String,Double> provinceMap = new HashMap<>();
		values().forEach(day -> {
			day.forEach(hour -> {
				hour.keySet().forEach(province -> {
					if(!provinceMap.containsKey(province))
						provinceMap.put(province,null);
				});
			});
		});
		for(Entry<String,OrderTimesharingBeanDay> dayEntry : entrySet()) {
			for(OrderTimesharingBeanHour hour : dayEntry.getValue()) {
				Map<String,Double> inlineProvinceMap = cloneMap(provinceMap);
				hour.entrySet().forEach(hourEntry -> inlineProvinceMap.put(hourEntry.getKey(), hourEntry.getValue().get()));
				OrderTimesharingBeanReturn returnBean = new OrderTimesharingBeanReturn();
				returnBean.setDay(dayEntry.getKey());
				returnBean.setHour(hour.getFlag());
				returnBean.setPrices(inlineProvinceMap);
				list.add(returnBean);
			}
		}
		
		return list;
	}
	
	private Map<String,Double> cloneMap(Map<String,Double> map) {
		Map<String,Double> cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(map);
            obs.close();
            
            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            cloneObj = (Map<String,Double>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
	}
}
