package cn.yunhe.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;

import cn.yunhe.model.User;
import cn.yunhe.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class TestPageHelper {
	
	@Resource
	private IUserService userService;
	
	@Test
	public void test(){
		PageHelper.startPage(2, 5);
		List<User> list=userService.selectAll();
		for (User user : list) {
			System.out.println(user.getUserName()+",");
		}
	}
	

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
}
