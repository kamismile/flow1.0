package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.ziyuan.web.manager.dao.BindSupplierPresentMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.domain.BindSupplierBean;
import com.ziyuan.web.manager.domain.BindSupplierBean2;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;

@Repository
public class BindSupplierPresentWrap extends AbstractCRUDWrap<BindSupplier>{

	@Resource
	private BindSupplierPresentMapper bindSupplierPresentMapper;
		
	@Override
	public ICRUDMapper<BindSupplier> getMapper() {
		return bindSupplierPresentMapper;
	}

	@Transactional(readOnly = true)
	public JEasyuiData selectCodetable(JEasyuiRequestBean bean) {
		List<BindSupplierBean> list = bindSupplierPresentMapper.selectCodetable(bean.getParam());

		JEasyuiData result = new JEasyuiData(list);
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBindedSorting(JEasyuiRequestBean bean) {
		List<BindSupplierBean> list = bindSupplierPresentMapper.selectBindedSorting(bean.getParam());

		return new JEasyuiData(list);
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<BindSupplierBean2> list = bindSupplierPresentMapper.selectBind2_binded(bean.getParam());
		PageInfo<BindSupplierBean2> pageInfo = new PageInfo<BindSupplierBean2>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<InfoSupplierCodetableBean> list = bindSupplierPresentMapper.selectBind2_nonbinded(bean.getParam());
		PageInfo<InfoSupplierCodetableBean> pageInfo = new PageInfo<InfoSupplierCodetableBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}
	
	@Transactional(readOnly = false)
	public void bindChange(List<BindSupplier> newlist, List<BindSupplier> removelist) {
		super.batchRun(session -> {
			if(removelist.size() > 0) {
				removelist.forEach(item -> {
					session.delete("com.ziyuan.web.manager.dao.BindSupplierPresentMapper.delete",item.getId());
				});
			}
			if(newlist.size() > 0) {
				newlist.forEach(item -> {
					session.insert("com.ziyuan.web.manager.dao.BindSupplierPresentMapper.insert",item);
				});
			}
		});
	}
	
	@Transactional(readOnly = true)
	public InfoSupplierBinded selectByOrder(Map<String,Object> param) {
		return bindSupplierPresentMapper.selectByOrder(param);
	}
	
	@Transactional(readOnly = true)
	public boolean checkNull() {
		// TODO Auto-generated method stub
		int tem = bindSupplierPresentMapper.checkNull();
		if(tem > 0){
			return true;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean checkRepetion() {
		int tem = bindSupplierPresentMapper.checkRepetion();
		if(tem > 0){
			return true;
		}
		return false;
	}
}
