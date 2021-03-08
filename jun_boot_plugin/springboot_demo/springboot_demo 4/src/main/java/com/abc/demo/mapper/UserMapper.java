package com.abc.demo.mapper;

import java.util.List;

import com.abc.demo.domain.User;

public interface UserMapper {
/*	@Select("SELECT * FROM user Where id=#{id}")
	User findById(@Param("id")Integer id);
	
	@Select("SELECT * FROM user Where user_name=#{name}")
	User findByName(@Param("name")String name);*/
	
	//根据ID查询
	User findById(Integer id);
	//获取全部
	List<User> findByAll();
	//新增
	int insert(User user);
	//修改
	int update(User user);
	//删除
	int deleteById(Integer id);
}
