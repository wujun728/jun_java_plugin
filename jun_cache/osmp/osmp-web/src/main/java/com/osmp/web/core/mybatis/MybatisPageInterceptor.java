/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.osmp.web.utils.Pagination;

/**
 * Mybatis分页拦截组件
 * 
 * @author wangkaiping
 * @version V1.0, 2013-5-17 下午11:43:16
 */
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class MybatisPageInterceptor implements Interceptor {

	private String databaseType;

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");

		BoundSql boundSql = delegate.getBoundSql();
		// 拿到传入的参数分页实体类
		Object obj = boundSql.getParameterObject();
		Object pagParme = obj;
		boolean flag = false;
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> objMap = (Map<?, ?>) obj;
			for (Entry<?, ?> entry : objMap.entrySet()) {
				Object val = entry.getValue();
				if (val instanceof Pagination<?>) {
					pagParme = val;
					flag = true;
					break;
				}
			}

		}

		if (obj instanceof Pagination<?> || flag) {
			Pagination<?> page = (Pagination<?>) pagParme;
			// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
			// 拦截到的prepare方法参数是一个Connection对象
			Connection connection = (Connection) invocation.getArgs()[0];
			// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
			String sql = boundSql.getSql();
			// 给当前的page参数对象设置总记录数
			this.setTotalRecord(obj, mappedStatement, connection, page);
			// 获取分页Sql语句
			String pageSql = this.getPageSql(page, sql);
			// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
			ReflectUtil.setFieldValue(boundSql, "sql", pageSql);

			// 动态值绑定通过此进行反射等操作不好处理。看是否可以直接拦截
			// TypeHandlerRegistry registry = (TypeHandlerRegistry)
			// ReflectUtil.getFieldValue(delegate, "typeHandlerRegistry");
			// TypeHandler typeHandler = registry.getTypeHandler(Integer.class);
			// List<ParameterMapping> parameterMappings = new
			// ArrayList<ParameterMapping>();
			// new ParameterMapping.Builder(null, "", typeHandler);
			//
			// int offset = (page.getCurPage() - 1) * page.getPageSize() + 1;
			// typeHandler.setParameter((PreparedStatement) delegate, 1,
			// offset+page.getPageSize(), JdbcType.INTEGER);
			// typeHandler.setParameter((PreparedStatement) delegate, 2, offset,
			// JdbcType.INTEGER);
			//
			//
			// MetaObject metaStatementHandler = MetaObject.forObject(handler);
			// Configuration configuration = (Configuration)
			// metaStatementHandler.getValue("delegate.configuration");
			// ParameterMapping.Builder builder = new
			// ParameterMapping.Builder(configuration, "curPage",
			// Integer.class);
			// boundSql.getParameterMappings().add(builder.build());
			// boundSql.setAdditionalParameter("curPage", page.getCurPage());
			//
			// builder = new ParameterMapping.Builder(configuration,
			// "pageSize",Integer.class);
			// boundSql.getParameterMappings().add(builder.build());
			// boundSql.setAdditionalParameter("pageSize", page.getPageSize());

		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println(properties.getProperty("databaseType"));
	}

	/**
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
	 * 
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return
	 */
	private String getPageSql(Pagination<?> page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return getMysqlPageSql(page, sqlBuffer);
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return getOraclePageSql(page, sqlBuffer);
		}
		return sqlBuffer.toString();
	}

	/**
	 * 获取Oracle数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Oracle数据库的分页查询语句
	 */
	private String getOraclePageSql(Pagination<?> page, StringBuffer sqlBuffer) {
		int offset = (page.getCurPage() - 1) * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
				.append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		// sqlBuffer.insert(0,
		// "select u.*, rownum r from (").append(") u where rownum < ").append("?");
		// sqlBuffer.insert(0,
		// "select * from (").append(") where r >= ").append("?");
		// select * from (select u.*, rownum r from (select * from t_user) u
		// where rownum < 31) where r >= 16
		// System.out.println("Mybatis拦截分页插件SQL语句 : ");
		// System.out.println(sqlBuffer.toString());
		return sqlBuffer.toString();
	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 */
	private String getMysqlPageSql(Pagination<?> page, StringBuffer sqlBuffer) {
		int offset = (page.getCurPage() - 1) * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	}

	/**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @param page
	 *            Mapper映射语句对应的参数对象
	 * @param mappedStatement
	 *            Mapper映射语句
	 * @param connection
	 *            当前的数据库连接
	 */
	private void setTotalRecord(Object obj, MappedStatement mappedStatement, Connection connection, Pagination<?> page) {
		BoundSql boundSql = mappedStatement.getBoundSql(obj);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, obj);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, obj, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				page.setTotal(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private String getCountSql(String sql) {
		// int index = sql.indexOf("from");
		// if (-1 == index){
		// index = sql.indexOf("FROM");
		// }
		return "select count(0) from (" + sql + ") as a";
	}

	/**
	 * 反射工具类
	 * 
	 * @author wangkaiping
	 * @version V1.0, 2013-5-17 下午11:58:50
	 */
	private static class ReflectUtil {

		/**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标属性的值
		 */
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标字段
		 */
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		/**
		 * 利用反射设置指定对象的指定属性为指定的值
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @param fieldValue
		 *            目标值
		 */
		public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
