package com.buxiaoxia.business.repository;

import com.buxiaoxia.Application;
import com.buxiaoxia.business.entity.Car;
import com.buxiaoxia.business.entity.User;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
	public void before() {
		// 初始化数据
		userRepository.save(new User("xw1", "card1", "1", 1));
		userRepository.save(new User("xw2", "card2", "2", 2));
		userRepository.save(new User("xw3", "card3", "3", 3));
		userRepository.save(new User("xw4", "card4", "4", 4));

		carRepository.save(new Car("Benz", "AX123", 123343423, new Date()));
		carRepository.save(new Car("Benz", "SDF13", 34633145, new Date()));
		carRepository.save(new Car("Ferrari", "F112", 568345345, new Date()));
		carRepository.save(new Car("Ferrari", "F34", 546235454, new Date()));
		System.out.println("=============save=============");
	}

	@Test
	public void testFindSameUser() {
		// 如果redis没有 就会走一次sql 否则不走sql
		User user = userRepository.findByName("xw1");
		assertEquals("card1", userRepository.findByName("xw1").getCardId());
		assertEquals("card1", userRepository.findByName("xw1").getCardId());

		// 如果没有在save上加@CachePut(value = "userCache", key = "#p0.name")
		// 那么findByName走的缓存还是老数据 card1
		user.setCardId("card1_new");
		userRepository.save(user);
		user = userRepository.findByName("xw1");
		assertEquals("card1_new", user.getCardId());
	}

	@Test
	public void testFindSameCardId() {
		// 如果redis没有 就会走一次sql 否则不走sql
		User user = userRepository.findByCardId("card1");
		assertEquals(user.getName(), userRepository.findByCardId("card1").getName());
		assertEquals(user.getName(), userRepository.findByCardId("card1").getName());
	}

	@Test
	public void testFindCar() {
		System.out.println("===testFindCar===");
		List<Car> cars = carRepository.findByBrand("Benz");
		assertEquals(2, cars.size());
		// 不走sql
		cars = carRepository.findByBrand("Benz");
		assertEquals(2, cars.size());
	}

	@Transactional
	@Test
	public void testDeleteAndFindCar() {
		System.out.println("===testDeleteAndFindCar===");
		List<Car> cars = carRepository.findByBrand("Benz");
		assertEquals(2, cars.size());
		carRepository.deleteByBrand("Benz");
		// 走sql
		cars = carRepository.findByBrand("Benz");
		assertEquals(0, cars.size());
	}


	@After
	public void after() {
		System.out.println("=============deleteAll=============");
		userRepository.deleteAll();
		carRepository.deleteAll();
	}

}
