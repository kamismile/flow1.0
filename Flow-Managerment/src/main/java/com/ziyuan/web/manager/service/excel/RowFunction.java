package com.ziyuan.web.manager.service.excel;

/**
 * 应用于EXCEL导出器的 行输出后执行逻辑
 * @author yaohu
 *
 * @param <T>
 */
@FunctionalInterface
public interface RowFunction<T> {
	int apply(T instance,RowWrap row);
}
