package cn.itcast.mis.req.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;;
/**
* <p>Title:BasicDAO</p>  
*/
public class BasicDAO {
	SessionFactory sessionFactory;
	Session session ;	
	
	public void init() {
		session = sessionFactory.openSession();
		// TODO Auto-generated constructor stub
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveObject(Object obj) {
		init();
		this.session.save(obj);
		session.close();
	}
	
	public Object saveObjectWithReturn(Object obj) {
		init();
		this.session.save(obj);
		session.close();
		return obj;
	}

	public Object searchObjById(Class name, Integer id) {
		init();
		Object obj = this.session.get(name,id); 
		session.close();
		return obj;
	}

	public void updateObj(Object obj) {
		init();
		this.session.update(obj);
		session.close();
		
	}
	
	public List searchByHQL(String hql) {
		init();
		List result = this.session.createQuery(hql).list();
		session.close();
		return result;	
	}
	
	public void deleteObj(Object obj) {
		init();
		this.session.delete(obj);
		session.close();
		
	}

	public void deleteObj(Class name, Integer id) {
		init();
		Object o = this.session.load(name,id);
		if(o!=null)
			this.session.delete(o);
		session.flush();
		session.close();
	}

 
}

