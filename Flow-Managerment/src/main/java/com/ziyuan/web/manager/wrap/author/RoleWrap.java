package com.ziyuan.web.manager.wrap.author;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.shziyuan.flow.global.domain.flow.WebAccountAuthority;
import com.ziyuan.web.manager.dao.WebAccountAuthorityMapper;
import com.ziyuan.web.manager.dao.author.WebAccountAuthorityMegerMapper;
import com.ziyuan.web.manager.dao.author.WebAccountRoleMegerMapper;
import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;

@Repository
public class RoleWrap {
	@Resource
	private WebAccountRoleMegerMapper webAccountRoleMegerMapper;
	
	@Resource
	private WebAccountAuthorityMegerMapper webAccountAuthorityMegerMapper;

	/**
	 * 增加角色
	 * @param webAccountRoleMegerBean
	 */
	@Transactional(readOnly=false)
	public void addAccount(WebAccountRoleMegerBean webAccountRoleMegerBean) {
		// TODO Auto-generated method stub
		webAccountRoleMegerMapper.insert(webAccountRoleMegerBean);
	}

	/**
	 * 删除角色
	 * @param webAccountRoleMegerBean
	 */
	@Transactional(readOnly=false)
	public void deleteAccount(WebAccountRoleMegerBean webAccountRoleMegerBean) {
		// TODO Auto-generated method stub
		webAccountRoleMegerMapper.delete(webAccountRoleMegerBean.getId());
	}

	/**
	 * 更新角色
	 * @param webAccountRoleMegerBean
	 */
	@Transactional(readOnly=false)
	public void updateAccount(WebAccountRoleMegerBean webAccountRoleMegerBean) {
		// TODO Auto-generated method stub
		webAccountRoleMegerMapper.update(webAccountRoleMegerBean);
	}

	/**
	 * 查询所有角色
	 * @param jEasyuiRequestBean
	 * @return
	 */
	@Transactional(readOnly=true)
	public JEasyuiData selectAllAccount(JEasyuiRequestBean jEasyuiRequestBean) {
		PageHelper.startPage(jEasyuiRequestBean.getPage(), jEasyuiRequestBean.getRows());
		List<WebAccountRoleMegerBean> list = webAccountRoleMegerMapper.select(jEasyuiRequestBean.getParam());
		PageInfo<WebAccountRoleMegerBean> pageInfo = new PageInfo<WebAccountRoleMegerBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}

	/**
	 * 查询绑定角色
	 * @param jEasyuiRequestBean
	 * @return
	 */
	@Transactional(readOnly=true)
	public JEasyuiData selectBindAuthority(JEasyuiRequestBean jEasyuiRequestBean) {
		PageHelper.startPage(jEasyuiRequestBean.getPage(), jEasyuiRequestBean.getRows());
		List<WebAccountRoleMegerBean> list = webAccountRoleMegerMapper.selectBindAuthority(jEasyuiRequestBean.getParam());
		PageInfo<WebAccountRoleMegerBean> pageInfo = new PageInfo<WebAccountRoleMegerBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}

	/**
	 * 查询单个角色
	 * @param role
	 * @return
	 */
	@Transactional(readOnly=true)
	public JEasyuiData selectOneRole(String description, String enabled, int page, int rows) {
		PageHelper.startPage(page, rows);
		List<WebAccountRoleMegerBean> list = webAccountRoleMegerMapper.selectOneRole(description, enabled);
		PageInfo<WebAccountRoleMegerBean> pageInfo = new PageInfo<WebAccountRoleMegerBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}
	
	@Transactional(readOnly=true)
	public List<WebAccountAuthorityMegerBean> selectAuth(String role){
		List<WebAccountAuthorityMegerBean> list = webAccountAuthorityMegerMapper.selectBindAuthoritySys(role);
		return list;
	}

	@Transactional(readOnly=true)
	public int getID(String string) {
		// TODO Auto-generated method stub
		return webAccountAuthorityMegerMapper.getID(string);
	}

}
