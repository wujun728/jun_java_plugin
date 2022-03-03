package test;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.User;
import org.springrain.system.service.IMenuService;
import org.springrain.system.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringTest  {
	
	@Resource
	private IMenuService menuService;
	
	@Resource
	private IUserService userService;
	//@Test
	public void updateUser() throws Exception{
		
		User u=new User();
		u.setId(null);
		u.setName("test123");
		//menuService.update(u);
		//testSelectUser();
	
	}
	
	@Test
	public void testSelectUser() throws Exception{
		
		//框架默认Entity做为QueryBean,也可以自己定义QueryBean
		User queryBean=new User();
		//queryBean.setId("admin");
		
		//初始化Finder,并为User取别名 u
		//Finder finder=Finder.getSelectFinder(User.class," u.*").append(" u       WHERE  1=1 ");
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append(" u WHERE 1=1 ");
		//finder.append(" and u.id=:userId ").setParam("userId", "admin");
		
		//设置别名为u
		queryBean.setFrameTableAlias("u");
		
		//拼接queryBean作为查询条件
		userService.getFinderWhereByQueryBean(finder, queryBean);
		//查询第一页
		Page page=new Page(1);
		//分页查询List<User>集合对象
		List<User> list = userService.queryForList(finder,User.class,page);
		
		System.out.println(list.size());
		
		
		userService.update(list);
		
		
		
	}
	
	
	//@Test
	@SuppressWarnings("rawtypes")
	public void testCallProc() throws Exception{
        Finder finder=new Finder("");
		finder.setProcName("read_all_user");
		List<Map<String, Object>> list = menuService.queryForListByProc(finder);
		for (Map m:list) {
			System.out.println(m.get("name"));
		}
	
	}

}
