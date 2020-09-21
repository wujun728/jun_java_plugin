package org.coody.framework.jdbc.entity;


public class JDBCEntity {

	private String sql;
	
	private Object[]params;
	
	public JDBCEntity(String sql,Object[] params){
		this.sql=sql;
		this.params=params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	
	
}
