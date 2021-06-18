package com.tjgd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tjgd.DBHelper.DataSourceUtil;
import com.tjgd.bean.Auth;
import com.tjgd.bean.Role;
import com.tjgd.dao.IRoleDAO;

public class RoleDAOImpl implements IRoleDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	DataSourceUtil ds=null;
	//--------构造函数中初始化---------------------------------------
	public RoleDAOImpl() {
		try {
    		ds=new DataSourceUtil();
    		this.conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//--------查询单个角色---------------------------------------
	public Role findRoleByID(int roleId) {
		Role role = null;
		String sql = "select id,roleName  from role where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("roleName"));
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return role;
	}
	//--------删除角色---------------------------------------
	public boolean delRole(int roleId) {
		String sql = "delete from role where id=?";
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			int n = pstmt.executeUpdate();
			if (n > 0) {
				clearAllAssignedAuth(roleId);
				flag = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		
		return flag;
	}
	//--------获取所有角色---------------------------------------
	public List<Role> listRoles() {
		List<Role> list = new ArrayList<Role>();
		String sql = "select id,roleName  from role";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("roleName"));
				list.add(role);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return list;
	}
	//--------增加角色---------------------------------------
	public boolean saveRole(Role role){
		String sql = "insert into role(roleName)  values(?)";
		boolean flag = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, role.getRoleName());
			int n = pstmt.executeUpdate();
			if (n > 0) {
				flag = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return flag;
	}
	//---------先清除所有指定的权限，然后重新指定新的权限---------------
	public void assignAuthForRole(Role role){
		// 清除该角色的所有权限
		// 第一种方法是在为角色分配权限的时候先清除该角色所有的权限
		// 第二种方法是 在为角色分配权限的时候，先查询所有的权限A1，然后和提交上来的所有权限(A2)进行比较
		// 如果A1某些权限在提交上来的权限中没有，就删除A1中的权限，如果A2的某些权限在A1中没有那么添加这些权限，
		// A1和A2中重复的权限不再变动，这样的好处是对主键id的值可以让其在一个小的范围内变动
		clearAllAssignedAuth(role.getId());// 先清除所有的权限
		String sql = "insert into role_auth(roleId,authId) values(?,?)";
		List<Auth> list = role.getAuthList();

		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Auth auth = list.get(i);
				pstmt.setInt(1, role.getId());
				pstmt.setInt(2, auth.getId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
	}
	//--------获取已经指定的权限--------------------------------
	public List<Auth> listAssigedAuths(int roleId) {

		List<Auth> list = new ArrayList<Auth>();
		String sql = "select id,authName,actionName,url,moduleId  from auth where id in(select authId from role_auth where roleId=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setAuthName(rs.getString("authName"));
				auth.setActionName(rs.getString("actionName"));
				auth.setUrl(rs.getString("url"));
				auth.setModuleId(rs.getInt("moduleId"));
				list.add(auth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return list;
	}
	//-------清空已经指定的权限------------------------------------
	private void clearAllAssignedAuth(int roleId){
		String sql = "delete from role_auth where roleId =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//--------获取未指定的权限--------------------------------------
	public List<Auth> notAssignedAuths(int roleId){
		List<Auth> list = new ArrayList<Auth>();
		//
		String sql = "select id,authName,actionName,url,moduleId  from auth where id not in(select authId from role_auth where roleId=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Auth auth = new Auth();
				auth.setId(rs.getInt("id"));
				auth.setAuthName(rs.getString("authName"));
				auth.setActionName(rs.getString("actionName"));
				auth.setUrl(rs.getString("url"));
				auth.setModuleId(rs.getInt("moduleId"));
				list.add(auth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return list;
	}
}
