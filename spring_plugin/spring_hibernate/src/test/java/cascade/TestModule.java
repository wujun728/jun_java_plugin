/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestModule.java
 * Class:			TestModule
 * Date:			2013-5-27
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package cascade;

import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Module;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2013-5-27 上午10:31:07 
 */

public class TestModule {

	@Test
	public void test() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		Module module = (Module)s1.get(Module.class, 3L);
		
		s1.delete(module);
		s1.getTransaction().commit();
	}
}
