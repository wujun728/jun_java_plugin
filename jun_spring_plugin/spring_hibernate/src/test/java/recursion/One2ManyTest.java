package recursion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Message;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年12月24日 上午11:00:15 
 */
public class One2ManyTest {
	@Test
	public void testLoadLanguageCode() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = s1.beginTransaction();
		Message message = (Message) s1.load(Message.class, 1);
		
		transaction.commit();
		System.out.println(message.getLanguage().getCode());
	}
	
	@Test
	public void testGetLanguageCode() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = s1.beginTransaction();
		Message message = (Message) s1.get(Message.class, 1);
		
		transaction.commit();
		System.out.println(message.getLanguage().getCode());
	}
}
