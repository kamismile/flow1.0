package com.ziyuan.web.manager.wrap;

import java.util.Arrays;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shziyuan.flow.global.domain.flow.WebAccount;
import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.dao.WebAccountAuthorityMapper;
import com.ziyuan.web.manager.dao.WebAccountMapper;
import com.ziyuan.web.manager.domain.WebAccountDistributorBean;

@Repository
public class WebAccountWrap extends AbstractCRUDWrap<WebAccount>{

	@Resource
	private WebAccountMapper webAccountMapper;
	@Resource
	private WebAccountAuthorityMapper webAccountAuthorityMapper;
	
	@Override
	public ICRUDMapper<WebAccount> getMapper() {
		return webAccountMapper;
	}
	
	@Transactional(readOnly = true)
	public WebAccount selectByUsername(String username) {
		return webAccountMapper.selectByUsername(username);
	}

	@Transactional(readOnly = true)
	public WebAccountDistributorBean selectDiscountByCode(String code) {
		return webAccountMapper.selectDiscountByCode(code);
	}
	
	@Transactional(readOnly = false)
	public WebAccount insert(WebAccount domain,String[] authority) {
		WebAccount acc = super.insert(domain);
		Arrays.stream(authority)
				.map(auth -> new WebAccountAuthority(acc.getId(),auth))
				.forEach(auth -> webAccountAuthorityMapper.insert(auth));
		return acc;
	}
	
	@Transactional(readOnly = false)
	public WebAccount update2(WebAccount domain,String[] authority) {
		
		SqlSession session = getSqlSessionFactory().openSession();
		
		try {
			session.update(WebAccountMapper.class.getName() + ".update",domain);
			session.delete(WebAccountAuthorityMapper.class.getName() + ".deleteByAccountId",domain.getId());
			
			Arrays.stream(authority)
					.map(auth -> new WebAccountAuthority(domain.getId(), auth))
					.forEach(webauth -> session.insert(WebAccountAuthorityMapper.class.getName() + ".insert",webauth));
			session.commit();
		} catch (RuntimeException e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
		
		return domain;
	}

}
