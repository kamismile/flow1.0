package com.ziyuan.web.manager.service.author;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;
import com.ziyuan.web.manager.service.impl.AbstractCRUDService;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.author.AuthorityWrap;


/**
 * 权限表Service
 * @author user
 *
 */
@Service
public class AuthorityServiceImpl extends AbstractCRUDService<WebAccountAuthorityMegerBean> {
	
	private Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);
	
	@Resource
	private AuthorityWrap authorityWrap;

	/**
	 * 增加权限
	 * @param webAccountAuthorityMegerBean
	 * @return
	 */
	public JEasyuiData addAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean) {
//		
		JEasyuiData res = new JEasyuiData();
		try {
			authorityWrap.addAccount(webAccountAuthorityMegerBean);
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("插入失败",e);
			res.setSuccess(false);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/**
	 * 删除权限
	 * @param webAccountAuthorityMegerBean
	 * @return
	 */
	public JEasyuiData deleteAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean) {
//		
		JEasyuiData res = new JEasyuiData();
		try {
			authorityWrap.deleteAccount(webAccountAuthorityMegerBean);
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除失败",e);
			res.setSuccess(false);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/**
	 * 更新权限
	 * @param webAccountAuthorityMegerBean
	 * @return
	 */
	public JEasyuiData updateAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean) {
//		
		JEasyuiData res = new JEasyuiData();
		try {
			authorityWrap.updateAccount(webAccountAuthorityMegerBean);
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("修改失败",e);
			res.setSuccess(false);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	public JEasyuiData selectAllAccount(JEasyuiRequestBean jEasyuiRequestBean) {
//		//DynamicDataSourceHolder.useSlave();
		return authorityWrap.selectAllAccount(jEasyuiRequestBean);
	}

	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getMQCahceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICRUDWrap<WebAccountAuthorityMegerBean> getWrap() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询单个权限
	 * @param authority
	 * @return
	 */
	public JEasyuiData selectOneAuthority(String description, String enabled, int page, int rows) {
//		//DynamicDataSourceHolder.useSlave();
		return authorityWrap.selectOneAuthority(description, enabled, page, rows);
	}

}
