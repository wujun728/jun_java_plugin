package com.tjgd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tjgd.DBHelper.DataSourceUtil;
import com.tjgd.bean.Auth;
import com.tjgd.dao.IAuthDAO;

public class AuthDAOImpl implements IAuthDAO {
	//连接
	private Connection conn = null;
	//预处理声明，对sql语句预编译
	private PreparedStatement pstmt = null;
	//数据库连接工具类
	DataSourceUtil ds = null;

	// ------构造方法-------------------------------------------
	public AuthDAOImpl() {
		try {
			//从构造方法初始化对象，需要时创建，减少资源消耗。
			ds = new DataSourceUtil();
			this.conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------删除权限------------------------------------------
	public void delAuth(int authId) {
		//sql 语句，参数使用问号
		String sql = "delete from auth where id=?";
		try {
			//通过连接获取预编译对象
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setInt(1, authId);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				clear(authId); // 如果要删除成功，继续删除role_auth表中相关信息
			}
			//关闭，释放资源
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//最后都要关闭连接
			ds.close();
		}
	}

	// --------获取所有权限-------------------------------------
	public List<Auth> listAuths() {
		List<Auth> list = new ArrayList<Auth>();
		String sql = "select id,authName,actionName,url,moduleId  from auth";
		try {
			pstmt = conn.prepareStatement(sql);
			//执行查询
			ResultSet rs = pstmt.executeQuery();
			//循环遍历，直到没有下一个
			while (rs.next()) {
				//创建对象获取内容
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setAuthName(rs.getString("authName"));
				auth.setActionName(rs.getString("actionName"));
				auth.setUrl(rs.getString("url"));
				auth.setModuleId(rs.getInt("moduleId"));
				//添加到list中
				list.add(auth);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
		return list;
	}

	// -------增加权限-------------------------------------------
	public void saveAuth(Auth auth) {
		String sql = "insert into auth(authName,actionName,url,moduleId)  values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setString(1, auth.getAuthName());
			pstmt.setString(2, auth.getActionName());
			pstmt.setString(3, auth.getUrl());
			pstmt.setInt(4, auth.getModuleId());
			//执行更新
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
	}

	// -------清空被指定到特定角色的权限--------------------------
	private void clear(int authId) {
		String sql = "delete from role_auth where authId =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, authId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
