package com.ssh2.service;

import java.util.List;

import com.ssh2.po.User;

public interface UserService {
	public List<User> find(String name);
}
