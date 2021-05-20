package com.tjgd.dao;

import java.util.List;
import com.tjgd.bean.Employee;
import com.tjgd.bean.Role;

public interface IEmployeeDAO {
	//--------删除员工------------------------
	public void delEmployee(int empId);
	//--------返回所有的员工------------------
	public List<Employee> listEmployees();
	//--------保存员工------------------------
	public void saveEmployee(Employee emp);
	//--------获取单个员工--------------------
	public Employee findByID(int empId);
	//--------获取员工已经指定的角色-----------
	public List<Role> findAllAssignedRoles(int empId);
	//--------获取员工没有指定的角色-------------
	public List<Role> findNotAssignedRoles(int empId);
	//--------给员工指定角色-------------------
	public void assignRoleForEmp(Employee emp);
}
