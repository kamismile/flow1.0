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
import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.ziyuan.web.manager.dao.BindDistributorMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.domain.BindDistributorBean;
import com.ziyuan.web.manager.domain.BindDistributorBean2;
import com.ziyuan.web.manager.domain.InfoSkuBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

@Repository
public class BindDistributorWrap extends AbstractCRUDWrap<BindDistributor>{

	@Resource
	private BindDistributorMapper bindDistributorMapper;
	
	@Override
	public ICRUDMapper<BindDistributor> getMapper() {
		return bindDistributorMapper;
	}
		
	@Transactional(readOnly = true)
	public JEasyuiData selectBind(JEasyuiRequestBean bean) {
		
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<BindDistributorBean> list = bindDistributorMapper.selectBind(bean.getParam());
		PageInfo<BindDistributorBean> pageInfo = new PageInfo<BindDistributorBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize((int) pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeBySku() {
		return bindDistributorMapper.selectTreeBySku();
	}
	
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeByDistributor() {
		return bindDistributorMapper.selectTreeByDistributor();
	}
	
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeInBind2() {
		return bindDistributorMapper.selectTreeInBind2();
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectTreeTable(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<BindDistributorBean> list = bindDistributorMapper.selectTreeTable(bean.getParam());
		PageInfo<BindDistributorBean> pageInfo = new PageInfo<BindDistributorBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize((int) pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public List<BindDistributorBean> exportTreeTable(JEasyuiRequestBean bean) {
		List<BindDistributorBean> list = bindDistributorMapper.exportTreeTable(bean.getParam());
		
		return list;
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBusinessSkus(JEasyuiRequestBean bean) {
		List<InfoSkuBean> list = bindDistributorMapper.selectBusinessSkus(bean.getParam());
		JEasyuiData result = new JEasyuiData(list);
		return result;
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBusinessSkusPages(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<InfoSkuBean> list = bindDistributorMapper.selectBusinessSkus(bean.getParam());
		PageInfo<InfoSkuBean> pageInfo = new PageInfo<InfoSkuBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize((int) pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Transactional(readOnly = true)
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<BindDistributorBean2> list = bindDistributorMapper.selectBind2_binded(bean.getParam());
		PageInfo<BindDistributorBean2> pageInfo = new PageInfo<BindDistributorBean2>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize((int) pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		return result;
	}
	@Transactional(readOnly = true)
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<InfoSku> list = bindDistributorMapper.selectBind2_nonbinded(bean.getParam());
		PageInfo<InfoSku> pageInfo = new PageInfo<InfoSku>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize((int) pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Transactional(readOnly = true)
	public boolean checkNull() {
		// TODO Auto-generated method stub
		int tem = bindDistributorMapper.checkNull();
		if(tem > 0){
			return true;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean checkRepetion() {
		int tem = bindDistributorMapper.checkRepetion();
		if(tem > 0){
			return true;
		}
		return false;
	}
}
