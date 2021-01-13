package com.socket.demo.client;

import com.socket.client.Future;
import com.socket.client.ServiceProxyFactory;
import com.socket.demo.bean.User;

public class ClientDemo {
	public static void main(String[] args) {
		ServiceProxyFactory factory = new ServiceProxyFactory();
		final UserService userService = (UserService) factory.createProxy("127.0.0.1",60000,UserService.class);
		final PersionService persionServiceProxy = (PersionService) factory.createProxy(65535,PersionService.class);
		final Long start = System.currentTimeMillis();
		
		 Future<User> fUser = userService.getUser("oo", 12);
//		 userService.getUser("rr", 12);
//		 Future<Integer> fInt = userService.add(2);
		 
		 User user = fUser.getObject();
		 System.out.println(user.getName());
		 
		 Future<User> fUser2 = userService.getUser(user.getName()+"ff", 12);
		 System.out.println(fUser2.getObject().getName());
	}
}
