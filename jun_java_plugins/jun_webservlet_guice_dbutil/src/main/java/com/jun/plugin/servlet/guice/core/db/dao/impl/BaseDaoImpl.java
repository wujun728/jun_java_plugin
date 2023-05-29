package com.jun.plugin.servlet.guice.core.db.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jun.plugin.servlet.guice.core.db.ConnectionContext;
import com.jun.plugin.servlet.guice.core.db.ReflectUtils;
import com.jun.plugin.servlet.guice.core.db.dao.BaseDao;
import com.jun.plugin.servlet.guice.core.db.model.Page;

@Singleton
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	protected Class<T> entityClass;

	public BaseDaoImpl() {
		entityClass = ReflectUtils.getClassGenricType(getClass());
	}

	@Inject
	private QueryRunner qr;

	private Connection getCon() {
		return ConnectionContext.getInstance().getCon();
	}

	@Override
	public void add(String sql, Object[] params) throws SQLException {
		qr.update(getCon(), sql, params);
	}

	@Override
	public void batchAdd(String sql, Object[][] params) throws SQLException {
		if (null != params && params.length > 0) {
			qr.batch(getCon(), sql, params);
		}
	}

	@Override
	public void del(String sql, Object[] params) throws SQLException {
		qr.update(getCon(), sql, params);
	}

	@Override
	public void update(String sql, Object[] params) throws SQLException {
		qr.update(getCon(), sql, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(String sql, Object[] params) throws SQLException {
		List<T> list = (List<T>) qr.query(getCon(), sql, new BeanListHandler(entityClass));
		return list;
	}

	@Override
	public List<T> findByIds(String tableName, List<ID> ids) throws SQLException {
		if (ids == null || ids.size() == 0) {
			return null;
		}
		String idsStr = "";
		for (ID id : ids) {
			idsStr += id + ",";
		}
		idsStr = idsStr.substring(0, idsStr.length() - 1);
		String sql = "select * from " + tableName + " where id in (" + idsStr + ")";
		System.out.println(sql);
		return findAll(sql, null);
	}

	@Override
	public T findById(String tableName, ID id) throws SQLException {
		String sql = "select * from " + tableName + " where id=?";
		Object params[] = { id };
		@SuppressWarnings("deprecation")
		T t = (T) qr.query(getCon(), sql, id, new BeanHandler(entityClass));
		return t;
	}

	@Override
	public void delById(String tableName, ID id) throws SQLException {
		if (id == null) {
			return;
		}
		T entity = this.findById(tableName, id);
		if (entity != null) {
			String sql = "delete from " + tableName + " where id=?";
			Object[] params = { id };
			del(sql, params);
		}
	}

	@Override
	public void delByIds(String tableName, List<ID> ids) throws SQLException {
		if (ids == null || ids.size() == 0) {
			return;
		}
		String idsStr = "";
		for (ID id : ids) {
			idsStr += id + ",";
		}
		idsStr = idsStr.substring(0, idsStr.length() - 1);
		String sql = "delete from " + tableName + " where id in (" + idsStr + ")";
		del(sql, null);
	}

	@Override
	public Page<T> findByPage(String tableName, Integer offset, Integer size, Map<String, Object> map)
			throws SQLException {
		if (offset == null || size == null || offset < 0 || size < 1) {
			return Page.build(new ArrayList<T>(), 0L);
		}
		String countSql="select count(1) as count from " + tableName;
		Long total = count(countSql, offset, size, map);
		if (total == 0L) {
			return Page.build(new ArrayList<T>(), 0L);
		}
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from " + tableName);
		String sql = mapParams(offset, size, map, sbSql, true).toString();
		System.out.println(sql);
		List<T> lists = this.findAll(sql, null);
		return Page.build(lists, total);
	}

	protected Long count(String countSql, Integer offset, Integer size, Map<String, Object> map) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(countSql);
		boolean flag = false;
		String sql = mapParams(offset, size, map, sbSql, flag).toString();
		System.out.println(sql);
		try {
			Object[] list = qr.query(getCon(), sql, new ArrayHandler());
			if (list != null) {
				return Long.valueOf(list[0] + "");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	private StringBuffer mapParams(Integer offset, Integer size, Map<String, Object> map, StringBuffer sbSql,
			boolean flag) {
		StringBuffer newsbSqlSb = new StringBuffer();
		if (map != null && map.size() > 0) {
			sbSql.append(" where ");
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object obj = entry.getValue();
				if(obj instanceof String){
					String value=(String)obj;
					sbSql.append(key + "= '" + value + "' " + " and ");
				}else{
					Object value = entry.getValue();
					sbSql.append(key + "= " + value + "" + " and ");
				}
				
				System.out.println("key =" + key + " value = " + obj.toString());
			}
			String newSbSql = sbSql.substring(0, sbSql.length() - 4);
			newsbSqlSb.append(newSbSql);
		}
		if (flag == true) {
			newsbSqlSb.append(" limit " + offset + "," + size);
		}
		if(newsbSqlSb==null || newsbSqlSb.length()==0){
			return sbSql;
		}
		return newsbSqlSb;
	}

	@Override
	public Page<T> findByPage(String sql,String countSql) throws SQLException {
		Long total = count(countSql, null, null, null);
		if (total == 0L) {
			return Page.build(new ArrayList<T>(), 0L);
		}
		List<T> lists=this.findAll(sql, null);
		return Page.build(lists, total);
	}

	
}
