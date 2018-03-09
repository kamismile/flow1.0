package com.ziyuan.web.manager.service.author;

import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;
import com.ziyuan.web.manager.service.impl.AbstractCRUDService;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.author.RoleWrap;

/**
 * 角色表Service
 * @author user
 *
 */
@Service
public class RoleServiceImpl extends AbstractCRUDService<WebAccountRoleMegerBean> {
	
	private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Resource
	private RoleWrap roleWrap;

	/**
	 * 增
	 */
	public JEasyuiData addAccount(WebAccountRoleMegerBean webAccountRoleMegerBean) {
//		
		JEasyuiData res = new JEasyuiData();
		try {
			roleWrap.addAccount(webAccountRoleMegerBean);
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("插入失败",e);
			res.setSuccess(false);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/**
	 * 删
	 */
	public JEasyuiData deleteAccount(WebAccountRoleMegerBean webAccountRoleMegerBean) {
//		
		JEasyuiData res = new JEasyuiData();
		try {
			roleWrap.deleteAccount(webAccountRoleMegerBean);
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("删除失败",e);
			res.setSuccess(false);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/**
	 * 改
	 */
	public JEasyuiData updateAccount(WebAccountRoleMegerBean webAccountRoleMegerBean) {
//		
		JEasyuiData res = new JEasyuiData();
		try {
			roleWrap.updateAccount(webAccountRoleMegerBean);
			res.setSuccess(true);
		} catch (Exception e) {
			logger.error("修改失败",e);
			res.setSuccess(false);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/**
	 * 查
	 */
	public JEasyuiData selectAllAccount(JEasyuiRequestBean jEasyuiRequestBean) {
//		//DynamicDataSourceHolder.useSlave();
		return roleWrap.selectAllAccount(jEasyuiRequestBean);
	}

	/**
	 * 查询绑定权限
	 */
	public JEasyuiData selectBindAuthority(JEasyuiRequestBean jEasyuiRequestBean) {
//		//DynamicDataSourceHolder.useSlave();
		return roleWrap.selectBindAuthority(jEasyuiRequestBean);
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
	public ICRUDWrap<WebAccountRoleMegerBean> getWrap() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询单个角色
	 * @param role
	 * @return
	 */
	public JEasyuiData selectOneRole(String description, String enabled, int page, int rows) {
//		//DynamicDataSourceHolder.useSlave();
		return roleWrap.selectOneRole(description, enabled, page, rows);
	}

}
