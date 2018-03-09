package com.ziyuan.web.manager.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

public class ExcelUtil {
	private String sheetname;
	private ExcelRow header;
	private ExcelData rows;
	
	public ExcelUtil(String sheetname) {
		this.sheetname = sheetname;
	}
	
	/**
	 * 设置表头
	 * @param headerNames
	 */
	public void setHeaders(String... headerNames) {
		this.header = new ExcelRow(headerNames.length);
		Arrays.stream(headerNames).forEach(name -> header.add(name));
	}
	
	public <T> void setRows(List<T> beans) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Assert.notNull(beans);
		
		this.rows = new ExcelData(beans.size());
		
		if(beans.size() > 0) {
			T firstBean = beans.get(0);
			List<MethodDefine> methodList = getBeanColumnDefine(firstBean.getClass());
			
			for(T bean : beans) {
				ExcelRow row = new ExcelRow(methodList.size());
				for(MethodDefine methodDefine : methodList) {
					Class<?> returnType = methodDefine.method.getReturnType();
					Object returnObject = methodDefine.method.invoke(bean);
					String nullValue = methodDefine.nullValue;
					
					if(String.class.isAssignableFrom(returnType))
						row.add((String)returnObject,nullValue);
					else if(int.class.isAssignableFrom(returnType))
						row.add((int)returnObject);
					else if(float.class.isAssignableFrom(returnType))
						row.add((float)returnObject);
					else if(double.class.isAssignableFrom(returnType))
						row.add((double)returnObject);
					else if(boolean.class.isAssignableFrom(returnType))
						row.add((boolean)returnObject);
					else if(Timestamp.class.isAssignableFrom(returnType))
						row.add((Timestamp)returnObject,nullValue);
					else
						row.addUseObject(returnObject,nullValue);
				}
				
				this.rows.add(row);
			}			
		}
	}
	
	/**
	 * 获取指定注解的方法
	 * @param clazz
	 * @return
	 */
	private <T> List<MethodDefine> getBeanColumnDefine(Class<T> clazz) {
		Method[] methods = clazz.getMethods();

		return Arrays.stream(methods)
			.filter(method -> method.getAnnotation(ExcelColumn.class) != null)
			.sorted((m1,m2) -> {
				ExcelColumn ec1 = m1.getAnnotation(ExcelColumn.class);
				ExcelColumn ec2 = m2.getAnnotation(ExcelColumn.class);
				
				return ec1.sort() > ec2.sort() ? -1 : 1;
			})
			.map(method -> {
				ExcelColumn ec = method.getAnnotation(ExcelColumn.class);
				return new MethodDefine(method, ec.nullValue());
			})
			.collect(Collectors.toList());
	}
	
	/**
	 * 输出
	 * @return
	 */
	public ByteArrayOutputStream writeToXLSX() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(sheetname);
		XSSFRow row = sheet1.createRow(0);
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		XSSFCell cell;

		for (int i = 0; i < header.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(header.get(i));
			cell.setCellStyle(style);
		}
		if (rows != null) {
			for (int n = 0; n < rows.size(); n++) {
				row = sheet1.createRow(n + 1);
				ExcelRow datarow = rows.get(n);
				for (int m = 0; m < datarow.size(); m++) {
					cell = row.createCell(m);
					cell.setCellValue(datarow.get(m));
					cell.setCellStyle(style);
				}
			}
		}

		wb.write(out);
		
		return out;
	}
	
	public String getSheetname() {
		return sheetname;
	}
	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}
	public ExcelRow getHeader() {
		return header;
	}
	public void setHeader(ExcelRow header) {
		this.header = header;
	}
	public ExcelData getRows() {
		return rows;
	}
	public void setRows(ExcelData rows) {
		this.rows = rows;
	}
	
	
	private class MethodDefine {
		Method method;
		String nullValue;
		MethodDefine(Method method,String nullValue) {
			this.method = method;
			this.nullValue = nullValue;
		}
	}
}
