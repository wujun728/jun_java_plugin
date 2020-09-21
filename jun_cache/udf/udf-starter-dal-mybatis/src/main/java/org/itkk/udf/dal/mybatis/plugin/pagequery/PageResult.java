package org.itkk.udf.dal.mybatis.plugin.pagequery;

import java.util.ArrayList;

/**
 * <p>
 * ClassName: PageResult
 * </p>
 * <p>
 * Description: 分页类
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2013年12月2日
 * </p>
 */
public class PageResult<E> extends ArrayList<E> { //NOSONAR

    /**
     * 序列化标志
     */
    private static final long serialVersionUID = 1L;

    /**
     * 当前页,默认1
     */
    private int curPage = 1;

    /**
     * 每页数量,默认0
     */
    private int pageSize = 0;

    /**
     * 总页数
     */
    private long totalPages = 0;

    /**
     * 总条数
     */
    private long totalRecords = 0;

    /**
     * 只读属性,是否已经存在分页总条数,默认为false,当调用setTotalRecords方法的时候,会变更为true
     */
    private boolean hasTotalRecords = false;

    public int getCurPage() {
        return this.curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return this.totalRecords;
    }

    /**
     * <p>
     * Description: 设置总记录数
     * </p>
     *
     * @param totalRecords 总记录数
     */
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
        this.hasTotalRecords = true;
    }

    public boolean isHasTotalRecords() {
        return this.hasTotalRecords;
    }

    /**
     * <p>
     * Description: 计算页数
     * </p>
     *
     * @param page 页数
     * @param rows 行数
     * @return 结果
     */
    public static int getFistResult(int page, int rows) {
        return (page - 1) * rows;
    }

}
