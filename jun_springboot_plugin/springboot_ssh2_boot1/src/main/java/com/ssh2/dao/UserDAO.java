package com.ssh2.dao;

import java.util.List;

import com.ssh2.po.User;

public interface UserDAO {
	public List<User> findAll();
}
