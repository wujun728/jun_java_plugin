package com;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.caland.common.junit.AbstractSpringJunitTest;
import com.caland.core.bean.User;
import com.caland.core.query.UserQuery;
import com.caland.core.service.UserService;


/**
 * junit
 * @author lixu
 * @Date [2014-3-18 下午01:14:42]
 */
public class TestUser extends AbstractSpringJunitTest{

	//测试用户添加
	//看看能不能添加到指定库去
	//写好了Service  Dao  
	//直接使用 自动装配
	@Autowired
	private UserService userService;
	//开始运行了  
	
	@Test
	//@Ignore   //忽略
	public void testAddUser() throws Exception {
		//用户对象是跟数据库一一对应的JavaBean
		User user = new User();
		user.setUsername("李四");
		user.setAge(33);
		user.setPhone(138888888);
		user.setEmail("888888@qq.com");
		//把此数据添加到数据库去
		userService.addUser(user);
	}
	//测试取数据  
	@Test
	@Ignore
	public void testGetUser() throws Exception {
		
		//使用用户的Service层来取
		//知道只有一条
		//实际当中  我们是不知道有多少条的
		//返回的一定是List集合
		//创建用户条件对象
		UserQuery  userQuery = new UserQuery();
		//设置用户名  为  赵六  或李四
		userQuery.setUsername("赵六");
		//返回结果
		List<User> users = userService.getUserList(userQuery);
		for(User user : users){
			//输出结果
			System.out.println(user.toString());
		}
	}




	
}
