package com.jun.plugin.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/********************************************************************************************/
	/********************************************************************************************/
	/********************************************************************************************/
	

//	private static SessionFactory sessionFactory;
	//private static Map<Thread,Session> sessionMap = new HashMap<Thread, Session>();
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
//	static{
//		sessionFactory = new Configuration().configure().buildSessionFactory();
//	}
	
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
	
	
	/********************************************************************************************/
	/********************************************************************************************/
	/********************************************************************************************/

}