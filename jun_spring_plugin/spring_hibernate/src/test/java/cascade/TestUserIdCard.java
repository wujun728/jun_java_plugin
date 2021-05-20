/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestUserIdCard.java
 * Class:			TestUserIdCard
 * Date:			2013-5-14
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package cascade;

import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.IDCard;
import com.ketayao.learn.hibernate.entity.User;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2013-5-14 下午2:34:55 
 */

public class TestUserIdCard {
	@Test
	public void test() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		IDCard idCard = new IDCard();
		idCard.setCardno("1231231293209423");
		
		User user = (User)s1.get(User.class, 1L);
		user.setIdCard(idCard);
		idCard.setUser(user);
		
		s1.persist(user);

		s1.getTransaction().commit();
	}
	
	@Test
	public void testDelete() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		IDCard idCard = (IDCard)s1.get(IDCard.class, 1L);
		s1.delete(idCard);

		s1.getTransaction().commit();
	}
}
