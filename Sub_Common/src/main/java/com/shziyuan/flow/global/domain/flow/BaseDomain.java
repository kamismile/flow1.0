package com.shziyuan.flow.global.domain.flow;

import java.io.Serializable;

public class BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int _rownum;

	public int get_rownum() {
		return _rownum;
	}

	public void set_rownum(int _rownum) {
		this._rownum = _rownum;
	}

	public String showname() {
		return null;
	}
}
