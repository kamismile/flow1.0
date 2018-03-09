package com.shziyuan.flow.irs.dao;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.shziyuan.flow.global.domain.flow.InfoDistributorIpAuthority;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.wraped.InfoDistributorBean;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ComponentScan("com.shziyuan.flow.irs.dao")
public class InfoDistributorDaoTest {
	@Autowired
	private InfoDistributorDao infoDistributorDao;
	
	@Test
	public void test() {
		Map<String,InfoDistributorBean> map = infoDistributorDao.selectAll();
		
		for(InfoDistributorBean dis : map.values()) {
			System.out.printf("%d-%s %s\n",dis.getId(),dis.getName(),dis.getInfo_company_name());
//			for(String ip : dis.getIpAuthoritys())
//				System.out.printf("\t%s\n",ip);
		}
	}
	
	
}
