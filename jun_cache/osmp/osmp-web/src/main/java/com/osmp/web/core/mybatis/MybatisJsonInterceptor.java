/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * Mybatis SQL语句数据集处理拦截器
 * 
 * @author wangkaiping
 * @version V1.0, 2013-5-17 下午11:43:16
 */
@Intercepts({ @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }) })
public class MybatisJsonInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(resultSetHandler,
				"mappedStatement");
		String mappedId = mappedStatement.getId();

		if (null != mappedId && mappedId.endsWith("selectByPage") || null != mappedId && mappedId.endsWith("getObject")
				|| null != mappedId && mappedId.endsWith("selectAll")) {
			DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) ReflectUtil.getFieldValue(
					resultSetHandler, "parameterHandler");
			Class<?> pojo = null;
			if (mappedId.endsWith("getObject")) {
				Object objTemp = defaultParameterHandler.getParameterObject();
				pojo = objTemp.getClass();
			} else {
				Map<?, ?> map = (Map<?, ?>) defaultParameterHandler.getParameterObject();
				pojo = (Class<?>) map.get("obj");
			}
			Field[] fields = pojo.getDeclaredFields();
			Statement statement = (Statement) invocation.getArgs()[0]; // 取得方法的参数Statement
			ResultSet rs = statement.getResultSet(); // 取得结果集
			List<Object> list = new ArrayList<Object>();
			while (rs.next()) {
				Object obj = pojo.newInstance();
				for (Field f : fields) {
					if (null != f.getAnnotation(Id.class) || null != f.getAnnotation(Column.class)) {
						Type clazz = f.getGenericType();
						Object o = null;
						String fieldName = f.getName();
						if (null != f.getAnnotation(Column.class)) {
							Column col = f.getAnnotation(Column.class);
							String cn = col.name();
							if (null != cn && cn.length() > 0) {
								fieldName = cn;
							}
						}
						if (clazz.equals(int.class)) {
							o = rs.getInt(fieldName);
						} else if (clazz.equals(Date.class)) {
							o = rs.getTimestamp(fieldName);
						} else {
							o = rs.getObject(fieldName);
						}
						ReflectUtil.setFieldValue(obj, f.getName(), o);
					}
				}
				list.add(obj);
			}
			return list;
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
	 * 反射工具类
	 * 
	 * @author wangkaiping
	 * @version V1.0, 2013-5-17 下午11:58:50
	 */
	public static class ReflectUtil {

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
		public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
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
