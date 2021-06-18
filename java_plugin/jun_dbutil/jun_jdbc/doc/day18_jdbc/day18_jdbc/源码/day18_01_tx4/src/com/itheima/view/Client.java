package com.itheima.view;

import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;
import com.itheima.util.BeanFactory;

public class Client {

	public static void main(String[] args) {
		BusinessService s = BeanFactory.getBusinessService();
		s.transfer("aaa", "bbb", 100);
	}

}
