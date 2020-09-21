/*   
 * Project:  
 * FileName: UserMapper.java
 * Company: Chengdu osmp Technology Co.,Ltd
 * version: V1.0
 */
package com.osmp.web.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.user.entity.User;
import com.osmp.web.user.entity.UserRole;

public interface UserMapper extends BaseMapper<User> {

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public User getById(@Param("id") String id);
	
	/**
	 * 根据用户Id查询用户权限
	 * @param userId
	 * @return
	 */
	public List<Integer> selectUserPrivilege(@Param("id")String id);
	
	/**
	 * 获得用户
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public User getUser(@Param("userId")String userId, @Param("roleId")String roleId); 
	
	/**
	 * 
	 * @param 删除用户角色
	 */
	public void deletByUserId(@Param("userId")String userId);
	
	/**
	 * 
	 * @param 删除角色用户关联
	 */
	public void deletByRoleId(@Param("roleId")String roleId);
	
	/**
	 * 增加一个用户角色
	 * @param userRole
	 */
	public void insertUserrole(@Param("userRole")UserRole userRole);
	
	/**
	 * 更新用户最后登录Ip
	 * @param id 用户id
	 * @param ip 登录IP
	 */
	public void updateLoadIp(@Param("id")String id,@Param("ip") String ip);
	
	/**
	 * 根据用户Id查询用户角色Id
	 * @param userId
	 * @return
	 */
	public List<String> selectUserRole(@Param("id")String id);
	
	/**
	 * 给用户增加一个角色
	 * @param userRole
	 */
	public void addUserRole(UserRole userRole);
}
