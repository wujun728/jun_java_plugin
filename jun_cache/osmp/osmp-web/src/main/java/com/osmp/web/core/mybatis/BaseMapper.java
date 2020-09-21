/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.osmp.web.utils.Pagination;

/**
 * 通用 增、删、改、查 Mapper接口
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-27 下午03:08:07
 */
public interface BaseMapper<T> {

	/**
	 * 根据传入的实体通过ID得到实体类 ID不能为空
	 * 
	 * @param obj
	 * @return
	 */
	@SelectProvider(type = CUDTemplate.class, method = "getObject")
	public List<T> getObject(T obj);

	/**
	 * 单表通用对象查询 实体类包含子类的不支持
	 * 
	 * @param p分页组件
	 * @param obj
	 *            实体类对象
	 * @param where
	 *            查询条件，可以参考何余UserService封装的
	 * @return
	 */
	@SelectProvider(type = CUDTemplate.class, method = "selectByPage")
	public List<T> selectByPage(@Param("pagination") Pagination<T> p, @Param("obj") Class<T> obj,
			@Param("where") String where);

	/**
	 * 根据条件查询，不支持实体类包含子类的
	 * 
	 * @param obj
	 * @param where
	 * @return
	 */
	@SelectProvider(type = CUDTemplate.class, method = "selectByPage")
	public List<T> selectAll(@Param("obj") Class<T> obj, @Param("where") String where);

	/**
	 * Insert语句从CUDTemplate类中生成
	 * 
	 * @param obj
	 */
	@InsertProvider(type = CUDTemplate.class, method = "insert")
	// @SelectKey(keyProperty = "id", statement = {
	// "select #{SEQ}.nextval as id from dual" }, before = true, resultType =
	// Integer.class)
	public int insert(T obj);

	/**
	 * Update语句从CUDTemplate类中生成
	 * 
	 * @param obj
	 */
	@UpdateProvider(type = CUDTemplate.class, method = "update")
	public void update(T obj);

	/**
	 * Delete语句从CUDTemplate类中生成
	 * 
	 * @param obj
	 */
	@DeleteProvider(type = CUDTemplate.class, method = "delete")
	public void delete(T obj);

	// /**
	// * 根据id查找
	// * @param id
	// * @return
	// */
	// public T findById(Serializable id);
	//
	// public T find();

	// public List<T> findByPage(int start,int end,);

}
