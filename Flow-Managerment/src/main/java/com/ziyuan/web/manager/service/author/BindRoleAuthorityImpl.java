package com.ziyuan.web.manager.service.author;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import com.ziyuan.web.manager.dao.author.BindRoleAuthorityMapper;
import com.ziyuan.web.manager.domain.author.BindAccountRoleAuthorityBean;
import com.ziyuan.web.manager.domain.author.BindRoleAuthorityBean;
import com.ziyuan.web.manager.service.impl.AbstractCRUDService;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.author.BindRoleAuthorityWrap;


/**
 * 角色-权限绑定表Service
 * @author user
 *
 */
@Service
public class BindRoleAuthorityImpl extends AbstractCRUDService<BindRoleAuthorityBean>{
	
	private Logger logger = LoggerFactory.getLogger(BindRoleAuthorityImpl.class);
	
	@Resource
	private BindRoleAuthorityWrap bindRoleAuthorityWrap;

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
	public ICRUDWrap<BindRoleAuthorityBean> getWrap() {
		// TODO Auto-generated method stub
		return bindRoleAuthorityWrap;
	}

	/**
	 * 批量插入绑定权限
	 * @param data
	 * @return
	 */
	public JEasyuiData insertBindAuthority(BindAccountRoleAuthorityBean data) {
//		
		List<BindRoleAuthorityBean> bindRoleAuthorityList = data.getBindRoleAuthorityList();
		try {
			if(bindRoleAuthorityList.size() > 0){
				bindRoleAuthorityWrap.batchInsert(BindRoleAuthorityMapper.class.getName(),bindRoleAuthorityList);
				return new JEasyuiData(true, "批量插入绑定权限成功");
			}else{
				return new JEasyuiData(false, "传入参数为空");
			}
		} catch (Exception e) {
			logger.error("批量插入绑定权限失败", e);
			return new JEasyuiData(false,"批量插入绑定权限失败");
		}
	}
	
	/**
	 * 批量删除绑定权限
	 * @param data
	 * @return
	 */
	public JEasyuiData deleteBindAuthority(BindAccountRoleAuthorityBean data) {
//		
		List<BindRoleAuthorityBean> bindRoleAuthorityList = data.getBindRoleAuthorityList();
		try {
			if(bindRoleAuthorityList.size() > 0){
				bindRoleAuthorityWrap.batchDelete(BindRoleAuthorityMapper.class.getName(), bindRoleAuthorityList, (domain) -> domain.getRole_id());
				return new JEasyuiData(true,"批量删除绑定权限成功");
			}else{
				return new JEasyuiData(false,"传入参数为空");
			}
		} catch (Exception e) {
			logger.error("批量删除绑定权限失败", e);
			return new JEasyuiData(false,"批量删除绑定权限失败");
		}
	}
	
	/**
	 * 批量更新绑定权限
	 * @param data
	 * @return
	 */
	public JEasyuiData updateBindAuthority(BindAccountRoleAuthorityBean data) {
//		
		List<BindRoleAuthorityBean> bindRoleAuthorityList = data.getBindRoleAuthorityList();
		try {
			if(bindRoleAuthorityList.size() > 0){
				bindRoleAuthorityWrap.delete(bindRoleAuthorityList.get(0).getRole_id());
				bindRoleAuthorityWrap.batchInsert(BindRoleAuthorityMapper.class.getName(),bindRoleAuthorityList);
				return new JEasyuiData(true, "批量更新绑定权限成功");
			}else{
				return new JEasyuiData(false, "传入参数为空");
			}
		} catch (Exception e) {
			logger.error("批量更新绑定权限失败", e);
			return new JEasyuiData(false,"批量更新绑定权限失败");
		}
	}

}
