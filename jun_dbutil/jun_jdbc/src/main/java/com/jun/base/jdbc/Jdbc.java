package com.jun.base.jdbc;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Jdbc {

	/**
	 * 不带参数执行
	 * 
	 * @param sql
	 * @return
	 */
	int excuteUpdate(String sql) throws SQLException;

	/**
	 * 带参数执行
	 * 
	 * @param sql
	 * @param params
	 *            参数处理
	 * @return
	 */
	int excuteUpdate(String sql, ParamsHandler params) throws SQLException;

	/**
	 * 查询,返回一条记录
	 * 
	 * @param sql
	 * @param handle
	 */
	<T> T query(String sql, ResultHandler<T> handle) throws SQLException;

	/**
	 * 带参数查询,返回一条记录
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param handle
	 */
	<T> T query(String sql, ParamsHandler params, ResultHandler<T> handle) throws SQLException;

	/**
	 * 带参数查询,返回多条记录
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param handle
	 */
	<T> List<T> queryForList(String sql, ParamsHandler params, ResultHandler<T> handle) throws SQLException;

	/**
	 * 带参数查询,返回多条记录
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param handle
	 */
	<T> List<T> queryForList(String sql, ResultHandler<T> handle) throws SQLException;

	/**
	 * 带参数查询结果只有一个值
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	<T> T queryUniqueResult(String sql, ParamsHandler params, Type clazz) throws SQLException;

	/**
	 * 查询结果只有一个值
	 * @param sql
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	<T> T queryUniqueResult(String sql, Type clazz) throws SQLException;

	/**
	 * 获得链接
	 * 
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection() throws SQLException;
}
