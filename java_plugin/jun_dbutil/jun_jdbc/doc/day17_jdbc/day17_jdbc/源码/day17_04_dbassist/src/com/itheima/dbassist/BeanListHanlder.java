package com.itheima.dbassist;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * 封装到JavaBean中
 * 满足约定：数据库字段名和JavaBean字段名保持一致
 * @author wzhting
 *
 */
public class BeanListHanlder implements ResultSetHandler {
	
	private Class clazz;//目标类型
	public BeanListHanlder(Class clazz){
		this.clazz = clazz;
	}

	public Object handle(ResultSet rs) {
		try {
			List list = new ArrayList();
			while(rs.next()){
				//有记录
				Object bean = clazz.newInstance();//目标对象
				//有多少列，列名和值又是什么？
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();//列数
				for(int i=0;i<count;i++){
					String fieldName = rsmd.getColumnName(i+1);//得到数据库字段名，也就得到了JavaBan的字段名
					Object fieldValue = rs.getObject(fieldName);//字段值
					//通过字段反射
					Field f = clazz.getDeclaredField(fieldName);
					f.setAccessible(true);
					f.set(bean, fieldValue);
				}
				list.add(bean);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
