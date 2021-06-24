package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.zml.oa.dao.IJdbcDao;
import com.zml.oa.entity.User;
import com.zml.oa.pagination.Pagination;
import com.zml.oa.pagination.PaginationThreadUtils;
import com.zml.oa.service.impl.PasswordHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml", "classpath*:/springMVC.xml" })  
public class passwordTest {
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Test
	public void testJdbc(){
		  User u = new User();
		  u.setName("admin");
		  u.setId(14);
		  u.setPasswd("admin");
		  u.setSalt("wujun");
		  passwordHelper.encryptPassword(u);
		  System.err.println(u.getPasswd());
		  System.err.println(u.getCredentialsSalt());
		  System.err.println(u.getSalt());
	}
}
