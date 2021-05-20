

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.learn.hibernate.test.annotation.TuDog;

/** 
 * @author Wujun
 * @since   2013年12月5日 上午10:35:57 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com\\ketayao\\learn\\hibernate\\test\\annotation\\springContext2.xml")
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class TestMappedSuperclass {
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Test
	public void test() {
		TuDog tuDog = new TuDog();
		tuDog.setName("jiji");
		tuDog.setColor("yellow");
		
		Session session = sessionFactory.openSession();
		
		Transaction transaction = session.beginTransaction();

		session.save(tuDog);
		
		transaction.commit();
	}
}
