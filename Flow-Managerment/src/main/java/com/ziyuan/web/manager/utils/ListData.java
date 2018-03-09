/**
 * @Version:
 * @author yangyl
 */
package com.ziyuan.web.manager.utils;

import java.io.Serializable;
import java.util.List;

//("分页数据")
public class ListData<Tp> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -372637723298070265L;

	/**
     * @return the list
     */
    public List<Tp> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Tp> list) {
        this.list = list;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    //("分页数据的列表")
    private List<Tp> list;
    
    //("分页数据总计记录数")
    private long total;

    //("分页数据总计分页数")
    private long totalPage;

    //("分页数据当前分页Index")
    private int page;

}
