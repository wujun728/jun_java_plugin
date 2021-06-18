#针对小型项目的MySql的JDBC封装

功能非常小，有需要的可以作为参考，或者扩展

#目前已经实现的接口


```java
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
	int excuteUpdate(String sql, ParamsHandle params) throws SQLException;

	/**
	 * 查询,返回一条记录
	 * 
	 * @param sql
	 * @param handle
	 */
	<T> T query(String sql, ResultHandle<T> handle) throws SQLException;

	/**
	 * 带参数查询,返回一条记录
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param handle
	 */
	<T> T query(String sql, ParamsHandle params, ResultHandle<T> handle) throws SQLException;

	/**
	 * 带参数查询,返回多条记录
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param handle
	 */
	<T> List<T> queryForList(String sql, ParamsHandle params, ResultHandle<T> handle) throws SQLException;

	/**
	 * 带参数查询,返回多条记录
	 * 
	 * @param <T>
	 * @param sql
	 * @param params
	 * @param handle
	 */
	<T> List<T> queryForList(String sql, ResultHandle<T> handle) throws SQLException;

	/**
	 * 带参数查询结果只有一个值
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	<T> T queryUniqueResult(String sql, ParamsHandle params, Type clazz) throws SQLException;

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
```