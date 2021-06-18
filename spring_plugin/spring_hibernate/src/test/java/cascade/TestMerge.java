/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestCascadeTypeRemove.java
 * Class:			TestCascadeTypeRemove
 * Date:			2012-9-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package cascade;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Car;
import com.ketayao.learn.hibernate.entity.User;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @since 2012-9-26 下午5:47:55
 */

public class TestMerge {
	
	/**
	 * 插入了两条数据
	 * 描述
	 */
	@Test
	public void test() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		User user = (User)s1.get(User.class, 7L);
		
		Car e = new Car();
		e.setType("奔驰" + new Date());
		e.setUser(user);
		
		user.getCars().add(e);
		s1.merge(user);// 插入了两条数据
		s1.getTransaction().commit();
	}
	
	/**
	 * 使用merge()插入多条数据 
	 * 描述
	 */
	@Test
	public void test2() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		User user = (User)s1.get(User.class, 7L);
		
		Car e = new Car();
		e.setType("奔驰" + new Date());
		e.setUser(user);
		
		user.getCars().add(e);
		s1.merge(user);// 插入了两条数据
		System.out.println("----------------自动flush前---------------");
		s1.getTransaction().commit();
	}
	
	/**
	 * 使用merge()插入多条数据 
	 * 描述
	 */
	@Test
	public void test3() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		User user = (User)s1.get(User.class, 7L);
		
		Car e = new Car();
		e.setType("奔驰" + new Date());
		e.setUser(user);
		
		user.getCars().add(e);
		System.out.println("----------------手动flush---------------");
		s1.flush();
		
		s1.merge(user);// 插入了一条数据
		System.out.println("----------------自动flush前---------------");
		s1.getTransaction().commit();
	}
	//总结：在使用merge时，如果需要操作的对象有级联关系，注意关联对象的状态，如果关联对象的状态是瞬时状态，会被立刻创建（执行insert），当执行
	//seesion.flush()，或者session.getTransaction().commit()会做级联操作，再次创建（执行insert）。所以在不能确认关联对象是否是持久状态时，
	//可以手动进行seesion.flush()操作，避免多次创建对象，当然使用saveOrUpdate，save，update操作时不会存在这个问题。
}
