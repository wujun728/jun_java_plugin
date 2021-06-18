package com.itheima.view;

import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;

public class Client {

	public static void main(String[] args) {
		BusinessService s = new BusinessServiceImpl();
		s.transfer("aaa", "bbb", 100);
	}

}
