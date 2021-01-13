package com.socket.demo.client;

import com.socket.client.Future;
import com.socket.demo.bean.User;

public interface UserService {
	
	public Future<User> getUser(String name,Integer age);
	
	public Future<Integer> add(Integer a);

}
