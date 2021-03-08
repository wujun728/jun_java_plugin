package com.sam.demo;

import java.util.Set;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.sam.demo.spring.boot.jpa.base.repository.DefaultRepositoryFactoryBean;
import com.sam.demo.spring.boot.jpa.json.entity.Order;
import com.sam.demo.spring.boot.jpa.json.entity.UserInfo;
import com.sam.demo.spring.boot.jpa.json.service.UserInfoService;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = DefaultRepositoryFactoryBean.class)
public class AppStart {

	public static void main(String[] args) {
		ApplicationContext beanFactory = SpringApplication.run(AppStart.class, args);
		UserInfoService userInfoService = beanFactory.getBean(UserInfoService.class);
		UserInfo userInfo1 = userInfoService.findOne(20L);
		
		System.out.println(userInfo1.getOrders().size());
		
		for(Order order : userInfo1.getOrders()){
			String name = MoreObjects.toStringHelper(order.getClass().getName())
						         .add("name", order.getName())
						         .add("price", order.getPrice())
						         .toString();
			order.setPrice(111D);
			System.out.println(name);
		}
		
		Order order1 = new Order();
		order1.setName(UUID.randomUUID().toString());
		order1.setPrice(Math.random()*1000);
		
		Order order2 = new Order();
		order2.setName(UUID.randomUUID().toString());
		order2.setPrice(Math.random()*1000);
		
		Set orderSet = Sets.newHashSet(order1, order2);
		
		userInfo1.setOrders(orderSet);
		userInfoService.update(userInfo1);
		
		
		/* 保存对象
		Order order1 = new Order();
		order1.setName(UUID.randomUUID().toString());
		order1.setPrice(Math.random()*1000);
		
		Order order2 = new Order();
		order2.setName(UUID.randomUUID().toString());
		order2.setPrice(Math.random()*1000);
		
		Set orderSet = Sets.newHashSet(order1, order2);
		
		UserInfo userInfo2 = new UserInfo();
		userInfo2.setBirthday(new Date());
		userInfo2.setOrders(orderSet);
		
		userInfoService.save(userInfo2);*/
		
		/* JSR-303验证
		Dept dept = new Dept();
		dept.setId(100L);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Dept>> violations = validator.validate(dept);
		for(ConstraintViolation c : violations){
			System.out.println(c.getMessage());
		}*/
	}
}
