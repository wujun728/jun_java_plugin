package com.jun.plugin.dbutils.dbutil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.ResultSetHandler;

public class BeanHandler implements ResultSetHandler {

	private Class clazz;

	public BeanHandler(Class clazz) {
		this.clazz = clazz;
	}

	// 将结果集的第一行数据封装到bean返回
	public Object handle(ResultSet rs) {
		try {
			if (rs.next()) {
				Object bean = this.clazz.newInstance();
				ResultSetMetaData metaData = rs.getMetaData();// 获取rs的元数据,该元数据可以获得列的数量和对应列索引处的列的名称值
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object value = rs.getObject(columnName);// 根据列的名称获取对应的值
					if (value instanceof Integer) {
						BeanUtils.setProperty(bean, columnName, (Integer) value);// 将值转化为对应的类型然后反射到对应对象属性中。
					} else if (value instanceof String) {
						BeanUtils.setProperty(bean, columnName, (String) value);
					} else if (value instanceof Date) {
						BeanUtils.setProperty(bean, columnName, (Date) value);
					} else if (value instanceof Boolean) {
						BeanUtils.setProperty(bean, columnName, (Boolean) value);
					} else if (value instanceof Float) {
						BeanUtils.setProperty(bean, columnName, (Float) value);
					}
				}
				return bean;
			}
			return null;
		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return rs;
	}
	
}
