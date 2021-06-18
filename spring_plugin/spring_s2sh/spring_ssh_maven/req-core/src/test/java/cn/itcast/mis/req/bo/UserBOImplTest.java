package cn.itcast.mis.req.bo;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cn.itcast.mis.req.bo.UserBO;
import cn.itcast.mis.req.hibernate.UserT;

public class UserBOImplTest extends AbstractDependencyInjectionSpringContextTests{

	private UserBO userBO;
	
	@Override
    protected String[] getConfigLocations() {
        // TODO Auto-generated method stub
        return new String[]{ "/applicationContext.xml" };
    }
	
	public UserBO getUserBO() {
        if(userBO == null ){
        	userBO = (UserBO)this.applicationContext.getBean("userBO");
        }
        return userBO;
    }
	
	@Test
	public void testSaveUser(){
		UserT user = new UserT();
		user.setUserEmail("litingwei@163.com");
		user.setUserName("ltw");
		user.setUserPassword("123456");
		getUserBO().addUser(user);
		assertNotNull(user.getUserId());
	}
	
	@Test
	public void testQueryUserList(){
		List users = getUserBO().queryUsersList();
		assertTrue(users.size()>0);
	}
	
	@Test
	public void testFindUserByName(){
		List<UserT> userList = getUserBO().findUserByName("ltw");
		assertTrue(userList.size()>0);
	}

	@Test
	public void testDeleteUser(){
		List<UserT> userList = getUserBO().findUserByName("ltw");
		for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
			UserT userT = (UserT) iterator.next();
			getUserBO().deleteUser(userT.getUserId());
		}
		List users = getUserBO().queryUsersList();
		assertTrue(users.size()<=0);
	}

}
