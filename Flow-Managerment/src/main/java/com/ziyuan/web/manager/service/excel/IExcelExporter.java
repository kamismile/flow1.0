package com.ziyuan.web.manager.service.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface IExcelExporter<T> {
	public ByteArrayOutputStream export(List<T> datas) throws IOException;
}
