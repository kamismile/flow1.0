package com.ziyuan.web.manager.service.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.Assert;

/**
 * 通用EXCEL导出器
 * @author yaohu
 *
 * @param <T>
 */
public class NormallyExcelExporter<T> implements IExcelExporter<T>{
	private String[] headers;
	private String sheetname;
	private RowFunction<T> rowAction;
	
	private Workbook wb;
	
	/**
	 * EXCEL输出器,单页形式
	 * @param sheetname 标签页名称
	 * @param headers	头部标签项
	 * @param rowAction	行产生行为,{@link RowFunction}
	 */
	public NormallyExcelExporter(String sheetname,String[] headers,RowFunction<T> rowAction,int flushSize) {
		this.headers = headers;
		this.sheetname = sheetname;
		this.rowAction = rowAction;
		
		this.wb = new SXSSFWorkbook(flushSize);
	}
	/**
	 * EXCEL输出器,单页形式
	 * @param sheetname 标签页名称
	 * @param headers	头部标签项
	 * @param rowAction	行产生行为,{@link RowFunction}
	 */
	public NormallyExcelExporter(String sheetname,String[] headers,RowFunction<T> rowAction) {
		this(sheetname,headers,rowAction,100);
	}
	
	
	public Sheet createHeaderSheet() {
		// 写入标签行
		Sheet sheet = wb.createSheet(sheetname);
		Row headRow = sheet.createRow(0);
		for(int index = 0; index < headers.length; ++ index) {
			Cell cell = headRow.createCell(index);
			cell.setCellValue(headers[index]);
		}
		
		return sheet;
	}
	public ByteArrayOutputStream export(List<T> datas) throws IOException {
		Assert.notEmpty(datas);
		
		Sheet sheet = createHeaderSheet();
		
		int offset = 0;
		for(int i = 0; i < datas.size(); ++i) {
			int rowIndex = i + offset + 1;
			offset = rowAction.apply(datas.get(i), new RowWrap(sheet,rowIndex));
		}
		
		return write();
	}
	public ByteArrayOutputStream write() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		
		return out;
	}
	
}
