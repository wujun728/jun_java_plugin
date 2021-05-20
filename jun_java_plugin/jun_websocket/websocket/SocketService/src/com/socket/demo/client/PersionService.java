package com.socket.demo.client;

import com.socket.client.Future;
import com.socket.demo.bean.User;

public interface PersionService {
	
	public Future<User> getPersion(String name,Integer age);

}
