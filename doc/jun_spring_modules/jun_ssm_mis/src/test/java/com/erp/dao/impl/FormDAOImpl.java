package com.erp.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.erp.dao.IFormDAO;
import com.jun.plugin.utils.StringUtil;

@Repository("formDAOImpl")
public class FormDAOImpl implements IFormDAO {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;


	@SuppressWarnings("rawtypes")
	@Override
	public String getTotal(Map map) {
		String sql = " SELECT * FROM " + map.get("TABLENAME") + " WHERE 1=1 ";
		if (null != map.get("searchName")) {
			if (null != map.get("searchValue")) {
				sql += "AND " + StringUtil.toString(map.get("searchName")) + " like '%" + StringUtil.toString(map.get("searchValue")).trim() + "%'";
			}
		}
		for(int i=0;i<9;i++){
			if(i==0)  continue;
			if (null != map.get("searchName"+i)) {
				if (null != map.get("searchValue"+i)) {
					sql+="AND " + StringUtil.toString(map.get("searchName"+i)) + " like '%" + StringUtil.toString(map.get("searchValue"+i)).trim() + "%'" ;
				}
			}
		}
		return StringUtil.toString(((Map) this.jdbcTemplate.queryForList(" SELECT COUNT(ID) AS C FROM  (" + sql + ") AS T  ").get(0)).get("C"));
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public  Object[] getMysqlColumnNames(Map map) {
		String sql = " SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_NAME ='"+ map.get("TABLENAME") +"'   AND COLUMN_KEY<>'PRI' ";
		List list=this.jdbcTemplate.queryForList(sql);
		if(list!=null&&list.size()>0){
			Object obj[]=new Object[list.size()];
			for(int i=0;i<list.size();i++){
				Map m=(Map) list.get(i);
				obj[i]=StringUtil.toString(m.get("COLUMN_NAME"));
			}
			return obj;
		}
		return null;
	}
	
	
	/**
	 * param tableName,colmunName*n,columnValue*n
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int doUpdate(Map map) {
		Object fields[]=this.getMysqlColumnNames(map);
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE ");
		sql.append(map.get("TABLENAME"));
		sql.append(" SET " + fields[0] + " = ?");
		Object[] params = new Object[fields.length + 1];
		params[0] = StringUtil.toString(map.get(fields[0]));
		for (int i = 1; i < fields.length; i++) {
			sql.append("," + fields[i] + " = ?");
			Object value = map.get(fields[i]);
			params[i] = value;
		}
		sql.append(" WHERE ID = ? ");
		params[params.length - 1] = StringUtil.toString(map.get("ID"));
		System.out.println("sql=" + sql);
		System.out.println("ID=" + map.get("ID"));
		return this.jdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * param tableName,colmunName*n,columnValue*n
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int doCreate(Map map) throws Exception {
		Object fields[]=this.getMysqlColumnNames(map);
		if(map.get("TABLENAME")==null){
			return 0;
		}
		if(fields.length<1){
			return 0;
		}
		StringBuffer sql = new StringBuffer();
		String str2=new String();
		Object[] params = new Object[fields.length];
		sql.append(" INSERT INTO  ");
		sql.append(map.get("TABLENAME"));
		sql.append(" (");
		sql.append(Arrays.toString(fields).replaceAll("\\[|\\]", ""));
		sql.append(" ) VALUES (");
		for (int i = 0; i < fields.length; i++) {
			Object value = map.get(fields[i]);
			System.out.println(fields[i] + "-->" + value);
			params[i] = value;
			str2+="?,";
		}
		sql.append(str2.substring(0, str2.length()-1));
		sql.append("  )");
		System.out.println("sqll=" + sql);
		return this.jdbcTemplate.update(sql.toString(), params);
	}

	/**
	 * param tableName,id
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int doRemove(Map map) throws Exception {
		if(null == map.get("tableName") || null == map.get("id")){
			return 0 ;
		}
		String id = StringUtil.decodeToUtf(map.get("id"));
		String tableName = StringUtil.decodeToUtf(map.get("tableName"));
		int flag = 0;
		if (!"".equals(id)) {
			flag = this.jdbcTemplate.update("  DELETE FROM " + tableName + " WHERE ID=" + id);
		}
		return flag;
	}

	/**
	 * param tableName,page,rows,searchName,searchValue,orderby,limit
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findAll(Map map) throws Exception {
		if(null == map.get("tableName")){
			return null ;
		}
		int page = Integer.parseInt(StringUtil.toString(map.get("page")));
		int rows = Integer.parseInt(StringUtil.toString(map.get("rows")));
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT * FROM " + map.get("tableName")+"  WHERE 1=1  ");
		if (null != map.get("searchName")) {
			if (null != map.get("searchValue")) {
				sql.append(" AND " + StringUtil.toString(map.get("searchName")) + " LIKE '%" + StringUtil.toString(map.get("searchValue")).trim() + "%'");
			}
		}
		sql.append(" ORDER BY "+map.get("orderby")+" DESC " );
		if(null!=map.get("limit")){
			sql.append(" LIMIT " + (page - 1) * rows + "," + page * rows);
		}
		list = this.jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryJsonBySQl(Map map) throws Exception {
		return this.jdbcTemplate.queryForList(String.valueOf(map.get("sql")));
	}

	/**
	 * param tableName,id
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findByID(Map map) throws Exception {
		if(null == map.get("tableName") || null == map.get("tableName")){
			return null ;
		}
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT * FROM " + map.get("tableName")+"  WHERE 1=1 and id= "+map.get("id"));
		list = this.jdbcTemplate.queryForList(sql.toString());
		return list;
	}

	/**
	 * param tableName,columnName*n,columnValue*n,n=2
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int getCount(Map map) throws Exception {
		if(null == map.get("tableName") || null == map.get("columnName")){
			return 0 ;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT COUNT("+map.get("columnName")+") AS C FROM " + map.get("tableName")+"  WHERE 1=1  ");
		for(int i=1;i<Integer.valueOf(StringUtil.toString(map.get("n")));i++){
			if (null != map.get("columnName"+i)) {
				sql.append(" AND " + map.get("columnName"+i) +" LIKE '%" + StringUtil.toString(map.get("columnValue"+i)).trim()+"%'" );
			}
		}
		return Integer.valueOf(StringUtil.toString(((Map)this.jdbcTemplate.queryForList(sql.toString()).get(0)).get("C"),"0"));
	}

	/**
	 * param tableName,columnName2,columnValue2,columnName1
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getColumnValues(Map map) throws Exception {
		if(null == map.get("tableName") || null == map.get("columnName1")){
			return null ;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT "+map.get("columnName1")+" AS V FROM " + map.get("tableName")+"  WHERE 1=1  ");
		if (null != map.get("columnName2")) {
			sql.append(" AND " + map.get("columnName2") +" = " + StringUtil.toString(map.get("columnValue2")).trim() );
		}
		return this.jdbcTemplate.queryForList(sql.toString());
	}

}
