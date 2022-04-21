package com.jun.plugin.rmi;

import java.rmi.Naming;

public class RmiClient {
	public static void main(String[] args) {
		try {
			// 通过RMI名称查找远程对象,如果是本地调用的话,直接用RMI_Hello即可
			IHello hello = (IHello) Naming
					.lookup("rmi://127.0.0.1:1099/RMI_Hello");
			hello.sayHello("jun_rmi");
			System.out.println(hello.sayHello("jun_rmi")); // 调用远程对象的方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
