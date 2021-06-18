package org.tangjl.multidatasource.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.tangjl.multidatasource.model.User;
import org.tangjl.multidatasource.util.DBContextHolder;

@Repository
public class UserDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * 在同一线程获取Session
	 * 
	 * @return 返回获取到的Session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public User save (User u) {
		getSession().save(u);
		return u;
	}

	/**
	 * 同一个事物测试事物回滚
	 * 
	 * @param u 对象
	 * @param isExe 是否产生异常
	 */
	public void saveException (User u, boolean isExe) {
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_B);
		save(u);
		if (isExe) {
			System.out.println("事物回滚了");
			throw new RuntimeException();
		}
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_A);
		User user = new User();
		user.setName(u.getName());
		save(user);
	}
}
