package com.shziyuan.flow.global.domain.flow.wraped;

import java.util.List;
import java.util.NoSuchElementException;

import com.shziyuan.flow.global.domain.flow.BindSupplier;

public class BindSupplierBean extends BindSupplier{

	private static final long serialVersionUID = 1L;

	private List<InfoSupplierCodetableBean> codeList;
	private InfoSupplierCodetableBean currentCode;

	public BindSupplierBean() {
	
	}
	
	public List<InfoSupplierCodetableBean> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<InfoSupplierCodetableBean> codeList) {
		this.codeList = codeList;
	}
	
	public void currentCode(int index) {
		if(codeList == null || codeList.size() <= index)
			throw new NoSuchElementException();
		
		this.currentCode = codeList.get(index);
	}

	public InfoSupplierCodetableBean getCurrentCode() {
		return currentCode;
	}

}
