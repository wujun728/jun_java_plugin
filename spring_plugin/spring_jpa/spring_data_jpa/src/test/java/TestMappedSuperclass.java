

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.learn.entity.Dog;
import com.ketayao.learn.entity.TuDog;
import com.ketayao.learn.service.DogService;

/** 
 * @author Wujun
 * @since   2013年12月5日 上午10:35:57 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:springContext.xml")
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class TestMappedSuperclass {
	@Autowired
	private DogService dogService;
	
	@Test
	public void test() {
		TuDog tuDog = new TuDog();
		tuDog.setName("jiji2");
		tuDog.setColor("yellow");
		
		dogService.saveOrUpDate(tuDog);
	}
	
	@Test
	public void test2() {
		Dog dog = new Dog();
		dog.setName("xx");
		
		dogService.saveOrUpDate(dog);
	}
}
