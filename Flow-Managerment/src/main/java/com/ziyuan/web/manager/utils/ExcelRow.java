package com.ziyuan.web.manager.utils;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ExcelRow extends ArrayList<String>{

	private static final long serialVersionUID = 1L;
	
	public ExcelRow() {
	
	}
	
	public ExcelRow(int size) {
		super(size);
	}
	
	public boolean add(String data) {
		return this.add(data,"");
	}
	public boolean add(String data,String nullValue) {
		return super.add(data == null ? nullValue : data);
	}
	public boolean add(int data) {
		return super.add(Integer.toString(data));
	}
	public boolean add(float data) {
		return super.add(Float.toString(data));
	}
	public boolean add(double data) {
		return super.add(Double.toString(data));
	}
	public boolean add(boolean data) {
		return super.add(data ? "是" : "否");
	}
	
	public boolean add(Timestamp data) {
		return this.add(data,"");
	}
	public boolean add(Timestamp data,String nullValue) {
		return super.add(data == null ? nullValue : 
			com.shziyuan.flow.global.util.StringUtil.formatTimestamp(data));
	}
	
	public boolean addUseObject(Object data,String nullValue) {
		return super.add(data == null ? nullValue : data.toString());
	}
}
