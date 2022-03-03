package org.springrain.frame.util;

import java.io.Serializable;

/**
 * page 分页对象
 *
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.util.Page
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Integer DEFAULT_PAGE_SIZE = 20;
    /** 页码,从1开始 */
    private Integer pageIndex = 1;
    /** 每页多少行 */
    private Integer pageSize = 20;
    /** 数据总行数 */
    private Integer totalCount = 0;
    /** 总共可以分多少页 */
    private Integer pageCount = 1;
    /** 排序方式 desc asc */
    private String sort;
    /** 排序字段 */
    private String order;
    /** 第一页 */
    private Boolean firstPage = false;
    /** 上一页 */
    private Boolean hasPrev = false;
    /** 下一页 */
    private Boolean hasNext = false;
    /** 最末页 */
    private Boolean lastPage = false;

    // 是否查询 分页的总条数,默认进行查询
    private Boolean selectpagecount = true;

    /**
     * 分页信息,默认每页20行数据
     * 
     * @param pageIndex
     *            页码,从1开始
     */
    public Page(Integer pageIndex) {
        this(pageIndex, DEFAULT_PAGE_SIZE);
    }

    public Page() {

    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 分页信息
     * 
     * @param pageIndex
     *            页码,从1开始
     * @param pageSize
     *            每页多少行,默认为 20
     */
    public Page(Integer pageIndex, Integer pageSize) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    /**
     * 获取当前页页码
     * 
     * @return
     */
    public Integer getPageIndex() {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return pageIndex;
    }

    /**
     * 获取每页多少行
     * 
     * @return
     */
    public Integer getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return pageSize;
    }

    /**
     * 获取总共有多少页
     * 
     * @return
     */
    public Integer getPageCount() {

        return pageCount;
    }

    /**
     * 获取起始行数
     * 
     * @return
     */
    public Integer getFirstResult() {
        return (pageIndex - 1) * pageSize;
    }

    /**
     * 获取总记录数
     * 
     * @return
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数
     * 
     * @param totalCount
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.pageCount = (totalCount + pageSize - 1) / pageSize;

        if (pageIndex >= pageCount) {
            this.lastPage = true;
        } else {
            this.hasNext = true;
        }

        if (getPageIndex() > 1) {
            this.hasPrev = true;
        } else {
            this.firstPage = true;
        }

        /*
         * // 调整页码信息,防止出界 if (totalCount == 0) { if (pageIndex != 1) pageIndex = 1; }
         * else { if (pageIndex > pageCount) pageIndex = pageCount; }
         */
    }

    /**
     * 是否有数据
     * 
     * @return
     */
    public Boolean isEmpty() {
        return totalCount < 1;
    }

    /**
     * 设置页面大小
     * 
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 排序方式 desc asc
     * 
     * @return String
     */
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 排序字段 例如 id
     * 
     * @return String
     */
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 是否是第一页
     * 
     * @return boolean
     */
    public Boolean getFirstPage() {
        return firstPage;

    }

    /**
     * 是否有上一页
     * 
     * @return boolean
     */
    public Boolean getHasPrev() {
        return hasPrev;
    }

    /**
     * 是否是最后一页
     * 
     * @return boolean
     */
    public Boolean getLastPage() {

        return lastPage;
    }

    /**
     * 是否有下一页
     * 
     * @return boolean
     */
    public Boolean getHasNext() {

        return hasNext;
    }

    public Boolean getSelectpagecount() {
        return selectpagecount;
    }

    public void setSelectpagecount(boolean selectpagecount) {
        this.selectpagecount = selectpagecount;
    }

    public Boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("pageIndex[").append(getPageIndex()).append("],").append("sort[")
                .append(getSort()).append("],").append("order[").append(getOrder()).append("]").toString();
    }

}
