package com.itheima.dbassist;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * 框架的核心类
 * @author wzhting
 *
 */
public class DBAssist {
	private DataSource dataSource;
	public DBAssist(DataSource dataSource){
		this.dataSource = dataSource;
	}
	//写：添加、删除、修改
	//params参数要和sql中的占位符对应
	public void update(String sql,Object...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			//设置参数
				//得到sql中的参数
				ParameterMetaData pmd = stmt.getParameterMetaData();
				int count = pmd.getParameterCount();
				if(count>0){
					if(params==null){
						throw new RuntimeException("必须传入参数的值");
					}
					if(count!=params.length){
						throw new RuntimeException("参数数量不匹配");
					}
					for(int i=0;i<count;i++){
						stmt.setObject(i+1, params[i]);
					}
					
				}
			
			stmt.executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			release(rs, stmt, conn);
		}
	}
	
	
	//读：查询
	public Object query(String sql,ResultSetHandler rsh,Object...params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			//设置参数
				//得到sql中的参数
				ParameterMetaData pmd = stmt.getParameterMetaData();
				int count = pmd.getParameterCount();
				if(count>0){
					if(params==null){
						throw new RuntimeException("必须传入参数的值");
					}
					if(count!=params.length){
						throw new RuntimeException("参数数量不匹配");
					}
					for(int i=0;i<count;i++){
						stmt.setObject(i+1, params[i]);
					}
					
				}
			
			rs = stmt.executeQuery();
			//有结果集，要封装到对象中。策略设计模式
			return rsh.handle(rs);
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			release(rs, stmt, conn);
		}
	}
	
	
	private void release(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
