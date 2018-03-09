package com.shziyuan.flow.global.domain.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<?> rows;
	private List<?> footer;
	
	private Object data;
	
	private int page = 1;
	private int pageSize = 10;
	private int total;
	
	private List<Column> columns;
	
	private boolean success;
	private int code;
	private String errorMsg;
	private List<Field> fieldErros;
	
	private Throwable throwable;
	
	
	public ActionResponse() {
	
	}
	
	public ActionResponse(boolean success) {
		this.success = success;
	}
	
	public ActionResponse(boolean success,List<?> rows) {
		this.success = success;
		this.rows = rows;
	}
	
	public ActionResponse(boolean success,Object data) {
		this.success = success;
		this.data = data;
	}
	public ActionResponse(boolean success,String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
	}
	public static ActionResponse success(List<?> rows) {
		ActionResponse ret = new ActionResponse(true,rows);
		ret.total = rows.size();
		return ret;
	}
	
	public static ActionResponse success() {
		ActionResponse ret = new ActionResponse(true);
		return ret;
	}
	
	public static ActionResponse success(Object data) {
		ActionResponse ret = new ActionResponse(true,data);
		ret.success = true;
		return ret;
	}
	
	public static ActionResponse error(String errorMsg) {
		return new ActionResponse(false, errorMsg);
	}
	
	public static ActionResponse error(Throwable e) {
		ActionResponse resp = new ActionResponse(false, e.getMessage());
		resp.setThrowable(e);
		return resp;
	}
	
	public static ActionResponse error(int code, String errorMsg) {
		ActionResponse ret = new ActionResponse(false, errorMsg);
		ret.code = code;
		return ret;
	}
	public ActionResponse(boolean success,Field... fields) {
		this.success = success;
		this.fieldErros = new ArrayList<Field>(fields.length);
		for(Field field : fields)
			this.fieldErros.add(field);
	}
	

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
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
	public List<Field> getFieldErros() {
		return fieldErros;
	}
	public void setFieldErros(List<Field> fieldErros) {
		this.fieldErros = fieldErros;
	}
	public void addFieldErrors(String name,String message) {
		if(this.fieldErros == null)
			this.fieldErros = new ArrayList<Field>();
		this.fieldErros.add(new Field(name,message));
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
