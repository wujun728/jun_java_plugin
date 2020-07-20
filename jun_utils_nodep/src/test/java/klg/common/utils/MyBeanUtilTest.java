package klg.common.utils;

import org.junit.Test;

import klg.common.utils.MyPrinter;

public class MyBeanUtilTest {
	@Test
	public void test(){
		User user=BeanTools.newAndSet(User.class, "logrole.logRoleId", 2);
		User user2=new User();
		user2.setAccount("aaa");
		BeanTools.copyPropertyIgnoreNull(user, user2);
		MyPrinter.printJson(user);
	}
}
