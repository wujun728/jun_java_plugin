package io.github.wujun728.db.utils;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import static io.github.wujun728.db.record.DbPro.getPkNames;


public class SqlUtils {

	private static final String SELECT = "SELECT * FROM ";
	private static final String SELECTCOUNT = "SELECT COUNT(*) ";
	private static final String INSERT = "INSERT INTO ";
	private static final String UPDATE = "UPDATE ";
	private static final String SET = " SET ";
	private static final String WHERE = " WHERE ";
	private static final String WHEREOK = " WHERE 1=1";
	private static final String WHERENO = " WHERE 1=2";
	private static final String DELETE = "DELETE FROM ";
	private static final String LEFT = "(";
	private static final String VALUES = ") VALUES (";
	private static final String RIGHT = ")";
	private static final String AND = " AND ";
	private static final String OR = " OR ";
	private static final String LINK = ",";
	private static final String OCCUPY = "?,";
	private static final String EQUAL = "=?";
	private static final String EQUAL_LINK = "=?,";
	private static final String EQUAL_AND = "=? AND ";
	private static final String FROM = "FROM";
	private static final String ORDER = "ORDER BY";
	private static final String LIMIT = " LIMIT ";
	private static final String order = "order by";
	private static final String limit = " limit ";
	private static final String from = "from";
	private static final String EMPTY = "";
	private static final String R_ORDER = "ORDER.*";
	private static final String R_LIMIT = "LIMIT.*";


	public static String getTableName(Object bean) {
		String tableName = "";
		if(bean instanceof Class){
			tableName = ((Class)bean).getSimpleName();
			tableName = FieldUtils.getUnderlineName(tableName);
			Class clazz = (Class) bean;
			if(AnnotationUtil.hasAnnotation(clazz,Table.class)){
				tableName = AnnotationUtil.getAnnotationValue(clazz,Table.class,"name");
			}else if(AnnotationUtil.hasAnnotation(clazz,TableName.class)){
				tableName = AnnotationUtil.getAnnotationValue(clazz,TableName.class,"value");
			}/*else if(AnnotationUtil.hasAnnotation(clazz,Entity.class)){
				tableName = AnnotationUtil.getAnnotationValue(clazz, Entity.class,"table");
			}*/
		}else{
			tableName = FieldUtils.getUnderlineName(bean.getClass().getSimpleName());
			if(AnnotationUtil.hasAnnotation(bean.getClass(),Table.class)){
				tableName = AnnotationUtil.getAnnotationValue(bean.getClass(),Table.class,"name");
			}else if(AnnotationUtil.hasAnnotation(bean.getClass(),TableName.class)){
				tableName = AnnotationUtil.getAnnotationValue(bean.getClass(),TableName.class,"value");
			}/*else if(AnnotationUtil.hasAnnotation(bean.getClass(),Entity.class)){
				tableName = AnnotationUtil.getAnnotationValue(bean.getClass(), Entity.class,"table");
			}*/
		}
		return tableName;
	}
	/**
	 * 构建insert语句
	 *
	 * @param bean
	 *            实体映射对象
	 * @return sql上下文
	 */
	public static SqlContext getInsert(Object bean) {
		List<Object> values = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		StringBuilder cols = new StringBuilder();
		StringBuilder placeholder = new StringBuilder();
		try {
			Class<?> cls = bean.getClass();
			for (Field field : cls.getDeclaredFields()) {
				field.setAccessible(true);
				Object val = field.get(bean);
				if (val != null) {
					//Mark Mysql
					String columndName = FieldUtils.getUnderlineName(field.getName());
					String columndNameNew = '`'+columndName+'`';
					if(AnnotationUtil.hasAnnotation(field, Transient.class)){
						continue;
					}
					if(AnnotationUtil.hasAnnotation(field,TableField.class)){
						columndName = AnnotationUtil.getAnnotationValue(field,TableField.class,"value");
						Boolean existFlag  = AnnotationUtil.getAnnotationValue(field,TableField.class,"exist");
						if(existFlag==false){
							continue;
						}
						columndNameNew = '`'+columndName+'`';
					}else if(AnnotationUtil.hasAnnotation(field, Column.class)){
						columndName = AnnotationUtil.getAnnotationValue(field,Column.class,"name");
						columndNameNew = '`'+columndName+'`';
					}/*else if(AnnotationUtil.hasAnnotation(field, io.github.wujun728.db.orm.annotation.Column.class)){
						columndName = AnnotationUtil.getAnnotationValue(field, io.github.wujun728.db.orm.annotation.Column.class,"name");
						columndNameNew = '`'+columndName+'`';
					}*/
					cols.append(columndNameNew).append(LINK);
					placeholder.append(OCCUPY);
					values.add(val);
				}
			}
			if(cols.length()>0){
				cols.deleteCharAt(cols.length() - 1);
			}
			if(placeholder.length()>0){
				placeholder.deleteCharAt(placeholder.length() - 1);
			}
			sql.append(INSERT);
			String tableName = getTableName(bean);
			sql.append(tableName);
			sql.append(LEFT);
			sql.append(cols);
			sql.append(VALUES);
			sql.append(placeholder);
			sql.append(RIGHT);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new SqlContext(sql, values.toArray());
	}

	/**
	 * 构建update语句
	 * 
	 * @param bean
	 *            实体映射对象
	 * @return sql上下文
	 */
	public static SqlContext getUpdate(Object bean) {
		List<Object> values = new ArrayList<Object>();
		List<Object> wheresValue = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		StringBuilder cols = new StringBuilder();
		StringBuilder where = new StringBuilder();
		String tableName = getTableName(bean);
		String primaryKeyStr = getPkNames(tableName);
		try {
			Class<?> cls = bean.getClass();
			for (Field field : cls.getDeclaredFields()) {
				field.setAccessible(true);
				Object val = field.get(bean);
				if (val != null) {
					//Mark Mysql
					String columndName = FieldUtils.getUnderlineName(field.getName());
					String columndNameNew = '`'+columndName+'`';
					if (/*field.isAnnotationPresent(PK.class)  ||*/ /*field.isAnnotationPresent(Id.class)*/ AnnotationUtil.hasAnnotation(field,Id.class)
							|| /*field.isAnnotationPresent(TableId.class)*/  AnnotationUtil.hasAnnotation(field,TableId.class)  || primaryKeyStr.contains(columndName)) {
						/*if(AnnotationUtil.hasAnnotation(field, io.github.wujun728.db.orm.annotation.Column.class)){
							columndName = AnnotationUtil.getAnnotationValue(field, io.github.wujun728.db.orm.annotation.Column.class,"name");
							columndNameNew = '`'+columndName+'`';
						}*/
						where.append(columndNameNew).append(EQUAL_AND);
						wheresValue.add(val);
					} else {
						if(AnnotationUtil.hasAnnotation(field, Transient.class)){
							continue;
						}
						if(AnnotationUtil.hasAnnotation(field,TableField.class)){
							columndName = AnnotationUtil.getAnnotationValue(field,TableField.class,"value");
							Boolean existFlag  = AnnotationUtil.getAnnotationValue(field,TableField.class,"exist");
							if(existFlag==false){
								continue;
							}
							columndNameNew = '`'+columndName+'`';
							cols.append(columndNameNew).append(EQUAL_LINK);
							values.add(val);
						}else if(AnnotationUtil.hasAnnotation(field, Column.class)){
							columndName = AnnotationUtil.getAnnotationValue(field,Column.class,"name");
							columndNameNew = '`'+columndName+'`';
							cols.append(columndNameNew).append(EQUAL_LINK);
							values.add(val);
						}/*else if(AnnotationUtil.hasAnnotation(field, io.github.wujun728.db.orm.annotation.Column.class)){
							columndName = AnnotationUtil.getAnnotationValue(field, io.github.wujun728.db.orm.annotation.Column.class,"name");
							columndNameNew = '`'+columndName+'`';
							cols.append(columndNameNew).append(EQUAL_LINK);
							values.add(val);
						}*/else{
							cols.append(columndNameNew).append(EQUAL_LINK);
							values.add(val);
						}
					}
				}
			}
			if(StrUtil.isEmpty(cols)){
				throw new RuntimeException("待更新的列不能为空！");
			}
			if(StrUtil.isEmpty(where)){
				throw new RuntimeException("待更新的主键列不能为空(无主键建议写SQL直接执行)！");
			}
			cols.deleteCharAt(cols.length() - 1);
			where.delete(where.length() - 4, where.length());
			values.addAll(wheresValue);
			sql.append(UPDATE);
			sql.append(tableName);
			sql.append(SET);
			sql.append(cols);
			sql.append(WHERE);
			sql.append(where);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new SqlContext(sql, values.toArray());
	}

	private static String getPkNames(String tableName) {
		return "id";
	}


	/**
	 * 构建delete语句（删除条件为实体对象@key字段）
	 * 
	 * @param bean
	 *            实体映射对象
	 * @return
	 */
	public static SqlContext getDelete(Object bean) {
		List<Object> wheresValue = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		StringBuilder where = new StringBuilder();
		String tableName = getTableName(bean);
		String primaryKeyStr = getPkNames(tableName);
		try {
			Class<?> cls = bean.getClass();
			for (Field field : cls.getDeclaredFields()) {
				field.setAccessible(true);
				Object val = field.get(bean);
				//Mark Mysql
				String columndName = FieldUtils.getUnderlineName(field.getName());
				String columndNameNew = '`'+columndName+'`';

				if(AnnotationUtil.hasAnnotation(field, Transient.class)){
					continue;
				}
				if(AnnotationUtil.hasAnnotation(field,TableField.class)){
					columndName = AnnotationUtil.getAnnotationValue(field,TableField.class,"value");
					Boolean existFlag  = AnnotationUtil.getAnnotationValue(field,TableField.class,"exist");
					if(existFlag==false){
						continue;
					}
					columndNameNew = '`'+columndName+'`';
				}else if(AnnotationUtil.hasAnnotation(field, Column.class)){
					columndName = AnnotationUtil.getAnnotationValue(field,Column.class,"name");
					columndNameNew = '`'+columndName+'`';
				}/*else if(AnnotationUtil.hasAnnotation(field, io.github.wujun728.db.orm.annotation.Column.class)){
					columndName = AnnotationUtil.getAnnotationValue(field, io.github.wujun728.db.orm.annotation.Column.class,"name");
					columndNameNew = '`'+columndName+'`';
				}*/

				if (val != null && (/*field.isAnnotationPresent(PK.class)  ||*/ field.isAnnotationPresent(Id.class)
						|| field.isAnnotationPresent(TableId.class)  ||  primaryKeyStr.contains(columndName))) {
					where.append(columndNameNew).append(EQUAL_AND);
					wheresValue.add(val);
				}
			}
			where.delete(where.length() - 4, where.length());
			sql.append(DELETE);
			//sql.append(FieldUtils.getUnderlineName(cls.getSimpleName()));
			sql.append(tableName);
			sql.append(WHERE);
			sql.append(where);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new SqlContext(sql, wheresValue.toArray());
	}


	/**
	 * 数组合并
	 * @param first
	 * @param second
	 * @return
	 */
	public static Object[] concat(Object[] first, Object... second) {
		Object[] result = new Object[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * 构建select语句
	 * 
	 * @param cls
	 *            类对象
	 * @return sql上下文
	 */
	public static SqlContext getSelect(Class<?> cls) {
		StringBuilder sql = new StringBuilder(SELECT);
		String tableName = getTableName(cls);
		sql.append(tableName);
		//sql.append(FieldUtils.getUnderlineName(cls.getSimpleName()));
		return new SqlContext(sql);
	}
	public static String getSelectSQl(Class<?> cls) {
		StringBuilder sql = new StringBuilder(SELECT);
		String tableName = getTableName(cls);
		sql.append(tableName);
		//sql.append(FieldUtils.getUnderlineName(cls.getSimpleName()));
		return sql.toString();
	}

	/**
	 * 构建单个对象查询语条件为带有@key的字段
	 * 
	 * @param cls
	 * 
	 * @param id
	 *            查询条件
	 * @return sql上下文
	 */
	public static SqlContext getByKey(Class<?> cls, Object... id) {
		SqlContext sqlContext = getSelect(cls);
		StringBuilder sql = sqlContext.getSqlBuilder();
		StringBuilder where = new StringBuilder();
		sql.append(WHERE);
		String tableName = getTableName(cls);
		String primaryKeyStr = getPkNames(tableName);
		for (Field field : cls.getDeclaredFields()) {
			field.setAccessible(true);
			//Mark Mysql
			String columndName = FieldUtils.getUnderlineName(field.getName());
			String columndNameNew = '`'+columndName+'`';
			if(AnnotationUtil.hasAnnotation(field, Transient.class)){
				continue;
			}
			if(AnnotationUtil.hasAnnotation(field,TableField.class)){
				columndName = AnnotationUtil.getAnnotationValue(field,TableField.class,"value");
				Boolean existFlag  = AnnotationUtil.getAnnotationValue(field,TableField.class,"exist");
				if(existFlag==false){
					continue;
				}
				columndNameNew = '`'+columndName+'`';
			}else if(AnnotationUtil.hasAnnotation(field, Column.class)){
				columndName = AnnotationUtil.getAnnotationValue(field,Column.class,"name");
				columndNameNew = '`'+columndName+'`';
			}

			if (/*field.isAnnotationPresent(PK.class) ||*/ field.isAnnotationPresent(Id.class)
					|| field.isAnnotationPresent(TableId.class)   ||  primaryKeyStr.contains(columndName)) {
				//sql.append(columndNameNew).append(EQUAL);
				where.append(columndNameNew).append(EQUAL_AND);
				sqlContext.setParams( id );
//				if(id != null && id.getClass().isArray() ){
//				}else{
//					sqlContext.setParams(new Object[] { id });
//				}
			}
		}
		where.delete(where.length() - 4, where.length());
		sql.append(where);
		return sqlContext;
	}

	/**
	 * 构建select语句
	 * 
	 * @param cls
	 *            类对象
	 * @param params
	 *            查询参数
	 * @return sql上下文
	 */
	public static SqlContext getSelect(Class<?> cls, Map<String, Object> params) {
		SqlContext sqlContext = getSelect(cls);
		StringBuilder sql = sqlContext.getSqlBuilder();
		sql.append(WHEREOK);
		List<Object> values = new ArrayList<Object>();
		if(CollectionUtil.isNotEmpty(params)){
			for (Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				sql.append(AND).append(key).append(EQUAL);
				values.add(value);
			}
		}
		sqlContext.setParams(values.toArray());
		return sqlContext;
	}

	/**
	 * 构建select语句
	 * 
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数
	 * @return sql上下文
	 */
	public static SqlContext getSelect(StringBuilder sql, Map<String, Object> params) {
		if(sql!=null && !StrUtil.containsAnyIgnoreCase(sql,"where") && !StrUtil.containsAnyIgnoreCase(sql,"limit")
				&& !StrUtil.containsAnyIgnoreCase(sql,"order")){
			sql.append(WHEREOK);
		}
		List<Object> values = new ArrayList<Object>();
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			sql.append(AND).append(key).append(EQUAL);
			values.add(value);
		}
		return new SqlContext(sql, values.toArray());
	}

	/**
	 * 构建select语句
	 * 
	 * @param cls
	 *            类对象
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页条数
	 * @param params
	 *            查询参数
	 * @return sql上下文
	 */
	public static SqlContext getSelect(Class<?> cls, int page, int rows, Map<String, Object> params) {
		SqlContext sqlContext = getSelect(cls, params);
		StringBuilder sql = sqlContext.getSqlBuilder();
		sql.append(LIMIT);
		sql.append((page - 1) * rows);
		sql.append(LINK);
		sql.append(rows);
		return sqlContext;
	}
	public static String getMySQlPageSQL(String sqlStr, int page, int rows) {
		StringBuilder sql = new StringBuilder(sqlStr);
		sql.append(LIMIT);
		sql.append((page - 1) * rows);
		sql.append(LINK);
		sql.append(rows);
		return sql.toString();
	}

	/**
	 * 构建select语句
	 * 
	 *            类对象
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页条数
	 * @param params
	 *            查询参数
	 * @return sql上下文
	 */
	public static SqlContext getSelect(String sql, int page, int rows, Map<String, Object> params) {
		SqlContext sqlContext = getSelect(new StringBuilder(sql), params);
		StringBuilder pageSql = sqlContext.getSqlBuilder();
		pageSql.append(LIMIT);
		pageSql.append((page - 1) * rows);
		pageSql.append(LINK);
		pageSql.append(rows);
		return sqlContext;
	}

	/**
	 * 构建count语句(查询语句中主FROM必须大写，其他from小写)
	 * 
	 *            类对象
	 * @return sql上下文
	 */
	public static SqlContext getCount(SqlContext sqlContext) {
		sqlContext.setSql(getCount(sqlContext.getSql()));
		return sqlContext;
	}

	/**
	 * 构建count语句(查询语句中主FROM必须大写，其他from小写)
	 * 
	 *            类对象
	 * @return sql语句
	 */
	public static String getCount(String sql) {
		int num = 0;
		String xing = "*";
		StringBuilder sb = new StringBuilder();
		for (char cr : sql.toCharArray()) {
			if (')' == cr) {
				num++;
			}
			if (num == 0) {
				sb.append(cr);
			} else {
				sb.append(xing);
			}
			if ('(' == cr) {
				num--;
			}
		}
		int i = sb.toString().replace(from, FROM).indexOf(FROM);
		sql = sql.substring(i);
		sql = sql.replace(order, ORDER).replace(limit, LIMIT).replaceAll(R_ORDER, EMPTY).replaceAll(R_LIMIT, EMPTY);
		return SELECTCOUNT.concat(sql);
	}

	/**
	 * 构建select语句
	 * 
	 * @param sql
	 *            类对象
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页条数
	 * @return sql语句
	 */
	public static String getSelect(String sql, int page, int rows) {
		StringBuilder pageSql = new StringBuilder(sql);
		pageSql.append(LIMIT);
		pageSql.append((page - 1) * rows);
		pageSql.append(LINK);
		pageSql.append(rows);
		return pageSql.toString();
	}

	/**
	 * 构建select语句
	 */
	public static SqlContext getByParams(Class<?> cls, String[] fields, Object... parmas) {
		SqlContext sqlContext = getSelect(cls);
		StringBuilder sql = sqlContext.getSqlBuilder();
		sql.append(WHERE);
		List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < fields.length; i++) {
			if(i>0)sql.append(AND);
			sql.append(fields[i]).append(EQUAL);
			values.add(parmas[i]);
		}
		sqlContext.setParams(values.toArray());
		return sqlContext;
	}

}