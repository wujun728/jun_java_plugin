package com.opensource.nredis.proxy.monitor.model.pagination;

import java.io.Serializable;

/**
* 分页工具类，封装分页元信息
* warn:不允许扩展
* warn:是基本的不可变类
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public final class Page implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5565054914458392441L;
	/** 第一页 */
    private int firstPage;
    /** 最后一页 */
    private int lastPage;
    /** 下一页 */
    private int nextPage;
    /** 上一页 */
    private int prevPage;
    /** 当前页 */
    private int currentPage;
    /** 总页数 */
    private int totalPage;
    /** 总记录数 */
    private int rowCount;
    /** 每页记录数 */
    private int pageSize;
    /** 是否有下一页 */
    private boolean hasNext;
    /** 是否有上一页 */
    private boolean hasPrev;
    /** 是否有第一页 */
    private boolean hasFirst;
    /** 是否有最后一页 */
    private boolean hasLast;
    /** 显示切页按钮数 */
    private static final int BUTTON_COUNT = 5;

    /**
    * 无参构造器，必须初始化数据
    */
    private Page() {
        this(1, PageAttribute.DEFAULT_PAGE_SIZE, 0);
    }
    /**
    * @param currentPage 当前页
    * @param pageSize 每页记录数
    * @param rowCount 总记录数
    */
    private Page(int currentPage, int pageSize, int rowCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.rowCount = rowCount;
        this.totalPage = this.rowCount % pageSize == 0 ? this.rowCount / pageSize : this.rowCount / pageSize + 1 ;
        if(this.totalPage > 0){
            this.hasFirst = true ;
            this.firstPage = currentPage - BUTTON_COUNT/2;
            if (this.firstPage < 1)
            this.firstPage = 1 ;
        }
        if(this.currentPage  > 1 ){
            this.hasPrev = true ;
            this.prevPage = this.currentPage - 1;
        }
        if(this.totalPage > 0 && this.currentPage < this.totalPage){
            this.hasNext = true;
            this.nextPage = this.currentPage + 1 ;
        }
        if(this.totalPage > 0){
            this.hasLast = true;
            this.lastPage = this.firstPage + BUTTON_COUNT - 1;
            if (this.lastPage > this.totalPage)
            this.lastPage = this.totalPage;
        }
    }

    /**
    * 获得一个记录数为0的page对象
    */
    public static Page getZeroRecordCrmPage() {
        return new Page();
    }

    /**
    * 获得一个完整的的page对象
    */
    public static Page getInstance(PageAttribute pageAttr, int rowCount) {
        return new Page(pageAttr.getPageIndex(), pageAttr.getPageSize(), rowCount);
    }

    public int getFirstPage() {
        return firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public boolean getHasPrev() {
        return hasPrev;
    }

    public boolean getHasFirst() {
        return hasFirst;
    }

    public boolean getHasLast() {
        return hasLast;
    }
}

