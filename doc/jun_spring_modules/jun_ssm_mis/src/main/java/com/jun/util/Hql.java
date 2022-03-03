package com.jun.util;

import java.util.*;

/**
 * 封装Hql语句
 * @author Wujun
 *
 */
@SuppressWarnings("unchecked")
public class Hql {
	public String sqlstr;
	public List params = new ArrayList();
	private Hql(String start){
		this.sqlstr = start;
	}
	
	public static Hql start(String start){
		return new Hql(start);
	}
	public Hql addWhere(String prefix,Where where){
		if(where.getSqlstr() != null){
			sqlstr+= " " + prefix + " " + where.getSqlstr();
			params.addAll(where.getParams());
		}
		return this;
	}
	
	public Hql add(String s){
		sqlstr+= " " + s;
		return this;
	}

	public List getParams() {
		return params;
	}

	public void setParams(List params) {
		this.params = params;
	}

	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	
	/**
	 * order by条件
	 */
	public Hql orderby(String str,String sort){
		if(null != str && !str.equals("") && null != sort && !sort.equals("")){
			sqlstr += " order by " + str + " " + sort;
		}
		return this;
	}
	
	/**
	 * order by条件(重载)默认升序
	 */
	public Hql orderby(String str){
		if(null != str && !str.equals("")){
			sqlstr += " order by " + str + " asc";
		}
		return this;
	}
}
