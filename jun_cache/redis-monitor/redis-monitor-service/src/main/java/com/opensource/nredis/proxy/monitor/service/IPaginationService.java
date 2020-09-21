package com.opensource.nredis.proxy.monitor.service;

import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;

import java.util.Map;

/**
* 分页业务公共接口
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public interface IPaginationService<T> {

    /**
    * 根据过滤条件查询出数据列表（用于分页）
    * @param page 分页参数对象
    * @param queryObject 过滤条件对象
    * @param otherParam 其它过滤条件
    */
    PageList<T> queryEntityPageList(PageAttribute page, T queryObject, Map<String, Object> otherParam);
}
