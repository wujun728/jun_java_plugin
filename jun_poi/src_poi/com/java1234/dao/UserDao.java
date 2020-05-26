package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.PageBean;
import com.java1234.model.User;

public class UserDao {

	public ResultSet userList(Connection con,PageBean pageBean)throws Exception{
		StringBuffer sb=new StringBuffer("select * from t_user");
		if(pageBean!=null){
			sb.append(" limit ?,?");			
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		if(pageBean!=null){
			pstmt.setInt(1, pageBean.getStart());
			pstmt.setInt(2, pageBean.getRows());
		}
		return pstmt.executeQuery();
	}
	
	public int userCount(Connection con)throws Exception{
		String sql="select count(*) as total from t_user";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int userDelete(Connection con,String delId)throws Exception{
		String sql="delete from t_user where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, delId);
		return pstmt.executeUpdate();
	}
	
	public int userAdd(Connection con,User user)throws Exception{
		String sql="insert into t_user values(null,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getName());
		pstmt.setString(2, user.getPhone());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getQq());
		return pstmt.executeUpdate();
	}
	
	public int userModify(Connection con,User user)throws Exception{
		String sql="update t_user set name=?,phone=?,email=?,qq=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getName());
		pstmt.setString(2, user.getPhone());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getQq());
		pstmt.setInt(5,user.getId());
		return pstmt.executeUpdate();
	} 
}
