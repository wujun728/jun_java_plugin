package com.springboot.dao;

import com.springboot.model.Admin;

public interface IAdminDao {

	public Admin find(Integer id);

	public String userName(Integer id);

}
