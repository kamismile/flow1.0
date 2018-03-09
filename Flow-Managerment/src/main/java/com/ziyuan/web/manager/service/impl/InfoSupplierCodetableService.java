package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shziyuan.flow.global.common.CacheDefine;
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable;
import com.ziyuan.web.manager.domain.BindDistributorBean;
import com.ziyuan.web.manager.domain.InfoSupplierCodetableBean;
import com.ziyuan.web.manager.domain.TreeBean;
import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.service.fmt.Formatter;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoSupplierCodetableWrap;



@Service
public class InfoSupplierCodetableService extends AbstractCRUDService<InfoSupplierCodetable> {

	@Resource
	private InfoSupplierCodetableWrap infoSupplierCodetableWrap;

	@Resource
	private Formatter formatter;
	
	@Override
	public ICRUDWrap<InfoSupplierCodetable> getWrap() {
		return infoSupplierCodetableWrap;
	}
	
	@Override
	protected String getMQCahceName() {
		return FlowMqDefine.CHANGE_SUPPLIER_CODE;
	}
	
	public int selectCountByCode(int supplier_id, String code) {
		
		return infoSupplierCodetableWrap.selectCountByCode(supplier_id, code);
	}
	
	@Override
	public JEasyuiData insert(InfoSupplierCodetable domain) {
		domain.setEnabled(true);
		int count = selectCountByCode(domain.getSupplier_id(), domain.getCode());
		if (count > 0) {
			return new JEasyuiData(false, "产品供货代码已存在");
		}
		return super.insert(domain);
	}
	
	public List<TreeBean> listSupplierSkuBySupplier() {
		// TODO Auto-generated method stub
		return infoSupplierCodetableWrap.listSupplierSkuBySupplier();
	}
	
	public JEasyuiData updateCacheTable(InfoSupplierCodetable domain) {
		
		try {
			infoSupplierCodetableWrap.updateCacheTable(domain);
			return new JEasyuiData(domain);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}

	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		//DynamicDataSourceHolder.useSlave();
		
		List<InfoSupplierCodetable> datalist = infoSupplierCodetableWrap.selectAll(domain);
		String sheetname = "供应商产品";
		String[] headers = {"名称","省份","运营商","使用范围","供货代码","折扣","单价","颗粒度","类型","网络","租用类型","是否启用"};
		RowFunction<InfoSupplierCodetable> rowAction = (sc,row) -> {
			row.add(sc.getName());
			row.add(formatter.getProvince(sc.getProvinceid()));
			row.add(sc.getOperator());
			row.add(formatter.getScope(sc.getScope_cid()));
			row.add(sc.getCode());
			row.add(sc.getDiscount());
			row.add(sc.getPrice());
			row.add(sc.getPkgsize());
			row.add(sc.getInfo_ptype());
			row.add(sc.getInfo_net());
			row.add(formatter.getRentType(sc.getRent_type()));
			row.add(sc.isEnabled());
			return 0;
		};
		
		NormallyExcelExporter<InfoSupplierCodetable> exporter = new NormallyExcelExporter<>(sheetname, headers, rowAction);
		try {
			return exporter.export(datalist);
		} catch (IOException e) {
			logger.error("导出供应商产品列表错误",e);
			return null;
		}
	}
	
	public JEasyuiData selectTree() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(infoSupplierCodetableWrap.selectTree());
	}
	public JEasyuiData selectTreeInCodetable2() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(infoSupplierCodetableWrap.selectTreeInCodetable2());
	}
	
	public JEasyuiData selectJoinSupplier(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<InfoSupplierCodetableBean> list = infoSupplierCodetableWrap.selectJoinSupplier(bean.getParam());
		PageInfo<InfoSupplierCodetableBean> pageInfo = new PageInfo<InfoSupplierCodetableBean>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}
	
	public JEasyuiData selectJoinSku(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(infoSupplierCodetableWrap.selectJoinSku(bean));
	}

	public JEasyuiData updateCodeParam(InfoSupplierCodetable domain) {
		// TODO Auto-generated method stub
		try {
			infoSupplierCodetableWrap.updateCodeParam(domain);
			return new JEasyuiData(true);
		} catch (Exception e) {
			return new JEasyuiData(false, e.getMessage());
		}
	}

	public ByteArrayOutputStream exportJoinSupplier(JEasyuiRequestBean bean) {
		List<InfoSupplierCodetableBean> dataList = infoSupplierCodetableWrap.exportJoinSupplier(bean.getParam());
		String sheetname = "供应商折扣调整";
		String[] headers = {"供应商","供应商产品","运营商","省份","折扣","单价",
				"状态","颗粒度","产品代码","调整折扣","调整进价","生效时间"};
		RowFunction<InfoSupplierCodetableBean> rowAction = (sup,row) -> {
			InfoSupplierCodetableBean bind = (InfoSupplierCodetableBean)sup;
			row.add(bind.getSupplier_name());
			row.add(bind.getName());
			row.add(bind.getOperator());
			row.add(bind.getProvince());
			row.add(bind.getDiscount());
			row.add(bind.getPrice());
			row.add(bind.isEnabled());
			row.add(bind.getPkgsize());
			row.add(bind.getCode());
			row.add(bind.getChange().getDiscount());
			row.add(bind.getChange().getPrice());
			row.add(bind.getChange().getEffective());
			return 0;
		};
		NormallyExcelExporter<InfoSupplierCodetableBean> exporter = new NormallyExcelExporter<>(sheetname, headers, rowAction);
		try {
			return exporter.export(dataList);
		} catch (IOException e) {
			logger.error("导出供应商折扣调整错误",e);
			return null;
		}
	}

}
