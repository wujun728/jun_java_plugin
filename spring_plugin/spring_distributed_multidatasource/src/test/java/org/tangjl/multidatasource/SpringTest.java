package org.tangjl.multidatasource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.tangjl.multidatasource.dao.UserDao;
import org.tangjl.multidatasource.model.User;
import org.tangjl.multidatasource.util.DBContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class SpringTest {

	@Autowired
	private UserDao userDao;

	/**
	 * 自动创建表
	 */
	@BeforeClass
	public static void creatTable() {
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_A);
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_B);
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_C);
	}

	/**
	 * 两次保存不在同一个事物,所以A数据源不会回滚
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTwoTransaction() throws Exception {
		User user = new User();
		user.setName("张三");
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_A);
		userDao.save(user);
		System.out.println("A数据源");
		
		// 因为上一次保存和这次保存不再同一个是物理
		// 所有可以直接user.setId(null);
		// 如果这两个操作在同一个是物理,则不允许这样操作,必须new一个新的User
		user.setId(null);
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_B);
		userDao.save(user);
		System.out.println("B数据源");
	}
	
	/**
	 * 两次保存在同一个事物里,所以A数据源回滚了
	 * @throws Exception
	 */
	@Test
	public void testOneTransaction() throws Exception {
		User user = new User();
		user.setName("李四");
		userDao.saveException(user, true);
	}

	/**
	 * 两次保存成功
	 * 
	 * <pre>
	 * 本来"王五"应该在两个数据源所对应的两个数据库中都有一条,
	 * 但是却都保存在一个数据源中(即一个数据库出现两条,
	 * 	另一个数据库没有,而出现两条数据的数据库是配置中的默认数据源所对应数据库,
	 * 	不是事物里设置的任意一个数据源,在该项目中会发现王五保存至c数据源对应数据库中),
	 * 并没有成功切换
	 * 网络上给出的解释是:为了保证事物的完整性,在事物里Spring会阻止切换数据源
	 * </pre>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTrue() throws Exception {
		User user = new User();
		user.setName("王五");
		userDao.saveException(user, false);
	}
}
