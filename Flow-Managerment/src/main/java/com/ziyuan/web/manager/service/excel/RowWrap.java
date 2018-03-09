package com.ziyuan.web.manager.service.excel;

import java.sql.Timestamp;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.shziyuan.flow.global.util.TimestampUtil;

/**
 * 用于包装POI的Row
 * 增加自定位Row与Cell功能
 * @author yaohu
 *
 */
public class RowWrap {
	private Sheet sheet;
	
	private int rowStartIndex;
	private Row row;
	
	private int cellIndex;
	
	public RowWrap(Sheet sheet,int rowStartIndex) {
		this.sheet = sheet;
		this.rowStartIndex = rowStartIndex;
		this.newRow();
	}
	
	public void newRow() {
		this.row = sheet.createRow(rowStartIndex ++);
		this.cellIndex = 0;
	}
	
	private Cell getCell() {
		return row.createCell(cellIndex ++);
	}
	public void add(String value) {
		getCell().setCellValue(value != null ? value : "");
	}
	
	public void add(int value) {
		getCell().setCellValue(Integer.toString(value));
	}
	
	public void add(Integer value) {
		getCell().setCellValue(value != null ? value.toString() : "");
	}
	
	public void add(boolean value) {
		getCell().setCellValue(value ? "是" : "否");
	}
	
	public void add(double value) {
		getCell().setCellValue(Double.toString(value));
	}
	
	public void add(Double value) {
		getCell().setCellValue(value != null ? value.toString() : "");
	}
	
	public void add(Timestamp timestamp) {
		getCell().setCellValue(timestamp != null ? TimestampUtil.excelString(timestamp) : "");
	}
}
