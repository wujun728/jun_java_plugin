package com.jun.plugin.dbutils.dbutil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.ResultSetHandler;

public class BeanListHandler implements ResultSetHandler {

	private Class clazz;

	public BeanListHandler(Class clazz) {
		this.clazz = clazz;
	}

	// 将结果集的每一行封装到bean，将bean加入一个list返回
	public Object handle(ResultSet rs) {
		try {
			List list = new ArrayList();
			while (rs.next()) {
				Object bean = this.clazz.newInstance();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object value = rs.getObject(columnName);

					if (value instanceof Integer) {
						BeanUtils.setProperty(bean, columnName, (Integer) value);
					} else if (value instanceof String) {
						BeanUtils.setProperty(bean, columnName, (String) value);
					} else if (value instanceof Date) {
						BeanUtils.setProperty(bean, columnName, (Date) value);
					} else if (value instanceof Boolean) {
						BeanUtils.setProperty(bean, columnName, (Boolean) value);
					}
				}
				list.add(bean);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}	
	
}