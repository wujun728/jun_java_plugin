package com.itstyle.quartz.dynamicquery;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * 扩展SpringDataJpa, 支持动态jpql/nativesql查询并支持分页查询
 * 使用方法：注入ServiceImpl
 * 创建者 张志朋
 * 创建时间	2018年3月8日
 */
public interface DynamicQuery {

	 void save(Object entity);

	 void update(Object entity);

	 <T> void delete(Class<T> entityClass, Object entityid);

	 <T> void delete(Class<T> entityClass, Object[] entityids);
	
	/**
	 * 执行nativeSql统计查询
	 * @param nativeSql
	 * @param params 占位符参数(例如?1)绑定的参数值
	 * @return 统计条数
	 */
	Long nativeQueryCount(String nativeSql, Object... params);

    /**
     * 执行nativeSql分页查询
     * @param resultClass
     * @param pageable
     * @param nativeSql
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> nativeQueryPagingList(Class<T> resultClass, Pageable pageable, String nativeSql, Object... params);


}
