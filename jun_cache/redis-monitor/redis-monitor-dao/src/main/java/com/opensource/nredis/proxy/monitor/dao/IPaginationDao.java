package com.opensource.nredis.proxy.monitor.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
/**
* 分页接口
* 定义了DAO分页功能的规范
* 使用说明：DAO如果有分页功能，则继承该接口
*
* @author liubing
* @date 2016/12/22 20:05
* @version v1.0.0
*/
public interface IPaginationDao<T> {

    /**
    * 根据过滤条件查询出数据列表（用于分页）
    * @param page 分页参数对象
    * @param queryObject 过滤条件对象
    * @param otherParam 其它过滤条件
    */
    List<T> queryPageList( @Param("page")PageAttribute page, @Param("queryObject")T queryObject);

    /**
    * 根据过滤条件统计记录条数（用于分页）
    * @param queryObject 过滤条件对象
    * @param otherParam 其它过滤条件
    */
    int count(@Param("queryObject")T queryObject);

}
