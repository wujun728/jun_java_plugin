package com.jun.plugin.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Hello extends UnicastRemoteObject implements IHello {
	private static final long serialVersionUID = 1L;

	/*
	 * 构造方法都要抛出RemoteException
	 */
	protected Hello() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String sayHello(String name) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("hello...." + name);
		return "Hello " + name;
	}
}
