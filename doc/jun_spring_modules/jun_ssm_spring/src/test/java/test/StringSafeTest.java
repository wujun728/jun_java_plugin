package test;

import org.junit.Test;
import org.springrain.frame.util.JsonUtils;
import org.springrain.system.entity.User;

public class StringSafeTest {

	
	@Test
	public void test1(){
		User u=new User();
		u.setName("name<script>alert(3);\\,&,</script>");
		
		String str=JsonUtils.writeValueAsString(u);
		System.out.println(str);
	}
	
	
	
	
}
