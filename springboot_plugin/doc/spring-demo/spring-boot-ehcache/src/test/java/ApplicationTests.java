import com.buxiaoxia.Application;
import com.buxiaoxia.business.entity.Car;
import com.buxiaoxia.business.entity.User;
import com.buxiaoxia.business.repository.CarRepository;
import com.buxiaoxia.business.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 2017-02-19 22:07.
 * author xiaw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CarRepository carRepository;


	@Before
	public void before(){
		userRepository.save(new User("xw1","1",1));
		userRepository.save(new User("xw2","2",2));
		userRepository.save(new User("xw3","3",3));
		userRepository.save(new User("xw4","4",4));

		carRepository.save(new Car("Benz","AX123",123343423,new Date()));
		carRepository.save(new Car("Benz","SDF13",34633145,new Date()));
		carRepository.save(new Car("Ferrari","F112",568345345,new Date()));
		carRepository.save(new Car("Ferrari","F34",546235454,new Date()));
		System.out.println("=============save=============");
	}

	@Test
	public void testFindSameUser(){
		//ehcache配置5s中超时时间。超过这个时间会重新获取
		User user = userRepository.findByName("xw1");
		assertEquals(user.getName(),userRepository.findByName("xw1").getName());
		assertEquals(user.getName(),userRepository.findByName("xw1").getName());
	}

	@Test
	public void testDelUser(){
		userRepository.findByName("xw1");
		userRepository.deleteByName("xw1");
		//不使用 @CacheEvict  而且不做查询
//		assertEquals("xw1",userRepository.findByName("xw1").getName());
//		assertEquals("xw1",userRepository.findByName("xw1").getName());

		//使用 @CacheEvict  就会做一次查询
		assertNull(userRepository.findByName("xw1"));
		assertNull(userRepository.findByName("xw1"));
	}

	@Test
	public void testFindCar(){
		System.out.println("===testFindCar===");
		List<Car> cars = carRepository.findByBrand("Benz");
		assertEquals(2,cars.size());
		// 不走sql
		cars = carRepository.findByBrand("Benz");
		assertEquals(2,cars.size());
	}

	@Transactional
	@Test
	public void testDeleteAndFindCar(){
		System.out.println("===testDeleteAndFindCar===");
		List<Car> cars = carRepository.findByBrand("Benz");
		assertEquals(2,cars.size());
		carRepository.deleteByBrand("Benz");
		// 走sql
		cars = carRepository.findByBrand("Benz");
		assertEquals(0,cars.size());
	}


	@After
	public void after(){
		System.out.println("=============deleteAll=============");
		userRepository.deleteAll();
	}

}
