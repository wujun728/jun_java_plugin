/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestReplicate.java
 * Class:			TestReplicate
 * Date:			2012-9-27
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package cascade;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.AssignKey;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-9-27 上午10:41:46 
 */

public class TestReplicate {
	/**
	 * ReplicationMode.EXCEPTION报错
	 * 
	 * ReplicationMode.IGNORE
	 * Hibernate: 
	 * 描述
	 */
	@Test
	public void test() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		AssignKey ak2 = new AssignKey();
		ak2.setId(5L);
		ak2.setName("bo2");
		
		s1.replicate(ak2, ReplicationMode.EXCEPTION);
		s1.getTransaction().commit();
	}
}
