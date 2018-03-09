package org.james.common.dao.domain;

public class JEasyuiColumn {
	private String field;
	private String title;
	private int width;
	
	public JEasyuiColumn() {
	}
	public JEasyuiColumn(String field,String title) {
		this.field = field;
		this.title = title;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
