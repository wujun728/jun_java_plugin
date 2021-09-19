package com.tjgd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tjgd.DBHelper.DataSourceUtil;
import com.tjgd.bean.Employee;
import com.tjgd.bean.Role;
import com.tjgd.dao.IEmployeeDAO;
public class EmployeeDAOImpl implements IEmployeeDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	DataSourceUtil ds=null;
	//-------构造函数中初始化-----------------------------
	public EmployeeDAOImpl() {
		try {
    		ds=new DataSourceUtil();
    		this.conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//--------删除单个员工--------------------------------
	public  void delEmployee(int empId){
		String sql = "delete from employee where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
	}
    //---------查找所有的员工-----------------------------
	public  List<Employee> listEmployees(){
		List<Employee> list = new ArrayList<Employee>();
		String sql = "select * from Employee order by id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Employee emp=new Employee();
				emp.setId(rs.getInt("id"));
				emp.setEmpName(rs.getString("empName"));
				emp.setPassword(rs.getString("password"));
				emp.setPosition(rs.getString("position"));
				list.add(emp);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return list;
	}
    //----------增加员工------------------------------------
	public  void saveEmployee(Employee emp){
		String sql = "insert into Employee(empName,password,position)  values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getPassword());
			pstmt.setString(3, emp.getPosition());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
	}
    //----------查询单个员工--------------------------------
	public  Employee findByID(int empId) {
		Employee emp = new Employee();
		String sql = "select id,empName,password,position from Employee where id=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setEmpName(rs.getString("empName"));
				emp.setPassword(rs.getString("password"));
				emp.setPosition(rs.getString("position"));
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return emp;
	}
	//-----------获取已经分配的角色---------------------------
	public  List<Role> findAllAssignedRoles(int empId){
		List<Role> list = new ArrayList<Role>();
		String sql = "select id,roleName  from role where id in(select roleId from emp_role where empId=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,empId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Role role=new Role();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("roleName"));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return list;
	}
	//------------为员工指定角色-------------------------------
	public  void assignRoleForEmp(Employee emp){
		//先清除所有已经指定的角色
		System.out.println(emp.getId());
		clearAllAssignedRole(emp.getId());
		String sql = "insert into emp_role(empId,roleId) values(?,?)";
		List<Role> list = emp.getRoles();
		
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=0;i<list.size();i++){
				
				Role role=list.get(i);
				pstmt.setInt(1,emp.getId());
				pstmt.setInt(2,role.getId());
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
	//--------------查找未被指定的权限--------------------------
	public List<Role> findNotAssignedRoles(int empId){
		List<Role> list = new ArrayList<Role>();
		String sql = "select id,roleName  from role where id not in(select roleId from emp_role where empId=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,empId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Role role=new Role();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("roleName"));
				list.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
		return list;
	}
	//------------批量删除员工指定的角色---------------------------
	private void clearAllAssignedRole(int empId){
		String sql = "delete from emp_role where empId = ? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,empId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
