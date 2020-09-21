package com.opensource.nredis.proxy.monitor.model.pagination;

import java.io.Serializable;

/**
* 分页工具类，封装分页的基本参数
* warn:不允许扩展
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public final class PageAttribute implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8579791322108263537L;

	public static final int DEFAULT_PAGE_INDEX = 1;

    /** 默认每页记录数 */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /** 不分页时查询记录数*/
    public static final int MAX_PAGE_SIZE = 9999;

    /** 参数：页码 */
    private int pageIndex = DEFAULT_PAGE_INDEX;
    /** 参数：每页记录数 */
    private int pageSize;
    /** 参数：查询记录开始下标 */
    private int begin;

    /**
    * 封闭无参构造器，使数据初始化完整
    * @param pageIndex 当前页码
    * @param pageSize 每页记录数
    */
    public PageAttribute(Integer pageIndex, Integer pageSize) {
        this.pageIndex = (pageIndex != null && pageIndex > 0) ? pageIndex : DEFAULT_PAGE_INDEX;
        this.pageSize = (pageSize != null && pageSize > 0) ? pageSize : DEFAULT_PAGE_SIZE;
        this.begin = (this.pageIndex - 1) * this.pageSize;
    }

    /**
    * 封闭无参构造器，使数据初始化完整
    * @param pageIndex 当前页码
    * @param pageSize 每页记录数
    */
    public PageAttribute(Integer pageIndex, Integer pageSize, Integer offset) {
        this.pageIndex = (pageIndex != null && pageIndex > 0) ? pageIndex : DEFAULT_PAGE_INDEX;
        this.pageSize = (pageSize != null && pageSize > 0) ? pageSize : DEFAULT_PAGE_SIZE;
        this.begin = offset + (this.pageIndex - 1) * this.pageSize;
    }

    /**
    * 获取默认分页参数(10页)
    * @param pageIndex
    * @return
    */
    public static PageAttribute getInstance(Integer pageIndex){
        return new PageAttribute(pageIndex, null);
    }

    /**
    * 不分页查询限制记录条数(避免内存撑满)
    * @return
    */
    public static PageAttribute getInstance(){
        return new PageAttribute(DEFAULT_PAGE_INDEX, MAX_PAGE_SIZE);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getBegin() {
        return begin;
    }

}

