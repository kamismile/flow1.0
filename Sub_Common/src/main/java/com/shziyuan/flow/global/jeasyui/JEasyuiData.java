package com.shziyuan.flow.global.jeasyui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JEasyuiData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<?> rows;
	private List<?> footer;
	
	private Object data;
	
	private int page = 1;
	private int pageSize = 10;
	private int total;
	
	private List<JEasyuiColumn> columns;
	
	private boolean success;
	private String errorMsg;
	private List<JEasyuiField> fieldErros;
	
	public List<JEasyuiColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<JEasyuiColumn> columns) {
		this.columns = columns;
	}
	
	public JEasyuiData() {
	}
	public JEasyuiData(List<?> rows) {
		this.rows = rows;
		this.total = rows.size();
		this.success = true;
	}
	
	public JEasyuiData(Object data) {
		this.success = true;
		this.data = data;
	}
	
	public JEasyuiData(boolean success,String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
	}
	
	public JEasyuiData(boolean success,JEasyuiField... fields) {
		this.success = success;
		this.fieldErros = new ArrayList<JEasyuiField>(fields.length);
		for(JEasyuiField field : fields)
			this.fieldErros.add(field);
	}
	public JEasyuiData(boolean success,List<JEasyuiField> fields) {
		this.success = success;
		this.fieldErros = fields;
	}
	
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public List<?> getFooter() {
		return footer;
	}
	public void setFooter(List<?> footer) {
		this.footer = footer;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public List<JEasyuiField> getFieldErros() {
		return fieldErros;
	}
	public void setFieldErros(List<JEasyuiField> fieldErros) {
		this.fieldErros = fieldErros;
	}
	public void addFieldErrors(String name,String message) {
		if(this.fieldErros == null)
			this.fieldErros = new ArrayList<JEasyuiField>();
		this.fieldErros.add(new JEasyuiField(name,message));
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
