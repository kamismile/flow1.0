package com.shziyuan.flow.irs.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.global.domain.flow.InfoCitySubmobile;
import com.shziyuan.flow.global.domain.flow.wraped.BindDistributorBean;
import com.shziyuan.flow.global.domain.flow.wraped.BindSupplierBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoCitySubmobileServiceTest {
	
	@Autowired
	private InfoCitySubmobileService infoCitySubmobileService;
	
	@Test
	public void test2() {
		InfoCitySubmobile bean = infoCitySubmobileService.getProvince("13761236377");
		System.out.println(bean.getProvinceid());
		
	}
}
