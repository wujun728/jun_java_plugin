/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.mybatis;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.osmp.web.core.mybatis.MyBatisUtil.WhereColumn;
import com.osmp.web.core.mybatis.MybatisJsonInterceptor.ReflectUtil;

/**
 * 拼装SQL条件的模板
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-27 下午02:53:48
 */
public class CUDTemplate {

	private final static Logger log = Logger.getLogger(CUDTemplate.class);

	/**
	 * 根据实体类的一个或多个属性查询实体类 SQL模板
	 * 
	 * @param obj
	 * @return
	 */
	public String getObject(Object obj) {
		String tableName = MyBatisUtil.tablename(obj);
		BEGIN();
		SELECT("*");
		FROM(tableName);
		MyBatisUtil.createPropertyColmn(obj);// 创建属性实体映射关系，如果已经存在则从缓存当中拿
		List<WhereColumn> columns = MyBatisUtil.returnWhereColumnsName(obj);// 属性不为空的情况下将属性及值绑定到查询条件
		Map<String, String> map = MyBatisUtil.insertColumn.get(obj.getClass());
		if (columns.size() > 0) {// 拼装查询条件，此只针对单个实体的一个或多个属性
			StringBuffer sb = new StringBuffer(" 1=1 ");
			for (int i = 0; i < columns.size(); i++) {
				WhereColumn wc = columns.get(i);
				Object value = ReflectUtil.getFieldValue(obj, map.get(wc.name));

				if (wc.isString) {
					sb.append(" and ");
					sb.append(wc.name + " = ");
					sb.append("'" + StringEscapeUtils.escapeSql(value + "") + "'");
				} else {
					try {
						Integer tempVal = (Integer) value;
						if (!tempVal.equals(0)) {
							sb.append(" and ");
							sb.append(wc.name + " = ");
							sb.append(value);
						}
					} catch (Exception e) {
					}
				}
			}

			if (sb.length() == 5) {
				log.error("出现查询所有的情况");
				throw new IllegalArgumentException("getObject方法出现查询全表的情况");
			}

			WHERE(sb.toString());
		}
		return SQL();
	}

	/**
	 * 新增一个实体模板
	 * 
	 * @param obj
	 * @return
	 */
	public String insert(Object obj) {
		BEGIN();

		INSERT_INTO(MyBatisUtil.tablename(obj));
		MyBatisUtil.createPropertyColmn(obj);
		MyBatisUtil.caculationColumnList(obj, true);
		VALUES(MyBatisUtil.returnInsertColumnsName(obj), MyBatisUtil.returnInsertColumnsDefine(obj));

		return SQL();
	}

	/**
	 * 更新一个实体 根据ID更新 SQL模板
	 * 
	 * @param obj
	 * @return
	 */
	public String update(Object obj) {
		String idname = MyBatisUtil.id(obj);

		BEGIN();

		UPDATE(MyBatisUtil.tablename(obj));
		MyBatisUtil.createPropertyColmn(obj);
		MyBatisUtil.caculationColumnList(obj, false);
		SET(MyBatisUtil.returnUpdateSet(obj));
		WHERE(idname + "=#{" + idname + "}");

		return SQL();
	}

	/**
	 * 删除一个实体
	 * 
	 * @param obj
	 * @return
	 */
	public String delete(Object obj) {
		String idname = MyBatisUtil.id(obj);

		BEGIN();

		DELETE_FROM(MyBatisUtil.tablename(obj));
		WHERE(idname + "=#{" + idname + "}");

		return SQL();
	}

	/**
	 * 根据实体类生成通用分页查询SQL
	 * 
	 * @param map
	 * @return
	 */
	public String selectByPage(Map<?, ?> map) {
		BEGIN();
		SELECT("*");
		Class<?> clazz = (Class<?>) map.get("obj");
		Table table = (Table) clazz.getAnnotation(Table.class);
		String tableName = table.name();
		FROM(tableName);
		// List<WhereColumn> columns = obj.returnWhereColumnsName();
		// if(columns.size() > 0){
		// StringBuffer sb = new StringBuffer(" 1=1 ");
		// for(int i=0; i<columns.size(); i++){
		// WhereColumn wc = columns.get(i);
		// Object value = ReflectUtil.getFieldValue(obj, wc.name);
		// if(wc.isString){
		// sb.append(" and ");
		// sb.append(wc.name+" = ");
		// sb.append("'"+value+"'");
		// }else{
		// try{
		// Integer tempVal = (Integer) value;
		// if(!tempVal.equals(0)){
		// sb.append(" and ");
		// sb.append(wc.name+" = ");
		// sb.append(value);
		// }
		// }catch (Exception e) {}
		// }
		// }
		// WHERE(sb.toString());
		// }
		try {
			String where = (String) map.get("where");

			if (where.toUpperCase().indexOf("ORDER BY") != -1) {
				String orderBy = where.toUpperCase().substring(where.toUpperCase().indexOf("ORDER BY"),
						where.toUpperCase().length());
				if (null != orderBy && orderBy.length() > 0) {
					ORDER_BY(orderBy.replaceAll("ORDER BY", ""));
				}
				where = where.substring(0, where.toUpperCase().indexOf("ORDER BY"));
			}
			if (where.trim().length() > 0) {
				WHERE(where);
			}
		} catch (Exception e) {
		}
		return SQL();
	}

}
