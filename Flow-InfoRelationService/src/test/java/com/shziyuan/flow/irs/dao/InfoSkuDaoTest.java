package com.shziyuan.flow.irs.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.global.domain.flow.InfoSku;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ComponentScan("com.shziyuan.flow.irs.dao")
public class InfoSkuDaoTest {
	@Autowired
	private InfoSkuDao infoSkuDao;
	
	@Test
	public void test() {
		System.out.println(infoSkuDao.selectAll());
	}
	
}
