package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.plugin.oa.dao.IJdbcDao;
import com.jun.plugin.oa.entity.User;
import com.jun.plugin.oa.pagination.Pagination;
import com.jun.plugin.oa.pagination.PaginationThreadUtils;
import com.jun.plugin.oa.service.impl.PasswordHelper;

import org.springframework.beans.factory.annotation.Autowired;

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
