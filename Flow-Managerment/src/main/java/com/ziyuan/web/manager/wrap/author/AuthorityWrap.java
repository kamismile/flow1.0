package com.ziyuan.web.manager.wrap.author;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.dao.author.WebAccountAuthorityMegerMapper;
import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;

@Repository
public class AuthorityWrap {
	@Resource
	private WebAccountAuthorityMegerMapper webAccountAuthorityMegerMapper;

	/**
	 * 增加权限
	 * @param webAccountAuthorityMegerBean
	 */
	@Transactional(readOnly=false)
	public void addAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean) {
		// TODO Auto-generated method stub
		webAccountAuthorityMegerMapper.insert(webAccountAuthorityMegerBean);
	}

	/**
	 * 删除权限
	 * @param webAccountAuthorityMegerBean
	 */
	@Transactional(readOnly=false)
	public void deleteAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean) {
		// TODO Auto-generated method stub
		webAccountAuthorityMegerMapper.delete(webAccountAuthorityMegerBean.getId());
	}

	/**
	 * 修改权限
	 * @param webAccountAuthorityMegerBean
	 */
	@Transactional(readOnly=false)
	public void updateAccount(WebAccountAuthorityMegerBean webAccountAuthorityMegerBean) {
		// TODO Auto-generated method stub
		webAccountAuthorityMegerMapper.update(webAccountAuthorityMegerBean);
	}

	/**
	 * 查询所有权限
	 * @param jEasyuiRequestBean
	 * @return
	 */
	@Transactional(readOnly=true)
	public JEasyuiData selectAllAccount(JEasyuiRequestBean jEasyuiRequestBean) {
		PageHelper.startPage(jEasyuiRequestBean.getPage(), jEasyuiRequestBean.getRows());
		List<WebAccountAuthorityMegerBean> list = webAccountAuthorityMegerMapper.select(jEasyuiRequestBean.getParam());
		PageInfo<WebAccountAuthorityMegerBean> pageInfo = new PageInfo<WebAccountAuthorityMegerBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}

	/**
	 * 查询单个权限
	 * @param authority
	 * @return
	 */
	@Transactional(readOnly=true)
	public JEasyuiData selectOneAuthority(String description, String enabled, int page, int rows) {
		PageHelper.startPage(page, rows);
		List<WebAccountAuthorityMegerBean> list = webAccountAuthorityMegerMapper.selectOneAuthority(description, enabled);
		PageInfo<WebAccountAuthorityMegerBean> pageInfo = new PageInfo<WebAccountAuthorityMegerBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}

}
