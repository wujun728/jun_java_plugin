package com.jun.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateSession工具类
 * @author Wujun
 * @createTime   Jul 28, 2011 10:27:32 PM
 */
public class HibernateSessionUtils {
	private static SessionFactory sessionFactory;
	//private static Map<Thread,Session> sessionMap = new HashMap<Thread, Session>();
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	static{
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}
	
	//打开Session并绑定当前线程对象
	public static Session openSession(){
		Session session = getCurrentSession();
		if(session == null){
			session = sessionFactory.openSession();
			//sessionMap.put(Thread.currentThread(),session);
			threadLocal.set(session);
		}
		return session;
	}
	
	//获取当前线程关联的Session
	public static Session getCurrentSession(){
		//return sessionMap.get(Thread.currentThread());
		return threadLocal.get();
	}
	
	/**
	 * 关闭当前线程关联的Session
	 */
	public static void closeSession(){
		Session session = getCurrentSession();
		if(null != session){
			session.close();
			//sessionMap.remove(Thread.currentThread());
			threadLocal.remove();
		}
	}
}
