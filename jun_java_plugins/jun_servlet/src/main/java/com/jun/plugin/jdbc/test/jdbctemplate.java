package com.jun.plugin.jdbc.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jun.plugin.base.DataSourceUtil;




public abstract class jdbctemplate<M,Q extends M> {
	public M getByUuid(Q qm,int uuid) {
		Q q = null;
		try {
			q = (Q) qm.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Method ms[] = qm.getClass().getMethods();
		for(Method mth : ms){
			if(mth.getName().equalsIgnoreCase("setUuid")){
				try {
					mth.invoke(q, uuid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		List<M> list = this.getByCondition(q,1,100,new ArrayList<String>());
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<M> getAll(Q qm,int fromNum,int toNum) {
		Q q = null;
		try {
			q = (Q) qm.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getByCondition(q,fromNum,toNum,new ArrayList<String>());
	}
	public int getCount(Q qm){
		return this.getCount(qm, new ArrayList<String>());
	}
	
	public int getCount(Q qm,List<String> excludeName) {
		int count = 0;
		Connection conn = null;
		try{
			//1
			conn = DataSourceUtil.getConn();
			//2:
			String sql = this.getCountMainSql(qm);
			
			sql = this.prepareSql(sql, qm,excludeName);
						
			System.out.println("sql=="+sql);
			//3:
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//4:
			this.setValue(pstmt, qm,0,0,excludeName);
			
			//5:
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			
			//6:
			rs.close();
			pstmt.close();
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	public List<M> getByCondition(Q qm,int fromNum,int toNum){
		return this.getByCondition(qm, fromNum,toNum,new ArrayList<String>());
	}
	public List<M> getByCondition(Q qm,int fromNum,int toNum,List<String> excludeName) {
		List<M> list = new ArrayList<M>();
		Connection conn = null;
		try{
			//1
			conn = DataSourceUtil.getConn();
			//2:
			String sql = this.getQueryByConditionMainSql(qm);
			
			sql = this.prepareSql(sql, qm,excludeName);
			//翻页
			sql = this.needPageSql(sql);		
			
			System.out.println("sql=="+sql);
			//3:
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//4:
			int count = this.setValue(pstmt, qm,fromNum,toNum,excludeName);
			this.setPageValue(pstmt, count, fromNum, toNum);
			//5:
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				M tempM = this.rs2model(rs,qm);
				list.add(tempM);
			}			
			//6:
			rs.close();
			pstmt.close();
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private String needPageSql(String sql){
		String str = "with aa as ("+sql+") select * from aa where r>=? and r<=?";
		return str;
	}
	
	protected String getQueryByConditionMainSql(Q m)throws SQLException {
		return this.getQueryByConditionMainSqlSelectPart(m)+this.getQueryByConditionMainSqlFormPart(m)+this.getQueryByConditionMainSqlWherePart(m);
	}
	protected String getQueryByConditionMainSqlSelectPart(Q m)throws SQLException {
		return "select rownum r,t.* ";
	}
	protected String getQueryByConditionMainSqlFormPart(Q m)throws SQLException {
		String tempStr = m.getClass().getSuperclass().getSimpleName();
		tempStr = tempStr.substring(0,tempStr.length()-5);
		return  " from tbl_"+tempStr+" t ";
	}
	protected String getQueryByConditionMainSqlWherePart(Q m)throws SQLException {
		return  " where 1=1 ";
	}
	
	
	protected String getCountMainSql(Q m)throws SQLException {
		String tempStr = m.getClass().getSuperclass().getSimpleName();
		tempStr = tempStr.substring(0,tempStr.length()-5);
		
		String sql = "select count(*) from tbl_"+tempStr+" t where 1=1 ";
		return sql;
	}
	protected String prepareSql(String sql,Q qm,List<String> excludeName)throws SQLException {
		StringBuffer buffer = new StringBuffer(sql);
		Field fs[] = qm.getClass().getSuperclass().getDeclaredFields();
		Field fs2[] = qm.getClass().getDeclaredFields();

		for(Field f : fs){
			if(Modifier.isTransient(f.getModifiers())){
				continue;
			}
			if(excludeName.contains(f.getName())){
				continue;
			}
			
			String type = f.getType().toString();
			if(type.endsWith("java.lang.String")){
				String v = ""+this.getValue(qm, f.getName());
				if(v!=null && v.trim().length()>0 && !v.equalsIgnoreCase("null")){
					buffer.append(" and t."+f.getName()+" like ? ");
				}
			}else if(type.endsWith("int")){
				int v = Integer.parseInt(""+this.getValue(qm, f.getName()));
				if(v > 0){
					if(this.isAreaValue(fs2, f.getName())){
						buffer.append(" and t."+f.getName()+" >=? ");	
					}else{
						buffer.append(" and t."+f.getName()+" =? ");
					}
				}
			}else if(type.endsWith("float")){
				float v = Float.parseFloat(""+this.getValue(qm, f.getName()));
				if(v > 0){
					if(this.isAreaValue(fs2, f.getName())){
						buffer.append(" and t."+f.getName()+" >=? ");	
					}else{
						buffer.append(" and t."+f.getName()+" =? ");
					}
				}
			}else if(type.endsWith("long")){
				long v = Long.parseLong(""+this.getValue(qm, f.getName()));
				if(v > 0){
					if(this.isAreaValue(fs2, f.getName())){
						buffer.append(" and t."+f.getName()+" >=? ");	
					}else{
						buffer.append(" and t."+f.getName()+" =? ");
					}
				}
			}		
		}
		
		for(Field f : fs2){
			if(Modifier.isTransient(f.getModifiers())){
				continue;
			}
			String type = f.getType().toString();
			if(type.endsWith("int")){
				int v = Integer.parseInt(""+this.getValue(qm, f.getName()));
				if(v>0){
					buffer.append(" and t."+f.getName().substring(0,f.getName().length()-1)+" <=? ");
				}
			}else if(type.endsWith("float")){
				float v = Float.parseFloat(""+this.getValue(qm, f.getName()));
				if(v > 0){
					buffer.append(" and t."+f.getName().substring(0,f.getName().length()-1)+" <=? ");
				}
			}else if(type.endsWith("long")){
				long v = Long.parseLong(""+this.getValue(qm, f.getName()));
				if(v > 0){
					buffer.append(" and t."+f.getName().substring(0,f.getName().length()-1)+" <=? ");
				}
			}
		}
		
		return buffer.toString();	
	}
	protected int setValue(PreparedStatement pstmt,Q qm,int fromNum,int toNum,List<String> excludeName) throws SQLException{
		int count = 1;
		Field fs[] = qm.getClass().getSuperclass().getDeclaredFields();
		Field fs2[] = qm.getClass().getDeclaredFields();

		for(Field f : fs){
			if(Modifier.isTransient(f.getModifiers())){
				continue;
			}
			if(excludeName.contains(f.getName())){
				continue;
			}
			String type = f.getType().toString();
			if(type.endsWith("java.lang.String")){
				String v = ""+this.getValue(qm, f.getName());
				if(v!=null && v.trim().length()>0  && !v.equalsIgnoreCase("null")){
					pstmt.setString(count, "%"+v+"%");
					count++;
				}
			}else if(type.endsWith("int")){
				int v = Integer.parseInt(""+this.getValue(qm, f.getName()));
				if(v > 0){
					pstmt.setInt(count, v);
					count++;
				}
			}else if(type.endsWith("float")){
				float v = Float.parseFloat(""+this.getValue(qm, f.getName()));
				if(v > 0){
					pstmt.setFloat(count, v);
					count++;
				}
			}else if(type.endsWith("long")){
				long v = Long.parseLong(""+this.getValue(qm, f.getName()));
				if(v > 0){
					pstmt.setLong(count, v);
					count++;
				}
			}		
		}
		
		for(Field f : fs2){
			if(Modifier.isTransient(f.getModifiers())){
				continue;
			}
			String type = f.getType().toString();
			if(type.endsWith("int")){
				int v = Integer.parseInt(""+this.getValue(qm, f.getName()));
				if(v>0){
					pstmt.setInt(count, v);
					count++;
				}
			}else if(type.endsWith("float")){
				float v = Float.parseFloat(""+this.getValue(qm, f.getName()));
				if(v > 0){
					pstmt.setFloat(count, v);
					count++;
				}
			}else if(type.endsWith("long")){
				long v = Long.parseLong(""+this.getValue(qm, f.getName()));
				if(v > 0){
					pstmt.setLong(count, v);
					count++;
				}
			}
		}
		
		return count;
	}
	private void setPageValue(PreparedStatement pstmt,int count,int fromNum,int toNum) throws SQLException{
		//翻页
		if(fromNum > 0 && toNum >0){
			pstmt.setInt(count, fromNum);
			count++;
			pstmt.setInt(count, toNum);
			count++;
		}
	}
	protected M rs2model(ResultSet rs,Q qm) throws Exception{
		M retm = (M)(qm.getClass().getSuperclass().newInstance());
		Field fs[] = qm.getClass().getSuperclass().getDeclaredFields();
		for(Field f : fs){
			if(Modifier.isTransient(f.getModifiers())){
				continue;
			}
			String type = f.getType().toString();
			
			if(type.endsWith("java.lang.String")){
				retm = this.setValue(retm, f.getName(), rs.getString(f.getName()));
			}else if(type.endsWith("int")){
				retm = this.setValue(retm, f.getName(), rs.getInt(f.getName()));
			}else if(type.endsWith("float")){
				retm = this.setValue(retm, f.getName(), rs.getFloat(f.getName()));
			}else if(type.endsWith("long")){
				retm = this.setValue(retm, f.getName(), rs.getLong(f.getName()));
			}		
		}
		return retm;
	}
	private M setValue(M m,String name,Object value){
		Method ms [] = m.getClass().getMethods();
		for(Method mth : ms){
			if(mth.getName().equalsIgnoreCase("set"+name)){
				try {
					mth.invoke(m, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		return m;
	}
	
	private boolean isAreaValue(Field fs2[] , String name){
		for(Field f : fs2){
			if(f.getName().equalsIgnoreCase(name+"2")){
				return true;
			}
		}
		return false;
	}
	
	public void create(M m) {
		this.myExecuteUpdate(m, 1);
	}	
	private String getTableName(M m){
		String tempStr = m.getClass().getSimpleName();
		tempStr = tempStr.substring(0,tempStr.length()-5);
		return tempStr;
	}
	protected String getCreateSql(M m){
		//约定大于配置
		
		Field fs[] = m.getClass().getDeclaredFields();
		StringBuffer buffer = new StringBuffer();
		for(Field f : fs){
			if(!Modifier.isTransient(f.getModifiers())){
				buffer.append("?,");
			}
		}
		String s = buffer.toString();
		s = s.substring(0,s.length()-1);
		
		String sql = "insert into tbl_"+this.getTableName(m)+" values("+s+")";
		return sql;
	}
	protected  void setCreateValue(PreparedStatement pstmt,M m)throws SQLException{
		Field fs[] = m.getClass().getDeclaredFields();
		int count = 1;
		for(Field f : fs){
			if(Modifier.isTransient(f.getModifiers())){
				continue;
			}
			String type = f.getType().toString();
			if(type.endsWith("java.lang.String")){
				pstmt.setString(count,""+this.getValue(m, f.getName()));
				count++;
			}else if(type.endsWith("int")){
				pstmt.setInt(count, Integer.parseInt(""+this.getValue(m, f.getName())));
				count++;
			}else if(type.endsWith("float")){
				pstmt.setFloat(count, Float.parseFloat(""+this.getValue(m, f.getName())));
				count++;
			}else if(type.endsWith("long")){
				pstmt.setLong(count, Long.parseLong(""+this.getValue(m, f.getName())));
				count++;
			}
		}
	}
	
	private Object getValue(M m,String name) {
		Object ret = null;
		Method ms[] = m.getClass().getMethods();
		for(Method mth : ms){
			if(mth.getName().equalsIgnoreCase("get"+name)){
				try {
					ret = mth.invoke(m, new Object[]{});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	}
	////////////////////////////////////////////
	public void update(M m) {
		this.myExecuteUpdate(m, 2);
	}
	protected String getUpdateSql(M m){
		Field fs[] = m.getClass().getDeclaredFields();
		StringBuffer buffer = new StringBuffer("update tbl_"+this.getTableName(m)+" set ");
		for(Field f : fs){
			if(!Modifier.isTransient(f.getModifiers()) && !f.getName().equalsIgnoreCase("uuid")){
				buffer.append(f.getName()+"=?,");
			}
		}
		String s = buffer.toString();
		s = s.substring(0,s.length()-1);
		
		String sql = s + " where uuid=?";
		
		return sql;
	}
	protected void setUpdateValue(PreparedStatement pstmt,M m)throws SQLException{
		Field fs[] = m.getClass().getDeclaredFields();
		int count = 1;
		for(Field f : fs){
			if(Modifier.isTransient(f.getModifiers()) || f.getName().equalsIgnoreCase("uuid")){
				continue;
			}
			String type = f.getType().toString();
			if(type.endsWith("java.lang.String")){
				pstmt.setString(count,""+this.getValue(m, f.getName()));
				count++;
			}else if(type.endsWith("int")){
				pstmt.setInt(count, Integer.parseInt(""+this.getValue(m, f.getName())));
				count++;
			}else if(type.endsWith("float")){
				pstmt.setFloat(count, Float.parseFloat(""+this.getValue(m, f.getName())));
				count++;
			}else if(type.endsWith("long")){
				pstmt.setLong(count, Long.parseLong(""+this.getValue(m, f.getName())));
				count++;
			}
		}
		//设置uuid
		String v = ""+this.getValue(m, "uuid");
		if(v!=null && v.trim().length()>0 && !v.equalsIgnoreCase("null")){
			pstmt.setInt(count, Integer.parseInt(v));
		}else{
			pstmt.setInt(count, 0);
		}
		
		
		
	}
	/////////////////////////////////////
	public void delete(M m) {
		this.myExecuteUpdate(m, 3);
	}
	protected  String getDeleteSql(M m){
		String sql = "delete from tbl_"+this.getTableName(m)+" where uuid=?";
		return sql;
	}
	protected void setDeleteValue(PreparedStatement pstmt,M m)throws SQLException{
		pstmt.setInt(1, Integer.parseInt(""+this.getValue(m, "uuid")));
	}
	
	////////////////////////////////
	private void myExecuteUpdate(M m,int type) {
		Connection conn = null;
		try{
			//1
			conn = DataSourceUtil.getConn();
			//2:
			String sql = "";
			if(type==1){
				sql = this.getCreateSql(m);
			}else if(type==2){
				sql = this.getUpdateSql(m);
			}else if(type==3){
				sql = this.getDeleteSql(m);
			}
			
			System.out.println("now myexecuteupdate sql=="+sql);
			//3:
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//4:
			if(type==1){
				this.setCreateValue(pstmt, m);
			}else if(type==2){
				this.setUpdateValue(pstmt, m);
			}else if(type==3){
				this.setDeleteValue(pstmt, m);
			}			
			//5:
			pstmt.executeUpdate();
			
			//6:
			pstmt.close();			
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
