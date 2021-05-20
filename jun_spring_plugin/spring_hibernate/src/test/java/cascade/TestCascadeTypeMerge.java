/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestCascadeTypeMerge.java
 * Class:			TestCascadeTypeMerge
 * Date:			2012-9-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package cascade;

import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.User;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-9-26 下午5:52:55 
 */

public class TestCascadeTypeMerge {
	/**
	 * 缓存中存在的对象，不能修改id，否则保存出错。
	 * 描述
	 */
	@Test
	public void testCascadeTypeALL_save() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = (User)s1.get(User.class, 5L);
		user.setCars(null);
		
		user.setId(6L);
		s1.save(user);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testCascadeTypeALL_update() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = (User)s1.get(User.class, 5L);
		user.setCars(null);
		
		s1.save(user);
		s1.getTransaction().commit();
	}
}
