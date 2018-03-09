package com.shziyuan.flow.global.domain.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.shziyuan.flow.global.domain.common.Status;

public class AccountResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<?> rows;
	private List<?> footer;
	
	private Object data;
	
	private Status status;
	
	private int page = 1;
	private int pageSize = 10;
	private int total;
	
	private List<Column> columns;
	
	private boolean success;
	private int code;
	private String errorMsg;
	private List<Field> fieldErros;
	
	private Throwable throwable;
	
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public AccountResponse() {
	
	}
	
	public AccountResponse(boolean success) {
		this.success = success;
	}
	
	public AccountResponse(boolean success,List<?> rows) {
		this.success = success;
		this.rows = rows;
	}
	
	public AccountResponse(boolean success,Object data) {
		this.success = success;
		this.data = data;
	}
	public AccountResponse(boolean success,String errorMsg) {
		this.success = success;
		this.errorMsg = errorMsg;
	}
	public static AccountResponse success(List<?> rows) {
		AccountResponse ret = new AccountResponse(true,rows);
		ret.total = rows.size();
		return ret;
	}
	
	public static AccountResponse success() {
		AccountResponse ret = new AccountResponse(true);
		return ret;
	}
	
	public static AccountResponse success(Object data) {
		AccountResponse ret = new AccountResponse(true,data);
		ret.success = true;
		return ret;
	}
	
	public static AccountResponse error(String errorMsg) {
		return new AccountResponse(false, errorMsg);
	}
	
	public static AccountResponse error(Throwable e) {
		AccountResponse resp = new AccountResponse(false, e.getMessage());
		resp.setThrowable(e);
		return resp;
	}
	
	public static AccountResponse error(int code, String errorMsg) {
		AccountResponse ret = new AccountResponse(false, errorMsg);
		ret.code = code;
		return ret;
	}
	public AccountResponse(boolean success,Field... fields) {
		this.success = success;
		this.fieldErros = new ArrayList<Field>(fields.length);
		for(Field field : fields)
			this.fieldErros.add(field);
	}
	

	public AccountResponse(Status status) {
		this.success = false;
		this.status = status;
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
