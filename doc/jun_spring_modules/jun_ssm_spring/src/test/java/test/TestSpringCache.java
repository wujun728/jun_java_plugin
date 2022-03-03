package test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSpringCache {

	@Resource
IUserService userService;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUser() throws Exception{
		
		List<User> list=new ArrayList<User>();
		
		for (int i = 0; i < 5; i++) {
			User u=new User();
			u.setId(""+i);
			list.add(u);
		}
		
		
		
		userService.putByCache("cacheName", "list1", list);
		
		List<User> byCache = userService.getByCache("cacheName", "list1",ArrayList.class);
		
		System.out.println(byCache.size());
		
		
		
		
	}
	
}
