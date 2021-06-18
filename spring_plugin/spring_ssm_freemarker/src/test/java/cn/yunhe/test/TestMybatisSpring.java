package cn.yunhe.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yunhe.model.User;
import cn.yunhe.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-mybatis.xml"})
public class TestMybatisSpring {

	ApplicationContext ac=null;
	
	IUserService userService=null;
	
	@Before
	public void before(){
		ac=new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
		userService=(IUserService) ac.getBean("userService");
	}
	
	@Test
	public void get(){
		User user=userService.selectByPrimaryKey(22);
		System.out.println(user.getUserName());
	}
	
	
	
	
	
	
}
