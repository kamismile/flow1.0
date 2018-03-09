package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.InfoSku;
import com.shziyuan.flow.global.domain.flow.InfoSupplierBinded;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.ziyuan.web.manager.dao.BindSupplierMapper;
import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.domain.BindSupplierBean;
import com.ziyuan.web.manager.domain.BindSupplierBean2;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.domain.TreeBindBean;

@Repository
public class BindSupplierWrap extends AbstractCRUDWrap<BindSupplier>{

	@Resource
	private BindSupplierMapper bindSupplierMapper;
		
	@Override
	public ICRUDMapper<BindSupplier> getMapper() {
		return bindSupplierMapper;
	}

	@Transactional(readOnly = true)
	public JEasyuiData selectCodetable(JEasyuiRequestBean bean) {

		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<BindSupplierBean> list = bindSupplierMapper.selectCodetable(bean.getParam());
		PageInfo<BindSupplierBean> pageInfo = new PageInfo<BindSupplierBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBindedSorting(JEasyuiRequestBean bean) {
		List<BindSupplierBean> list = bindSupplierMapper.selectBindedSorting(bean.getParam());

		return new JEasyuiData(list);
	}
	
	/**
	 * TREE
	 */
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeBySupplier() {
		return bindSupplierMapper.selectTreeBySupplier();
	}
	@Transactional(readOnly = true)
	public List<TreeBindBean> selectTreeBySku() {
		return bindSupplierMapper.selectTreeBySku();
	}

	@Transactional(readOnly = false)
	public JEasyuiData batchChangeBinding(List<Map<String,Object>> changeList,boolean masterSupplier) {
		JEasyuiData result = new JEasyuiData();
		
		final String KEY_RET = "success";
		final String KEY_MSG = "errorMsg";
		final String KEY_SKU = "sku";
		final String KEY_CODE = "code";

		SqlSession session = getSqlSessionFactory().openSession();
		
		try {
			changeList.forEach(change -> {
				if(!((boolean)change.get(KEY_RET))) {
					return;
				}
				
				InfoSku sku = (InfoSku) change.get(KEY_SKU);
				InfoSupplierCodetable code = (InfoSupplierCodetable) change.get(KEY_CODE);
				BindSupplier newbind = new BindSupplier();
				
				if(masterSupplier)
					session.delete(BindSupplierMapper.class.getName() + ".deleteBySkuId",sku.getId());
				newbind.setSku_id(sku.getId());
				newbind.setPkg_id(0);
				newbind.setSupplier_id(code.getSupplier_id());
				newbind.setSupplier_code_id(code.getId());
				newbind.setEnabled(true);
				newbind.setSorting(masterSupplier ? 1 : 2);
				session.insert(BindSupplierMapper.class.getName() + ".insert",newbind);
			});
			
			session.commit();
			result.setSuccess(true);
			result.setData(changeList);
		} catch (RuntimeException e) {
			session.rollback();
			result.setSuccess(false);
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	
	@Transactional(readOnly = true)
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		List<BindSupplierBean2> list = bindSupplierMapper.selectBind2_binded(bean.getParam());
		return new JEasyuiData(list);
	}
	@Transactional(readOnly = true)
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<InfoSupplierCodetableBean> list = bindSupplierMapper.selectBind2_nonbinded(bean.getParam());
		PageInfo<InfoSupplierCodetableBean> pageInfo = new PageInfo<InfoSupplierCodetableBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}
	
	@Transactional(readOnly = true)
	public InfoSupplierBinded selectByOrder(Map<String,Object> param) {
		return bindSupplierMapper.selectByOrder(param);
	}

	@Transactional(readOnly = true)
	public boolean checkNull() {
		int tem = bindSupplierMapper.checkNull();
		if(tem > 0){
			return true;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean checkRepetion() {
		int tem = bindSupplierMapper.checkRepetion();
		if(tem > 0){
			return true;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public BindSupplier selectById(int id) {
		// TODO Auto-generated method stub
		return bindSupplierMapper.selectById(id);
	}

	@Transactional(readOnly = true)
	public List<BindSupplier> selectBySkuId(int sku_id) {
		// TODO Auto-generated method stub
		return bindSupplierMapper.selectBySkuId(sku_id);
	}

	@Transactional(readOnly = false)
	public void updateSortingById(BindSupplier bindSupplier) {
		// TODO Auto-generated method stub
		bindSupplierMapper.updateSortingById(bindSupplier);
	}
}
