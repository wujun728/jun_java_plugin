package com.jun.plugin.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 客户端测试模板输入类
 * @author hailang
 * @date 2009-7-9 下午04:11:20
 * @version 1.0
 */
public class ClientTest {
	
	public static List<User> initUserList(){
		List<User> list=new ArrayList<User>();
		User user1=new User();
		user1.setUserName("张三");
		user1.setUserPassword("123");
		user1.setAge(20);
		
		
		User user2=new User();
		user2.setUserName("李四");
		user2.setUserPassword("123");
		user2.setAge(22);
		
		User user3=new User();
		user3.setUserName("王五");
		user3.setUserPassword("123");
		user3.setAge(21);
		
		list.add(user1);
		list.add(user2);
		list.add(user3);
		
		return list;
		
	}
	
	
	
	public static void main(String[] args) {
		
		Map<String,Object> root=new HashMap<String, Object>();
		List<User> list=ClientTest.initUserList();
		
		root.put("userList",list);
		
//		FreeMarkerUtil.analysisTemplate("user.ftl", "UTF-8", root);
		
		
	}
}
