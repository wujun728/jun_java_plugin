package com.jun.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.support.StaticApplicationContext;

import com.jun.entity.Department;

/**
 * @author Wujun
 * @createTime   2011-8-9 下午10:10:04
 */
public class DepartmentUtils {
	
	/**
	 * 返回部门树形字符串
	 * @param topDepartmentCollection  顶级部门集合
	 * @param prefix                   输出部门名称的前缀
	 * @parem list                     部门输出集合
	 * @return
	 */
	private static void getDepartmentTreesString(Collection<Department> topDepartmentCollection,String prefix,List<Department> list){
		for (Department department : topDepartmentCollection) {
			Department copy = new Department();
			copy.setId(department.getId());
			copy.setDeptName(prefix + "┝" + department.getDeptName());
			list.add(copy);
			getDepartmentTreesString(department.getChildren(),prefix + "　",list);
		}
	}
	
	/**
	 * 根据顶级部门获取所有部门(部门名称经过修改)
	 */
	public static List<Department> getAllDepartmentList(List<Department> topDepartmentList){
		List<Department> list = new ArrayList<Department>();
		getDepartmentTreesString(topDepartmentList,"",list);
		return list;
	}
	
	/**
	 * 给定集合中移除指定部门及其子部门
	 */
	public static void removeSomeDepartmentsAndChildren(List<Department> departmentList,Department department){
		departmentList.remove(department);
		for (Department dept : department.getChildren()) {
			removeSomeDepartmentsAndChildren(departmentList,dept);
		}
	}
	
	/**
	 * 给定集合中移除指定部门的子部门
	 */
	public static void removeSomeDepartmentsAndChildren(List<Department> departmentList,Department department,boolean isfirst){
		if(!isfirst){
			departmentList.remove(department);
		}
		for (Department dept : department.getChildren()) {
			isfirst = false;
			removeSomeDepartmentsAndChildren(departmentList,dept);
		}
	}
}
