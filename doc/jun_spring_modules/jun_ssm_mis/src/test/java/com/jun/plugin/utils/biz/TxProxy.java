package com.jun.plugin.utils.biz;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import com.jun.plugin.utils.JdbcUtil;
/**
 * 事务的代理类，可以代理任意的Service
 */
public class TxProxy implements InvocationHandler{
	private Object src;			//声明被代理类对象
	private TxProxy(Object src){//在私有的构造中给成员设置值
		this.src=src;
	}
	/**
	 * 提供一个静态的方法返回代理对象
	 */
	public static Object factory(Object src){
		System.err.println("0：返回代理类");
		Object proxyedObj = //生成被代理类的接口的子类
				Proxy.newProxyInstance(
						TxProxy.class.getClassLoader(),
						src.getClass().getInterfaces(), 
						new TxProxy(src));
		return proxyedObj;
	}
	
	/**
	 * 以下是执行的句柄，当调用代理类的任意方法时都会调用这个方法
	 * 在这儿是管理事务的关键
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//判断是否有事务的注解
		if(!method.isAnnotationPresent(Tx.class)){
			System.err.println("没有事务的注解，用户不需要事务。直接执行目标方法");
			return method.invoke(src, args);
		}
		
		//第一步：声明连接
		Connection con = null;
		Object returnValue = null;
		try{
			//第二步：获取连接
			con = JdbcUtil.getConn();
			//第三步：设置事务的开始
			System.err.println("设置事务的开始");
			con.setAutoCommit(false);
			//第四步：调用目标类(被代理类)的方法
			returnValue = method.invoke(src, args);
			//第五步：调用如果成功提交
			System.err.println("调用如果成功提交");
			con.commit();
		}catch(Exception e){
			System.err.println("调用不成功回滚");
			con.rollback();
			throw e;
		}finally{
			con.close();
		}
		return returnValue;
	}
}
