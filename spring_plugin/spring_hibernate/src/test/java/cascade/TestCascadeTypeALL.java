/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestOneToMany.java
 * Class:			TestOneToMany
 * Date:			2012-9-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package cascade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Car;
import com.ketayao.learn.hibernate.entity.User;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/**
 * 
 * @author Wujun
 * @since 2012-9-26 下午5:28:02
 */

public class TestCascadeTypeALL {

	/**
	 * 级联保存必须要互相设置对象 描述
	 */
	@Test
	public void testCascadeTypeALL_save() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Car car = new Car();
		car.setType("保时捷" + simpleDateFormat.format(date));
		car.setColor("red");

		List<Car> cars = new ArrayList<Car>();
		cars.add(car);

		User user = new User();
		user.setName("yao" + simpleDateFormat.format(date));
		user.setCars(cars);

		car.setUser(user);
		
		s1.save(user);
		s1.getTransaction().commit();
	}

	@Test
	public void testCascadeTypeALL_delete() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		User user = (User)s1.get(User.class, 1L);
		user.setCars(null);
		s1.delete(user);
		s1.getTransaction().commit();
	}
}
