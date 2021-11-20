package com.jun.plugin.rmi;

import java.rmi.Naming;

public class RmiServer {
	public static void main(String[] args) {
		try {
			Hello hello = new Hello(); // 实例化要发布的类
			Naming.rebind("RMI_Hello", hello); // 绑定RMI名称 进行发布
			System.out.println("=== Hello server Ready === ");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
