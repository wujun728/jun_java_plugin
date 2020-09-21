package com.opensource.nredis.proxy.monitor.model.pagination;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
* 分页工具类，封装分页数据结果
* warn:不允许扩展
* warn:是基本的不可变类
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public final class PageList<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5413179540641872089L;

	/** 业务数据列表 */
    private final List<T> datas;

    /** 分页元信息 */
    private final Page page;

    /**
    * 封闭构造器（获得实例请使用静态方法），并初始化数据。
    */
    private PageList(List<T> datas, Page page) {
        if (null == datas)       //避免null对象
            datas = new LinkedList<T>();
        if (null == page)        //避免null对象
            page = Page.getZeroRecordCrmPage();
        this.datas = datas;
        this.page = page;
    }

    /**
    * 获得一个空的实例
    * @param <T>实例所含业务数据泛型
    */
    public static <T> PageList<T> getEmptyInstance() {
        return new PageList<T>(null, null);
    }

     /**
     * 获得一个包含完整数据的实例
     */
    public static <T> PageList<T> getInstance(List<T> datas, Page page) {
         if (null == datas)
            throw new RuntimeException("业务数据列表不能为空！");
         if (null == page)
            throw new RuntimeException("分页对象page不能为空！");
          return new PageList<T>(datas, page);
    }

    public List<T> getDatas() {
         return datas;
    }

    public Page getPage() {
         return page;
    }
}
